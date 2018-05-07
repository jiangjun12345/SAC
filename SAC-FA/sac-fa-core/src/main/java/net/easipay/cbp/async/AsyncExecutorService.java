package net.easipay.cbp.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author mchen
 * @date 2016-4-25
 */
public class AsyncExecutorService
{
    public static AsyncExecutorService inst = new AsyncExecutorService();
    
    private ExecutorService executorService;
    
    public AsyncExecutorService()
    {
	AsyncThreadFactory asyncThreadFactory = new AsyncThreadFactory();
	executorService = Executors.newCachedThreadPool(asyncThreadFactory);
    }
    
    public void submit(Runnable task)
    {
	executorService.submit(task);
    }
    
}
