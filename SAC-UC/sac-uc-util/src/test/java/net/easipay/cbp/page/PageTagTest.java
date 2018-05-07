package net.easipay.cbp.page;


import javax.servlet.jsp.JspException;

import net.easipay.cbp.page.Page;
import net.easipay.cbp.page.PageTag;

import org.junit.Test;

/**
 * 分页标签处理类的相关测试。
 * @version 1.00 2010-5-25, 18:33:00
 * @since 1.5
 * @author ZhangShixi
 */
public class PageTagTest{

    @SuppressWarnings("rawtypes")
	@Test
    public void writePage() throws JspException {
//        PageTag instance = new PageTag();

//        Page page = new Page();
//        page.setTotalData(21);
//
//        instance.setPage(page);
//        instance.setDivClass("divClass");
//        instance.setHomePage("首页");
//        instance.setLastPage("末页");
//        instance.setPreviousPage("上一页");
//        instance.setNextPage("下一页");
//        instance.setPageIndex("pageIndex");
//        instance.setPageSize("pageSize");
//        instance.setBeforeNum(4);
//        instance.setAfterNum(8);
//        instance.setSupplement(false);
//        instance.setUrl("http://zhangshixi.javaeye.com");
//
//        instance.doStartTag();
//        instance.doEndTag();
//        //instance.printTag();
        
        PageTag instance = new PageTag();

        Page page = new Page(8, 10);
        page.setTotalData(127);

        instance.setPage(page);
        instance.setDivClass("divClass");
//        instance.setHomePage("首页");
//        instance.setLastPage("末页");
        instance.setPreviousPage("上一页");
        instance.setPreSeparator("***");
        instance.setNextPage("下一页");
        instance.setNextSeparator("***");
        instance.setPageIndex("pageIndex");
        instance.setPageSize("pageSize");
        instance.setBeforeNum(4);
        instance.setAfterNum(4);
        instance.setSupplement(false);
        instance.setUrl("http://zhangshixi.javaeye.com");

        instance.doStartTag();
        instance.doEndTag();
    }
}
