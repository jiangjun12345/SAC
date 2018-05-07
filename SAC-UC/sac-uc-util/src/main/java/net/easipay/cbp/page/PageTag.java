package net.easipay.cbp.page;


import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 分页标签处理类。
 * @version 1.00 2010-5-25, 15:40:02
 * @jdj 1.6
 * @author sun
 */
public class PageTag extends TagSupport {

    private static final long serialVersionUID = 3441235160861303080L;
    // 字符串缓冲区
    private StringBuilder buff;
    // 首页
    private String homePage;
    // 末页
    private String lastPage;
    // 上一页
    private String previousPage;
    // 前置分隔符
    private String preSeparator;
    // 当前页之前的页数，默认为4。
    private int beforeNum = 4;
    // 当前页之后的页数，默认为5。
    private int afterNum = 4;
    // 分页对象
    @SuppressWarnings("rawtypes")
	private Page page;
    // 链接地址
    private String url;
    // 下一页
    private String nextPage;
    // 后置分隔符
    private String nextSeparator;
    // 当前页码
    @SuppressWarnings("unused")
	private String pageIndex;
    // 页面大小
    private String pageSize;
    // 外层div样式
    private String divClass;
    // 是否进行动态补足，默认为true。
    private boolean supplement = true;
    // Html标签
    private static final String LABEL_START = "<label>";
    private static final String LABEL_END = "</label>";
    private static final String SPAN_START = "<span>";
    private static final String SPAN_END = "</span>";
    // 日志记录器
    private static Log log = LogFactory.getLog(PageTag.class);

    /**
     * 标签处理开始，构造一个存放分页信息的字符串缓冲区。
     * @return SKIP_BODY，忽略标签之间的内容。
     * @throws javax.servlet.jsp.JspException Jsp异常。
     */
    @Override
    public int doStartTag() throws JspException {
        buff = new StringBuilder();
        return SKIP_BODY;
    }

    /**
     * 标签实际分页处理逻辑。
     * @return EVAL_PAGE，按正常的流程继续执行Jsp页面。
     * @throws javax.servlet.jsp.JspException Jsp异常。
     */
    @Override
    public int doEndTag() throws JspException {
        if (page == null) {
            log.info("page is null.");
            return EVAL_PAGE;
        }

        // 写入开始DIV
        writeBeginDiv();
        // 写入分页信息
        writePageInfo();
        // 写入结束DIV
        writeEndDiv();
        // 记录日志
        writeDebugLog();
        // 输出到页面
        // printToPage();

        return EVAL_PAGE;
    }

    /**
     * 写入实际的分页信息。
     * 调用者可自行设定是否显示首页、末页、上一页、下一页
     * 以及当前页面之前和之后的页数、是否进行动态补足等。
     */
    private void writePageInfo() {
        int beforeCount = countBefore();
        int afterCount = countAfter();

        // 如果定义了动态补足，则对当前页之前和之后的页数进行动态补足。
        if (supplement) {
            beforeCount = fixBeforeCount(beforeCount, afterCount);
            afterCount = fixAfterCount(beforeCount, afterCount);
        }

        int index = page.getPageIndex();
        // 首页
        if(homePage!=null && !homePage.trim().equals(""))writeHomePage(index);
        // 上一页
        
        if(previousPage!=null && !previousPage.trim().equals(""))writePreviousPage(index);
        // 前置分隔符
        if(preSeparator!=null && !preSeparator.trim().equals(""))writePreSeparator(getPreSeparator());
        // 当前页之前的页码
        if(beforeNum!=0)writeBeforePageIndex(index, beforeCount);
        // 当前页
        if(beforeNum!=0 && afterNum!=0)writeCurrentPageIndex(index);
        // 当前页之后的页码
        if(afterNum!=0)writeAfterPageIndex(index, afterCount);
        // 后置分隔符
        if(nextSeparator!=null && !nextSeparator.trim().equals(""))writeNextSeparator(getNextSeparator());
        // 下一页
        if(nextPage!=null && !nextPage.trim().equals(""))writeNextPage(index);
        // 末页
        if(lastPage!=null && !lastPage.trim().equals(""))writeLastPage(index);
        
        writeJumpLink();
    }
    
    private void writeJumpLink() {
		buff.append("共"+page.getTotalPage()+
				"页  到第<input type='text' name='pageIndex' size='4' maxlength='6'/>页" +
				"<input type='button'  size='4' value='确定'/>");
	}

	private void writePreSeparator(String separator){
    	if(page.getPageIndex()-beforeNum>1)
    	{
    		buff.append(LABEL_START);
            writeUrlPageIndex(1, page.getPageSize(), String.valueOf(1));
            buff.append(LABEL_END+"\r\n");
            if(page.getPageIndex()-beforeNum>2)
            	buff.append(separator);
    	}
    	
    }
    
