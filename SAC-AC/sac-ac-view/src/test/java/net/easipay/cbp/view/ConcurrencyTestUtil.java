package net.easipay.cbp.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.util.StopWatch;
import static org.junit.Assert.assertTrue;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author dell (Cyrus)
 * @date 2015-7-20 下午01:41:38
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */
public class ConcurrencyTestUtil
{

    public static void assertConcurrent(final String message, final List<? extends Runnable> runnables, final int maxTimeoutSeconds, final int maxThreadPoolSize) throws InterruptedException
    {
	StopWatch stopWatch = new StopWatch();
	stopWatch.start(message);

	final int numThreads = runnables.size();
	final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
	final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads > maxThreadPoolSize ? maxThreadPoolSize : numThreads);
	try {
	    final CountDownLatch afterInitBlocker = new CountDownLatch(1);
	    final CountDownLatch allDone = new CountDownLatch(numThreads);
	    for (final Runnable submittedTestRunnable : runnables) {
		threadPool.submit(new Runnable() {
		    public void run()
		    {
			try {
			    afterInitBlocker.await(); // 相当于加了个阀门等待所有任务都准备就绪
			    submittedTestRunnable.run();
			} catch ( final Throwable e ) {
			    exceptions.add(e);
			} finally {
			    allDone.countDown();
			}
		    }
		});
	    }

	    // start all test runners
	    afterInitBlocker.countDown();
	    assertTrue(message + " timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));

	    stopWatch.stop();
	    System.out.println(stopWatch.prettyPrint());
	} finally {
	    threadPool.shutdownNow();
	}
	assertTrue(message + "failed with exception(s)" + exceptions, exceptions.isEmpty());
    }

    @Test
    public void testAssertConcurrent() throws InterruptedException
    {
	List<Runnable> tasks = new ArrayList<Runnable>(100000);
	for (int i = 0; i < 100000; i++) {
	    tasks.add(new Runnable() {

		@Override
		public void run()
		{
		    try {
			Thread.sleep(20);
		    } catch ( InterruptedException _ ) {

		    }
		}

	    });
	}

	ConcurrencyTestUtil.assertConcurrent("1024tasks", tasks, 10, 215);
    }
}
