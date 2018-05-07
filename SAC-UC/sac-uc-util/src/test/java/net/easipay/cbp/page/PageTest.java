package net.easipay.cbp.page;


import net.easipay.cbp.page.Page;
import net.easipay.cbp.page.PageScope;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 分页工具的相关方法测试。
 * @version 1.00 2010-4-28
 * @since 1.5
 * @author ZhangShixi
 */
@SuppressWarnings("rawtypes")
public class PageTest {

    /**
     * 测试构造一个仅表示第一页的分页对象。
     */
   
	@Test
    public void newFirstPage() {
        Page page = Page.newFirstPage(10);
        PageScope scope = page.getPageScope();

        assertTrue(page.isFirstPage());
        assertEquals(10, page.getTotalData());
        assertEquals(1, page.getTotalPage());
        assertEquals(1, page.getPageIndex());
        assertEquals(10, page.getPageSize());
        assertEquals(0, scope.getStartLine());
        assertEquals(9, scope.getEndLine());
    }

    /**
     * 测试构造一个指定当前页数和页面大小的分页对象。
     */
    @Test
    public void newPage() {
        Page page = new Page(2, 10);
        page.setTotalData(55);
        PageScope scope = page.getPageScope();

        assertFalse(page.isFirstPage());
        assertEquals(55, page.getTotalData());
        assertEquals(6, page.getTotalPage());
        assertEquals(2, page.getPageIndex());
        assertEquals(10, page.getPageSize());
        assertEquals(10, scope.getStartLine());
        assertEquals(19, scope.getEndLine());
    }

    /**
     * 测试构造一个具有默认当前页数和页面大小的分页对象。
     */
    @Test
    public void newPage_default() {
        Page page = new Page();
        page.setTotalData(55);
        PageScope scope = page.getPageScope();

        assertFalse(page.isFirstPage());
        assertEquals(55, page.getTotalData());
        assertEquals(6, page.getTotalPage());
        assertEquals(1, page.getPageIndex());
        assertEquals(10, page.getPageSize());
        assertEquals(0, scope.getStartLine());
        assertEquals(9, scope.getEndLine());
    }

    /**
     * 测试传递不合法的参数时，分页对象的容错性。
     */
    @Test
    public void newPage_illegalArgument() {
        Page page = new Page(20, 10);
        page.setTotalData(55);
        PageScope scope = page.getPageScope();

        assertFalse(page.isFirstPage());
        assertEquals(55, page.getTotalData());
        assertEquals(6, page.getTotalPage());
        assertEquals(6, page.getPageIndex());
        assertEquals(10, page.getPageSize());
        assertEquals(50, scope.getStartLine());
        assertEquals(54, scope.getEndLine());


        page = new Page(-5, -10);
        page.setTotalData(55);
        scope = page.getPageScope();

        assertFalse(page.isFirstPage());
        assertEquals(55, page.getTotalData());
        assertEquals(6, page.getTotalPage());
        assertEquals(1, page.getPageIndex());
        assertEquals(10, page.getPageSize());
        assertEquals(0, scope.getStartLine());
        assertEquals(9, scope.getEndLine());
    }
}