    private void writeNextSeparator(String separator){
    	if(page.getTotalPage()-page.getPageIndex()>afterNum 
    			&& page.getTotalPage()>beforeNum+afterNum+1)
    	{
    		buff.append(LABEL_START);
    		buff.append(separator);
    		buff.append(LABEL_END+"\r\n");
    	}
    }

    /**
     * 计算当前页之前的页数。
     * @return 当前页之前的页数。
     */
    private int countBefore() {
        int beforeCount = 0;
        if (page.getPageIndex() - 1 > beforeNum) {
            beforeCount = beforeNum;
        } else {
            beforeCount = page.getPageIndex() - 1;
        }

        return beforeCount;
    }

    /**
     * 计算当前页之后的页数。
     * @return 当前页之后的页数。
     */
    private int countAfter() {
        int afterCount = 0;
        if (page.getTotalPage() - page.getPageIndex() > afterNum) {
            afterCount = afterNum;
        } else {
            afterCount = page.getTotalPage() - page.getPageIndex();
        }

        return afterCount;
    }

    /**
     * 动态补足当前页之前的页数。
     * @param beforeCount 当前页之前的页数。
     * @param afterCount 当前页之后的页数。
     * @return 修正后的当前页之前的页数。
     */
    private int fixBeforeCount(int beforeCount, int afterCount) {
        int totalNum = beforeNum + afterNum + 1;
        int useNum = beforeCount + afterCount + 1;

        if (useNum < totalNum) {
            int befores = page.getPageIndex() - 1;
            int margin = befores - beforeCount;
            if (margin > 0) {
                int needNum = totalNum - useNum;
                beforeCount += margin > needNum ? needNum : margin;
            }
        }

        return beforeCount;
    }

    /**
     * 动态补足当前页之后的页数。
     * @param beforeCount 当前页之前的页数。
     * @param afterCount 当前页之后的页数。
     * @return 修正后的当前页之后的页数。
     */
    private int fixAfterCount(int beforeCount, int afterCount) {
        int totalNum = beforeNum + afterNum + 1;
        int useNum = beforeCount + afterCount + 1;

        if (useNum < totalNum) {
            int afters = page.getTotalPage() - page.getPageIndex();
            int margin = afters - afterCount;
            if (margin > 0) {
                int needNum = totalNum - useNum;
                afterCount += margin > needNum ? needNum : margin;
            }
        }

        return afterCount;
    }

    /**
     * 写入首页信息，如果设定了显示首页。
     * @param index 当前页码。
     */
	private void writeHomePage(int index) {
        if (homePage == null || homePage.isEmpty()) {
            return;
        }

        buff.append(LABEL_START);
        int homeIndex = 1;
        if (index > homeIndex) {
            writeUrlPageIndex(homeIndex, page.getPageSize(), homePage);
        } else {
            buff.append(homePage);
        }
        buff.append(LABEL_END+"\r\n");
    }

    /**
     * 写入末页信息，如果设定了显示末页。
     * @param index 当前页码。
     */
	private void writeLastPage(int index) {
        if (lastPage == null || lastPage.isEmpty()) {
            return;
        }

        buff.append(LABEL_START);
        int lastIndex = page.getTotalPage();
        if (index < lastIndex) {
            writeUrlPageIndex(lastIndex, page.getPageSize(), lastPage);
        } else {
            buff.append(lastPage);
        }
        buff.append(LABEL_END+"\r\n");
    }

    /**
     * 写入上一页信息，如果设定了显示上一页。
     * @param index 当前页码。
     */
    private void writePreviousPage(int index) {
        if (index<=1 || previousPage == null || previousPage.isEmpty()) {
            return;
        }

        buff.append(LABEL_START);
        if (index > 1) {
            writeUrlPageIndex(index - 1, page.getPageSize(), previousPage);
        } else {
            buff.append(previousPage);
        }
        buff.append(LABEL_END+"\r\n");
    }

    /**
     * 写入下一页信息，如果设定了显示下一页。
     * @param index 当前页码。
     */
    private void writeNextPage(int index) {
        if (index>=page.getTotalPage() || nextPage == null || nextPage.isEmpty()) {
            return;
        }

        buff.append(LABEL_START);
        if (index < page.getTotalPage()) {
            writeUrlPageIndex(index + 1, page.getPageSize(), nextPage);
        } else {
            buff.append(nextPage);
        }
        buff.append(LABEL_END+"\r\n");
    }

    /**
     * 写入当前页之前的页码。
     * @param index 当前页码。
     * @param beforeCount 前页之前的页数。
     */
    private void writeBeforePageIndex(int index, int beforeCount) {
        int beginIndex = index - beforeCount < 0 ? 1 : index - beforeCount;
        for (int i = beginIndex; i < index; i++) {
            buff.append(LABEL_START);
            writeUrlPageIndex(i, page.getPageSize(), String.valueOf(i));
            buff.append(LABEL_END+"\r\n");
        }
    }

