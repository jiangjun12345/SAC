/**
 * JustCommodity Software Solutions<br/>
 * <br/>
 * Copyright © 2011
 */
package net.easipay.cbp.util;

public class Paging {

  private int currentPage = 1;
  private int totalRows;
  private int totalPages;
  private int maxRecordsOnePage;

  /**
   * construct function with no parameters
   */
  public Paging() {

  }

  public int getMaxRecordsOnePage() {
    return maxRecordsOnePage;
  }

  public void setMaxRecordsOnePage(int maxRecordsOnePage) {
    this.maxRecordsOnePage = maxRecordsOnePage;
  }

  public Paging(int currentPage, int totalRows) {
    super();
    this.currentPage = currentPage;
    this.totalRows = totalRows;
  }

  public Paging(int currentPage, int totalRows, int maxRecordsOnePage) {
    super();
    this.currentPage = currentPage;
    this.totalRows = totalRows;
    this.maxRecordsOnePage = maxRecordsOnePage;
  }

  public Integer getNext() {
    if (currentPage + 1 >= getMaxPages()) {
      return totalPages;
    } else {
      return currentPage = currentPage + 1;
    }

  }

  public Integer getEndElement() {
    int endElement = currentPage * maxRecordsOnePage;
    if (endElement >= totalRows) {
      return totalRows;
    } else {
      return endElement;
    }
  }

  public Integer getFirst() {

    return this.currentPage = 1;
  }

  public Integer getLast() {
    return getMaxPages();
  }

  public Integer getBeginElement() {
    return (currentPage - 1) * maxRecordsOnePage;
  }

  public Integer getPrevious() {
    if (currentPage - 1 <= 1) {
      return 1;
    } else {
      return currentPage = currentPage - 1;
    }
  }

  public void setPageNumber(Integer pageNumber) {
    if (pageNumber > getMaxPages()) {
      this.currentPage = totalPages;
    } else if (pageNumber < 1) {
      this.currentPage = 1;
    } else {
      this.currentPage = pageNumber;
    }
  }

  public Integer getMaxPages() {
    if (totalRows != 0 && (totalRows % maxRecordsOnePage == 0)) {
      totalPages = totalRows / maxRecordsOnePage;
    } else {
      totalPages = totalRows / maxRecordsOnePage + 1;
    }
    return totalPages;
  }

  /**
   * @return the currentPage
   */
  public int getCurrentPage() {
    return currentPage;
  }

  public int getTotalRows() {
    return totalRows;
  }

  public void setTotalRows(int totalRows) {
    this.totalRows = totalRows;
  }

  /**
   * @param currentPage the currentPage to set
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

}