    /**
     * 写入当前页之后的页码。
     * @param index 当前页码。
     * @param afterCount 前页之后的页数。
     */
    private void writeAfterPageIndex(int index, int afterCount) {
    	int endIndex = 1;
    	if(index<this.beforeNum+1)
    		endIndex = page.getTotalPage();
    	else
    		endIndex = index + afterCount > page.getTotalPage()
            ? page.getTotalPage() : index + afterCount;
        for (int i = index + 1; i <= endIndex; i++) {
            buff.append(LABEL_START);
            writeUrlPageIndex(i, page.getPageSize(), String.valueOf(i));
            buff.append(LABEL_END+"\r\n");
        }
    }

    /**
     * 写入当前页的页码。
     * @param index 当前页码。
     */
    private void writeCurrentPageIndex(int index) {
        buff.append(SPAN_START);
        buff.append(index);
        buff.append(SPAN_END+"\r\n");
    }

    /**
     * 写入开始div，如果指定样式divClass，那么样式也将被写入。
     */
    private void writeBeginDiv() {
        buff.append("<div");
        if (divClass != null && !divClass.isEmpty()) {
            buff.append(" class=\"");
            buff.append(divClass);
            buff.append("\"");
        }
        buff.append(">");
    }

    /**
     * 写入结束div。
     */
    private void writeEndDiv() {
        buff.append("</div>");
    }

    /**
     * 将分页信息输入到页面上。
     */
    @SuppressWarnings("unused")
	private void printToPage() {
        try {
            pageContext.getOut().write(buff.toString());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    /**
     * 写入添加链接地址url后的指定内容，并在链接地址url后添加pageIndex参数，
     * 如果指定了pageSize参数，也会将此参数添加到链接地址url中。
     * @param index 当前页码。
     * @param size 页面大小。
     * @param body 写入内容。
     */
    private void writeUrlPageIndex(int index, int size, String body) {
	        buff.append("<a href=\"");
	        buff.append(url);
//	        if (url.lastIndexOf("?") > 0) {
//	            buff.append("&");
//	        } else {
//	            buff.append("?");
//	        }
//	        buff.append(pageIndex);
//	        buff.append("=");
	        buff.append("/"+index);
	        if (pageSize != null && !pageSize.isEmpty()) {
//	            buff.append("&");
//	            buff.append(pageSize);
//	            buff.append("=");
	            buff.append("/"+size);
	        }
	        buff.append("\">");
	        buff.append(body);
	        buff.append("</a>");
    }

    /**
     * 将构造好的分页信息以debug级别记录到日志中。
     */
    private void writeDebugLog() {
        if (log.isDebugEnabled()) {
            log.debug(buff.toString());
        }
    }

    /**
     * 设置首页的显示内容。
     * @param homePage 首页。
     */
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    /**
     * 设置末页的显示内容。
     * @param lastPage 末页。
     */
    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * 设置上一页的显示内容。
     * @param previousPage 上一页。
     */
    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    /**
     * 设置下一页的显示内容。
     * @param nextPage 下一页。
     */
    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * 设置当前页之前显示的页数。
     * @param beforeNum 当前页之前显示的页数。
     */
    public void setBeforeNum(int beforeNum) {
        this.beforeNum = beforeNum;
    }

    /**
     * 设置当前页之后显示的页数。
     * @param afterNum 当前页之后显示的页数。
     */
    public void setAfterNum(int afterNum) {
        this.afterNum = afterNum;
    }

    /**
     * 设置分页信息。
     * @param page 分页信息。
     */
    @SuppressWarnings("rawtypes")
	public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 设置点击页码转向的url链接地址。
     * @param url 链接地址。
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置当前页码参数名称。
     * @param pageIndex 当前页码参数名称。
     */
    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 设置页面大小参数名称。
     * @param pageSize 页面大小参数名称。
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
    

    public String getNextSeparator() {
		return nextSeparator;
	}

	public void setNextSeparator(String nextSeparator) {
		this.nextSeparator = nextSeparator;
	}

	public String getPreSeparator() {
		return preSeparator;
	}

	public void setPreSeparator(String preSeparator) {
		this.preSeparator = preSeparator;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	/**
     * 设置外层div样式。
     * @param divClass 样式名称。
     */
    public void setDivClass(String divClass) {
        this.divClass = divClass;
    }

    /**
     * 设置是否对当前页面之前和之后的页数进行动态补足。
     * @param supplement 是否进行动态补足。
     *      <tt>true</tt>进行动态补足，<tt>false</tt>不进行动态补足。
     */
    public void setSupplement(boolean supplement) {
        this.supplement = supplement;
    }
    
    public void printTag() {
        System.out.println(buff.toString());
    }
}
