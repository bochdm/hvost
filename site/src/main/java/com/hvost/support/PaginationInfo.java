package com.hvost.support;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by kseniaselezneva on 13/02/15.
 */
public class PaginationInfo {
    private final long currentPage;
    private final long totalPage;

    public PaginationInfo(Page<?> page) {
        this.currentPage = page.getNumber() + 1;
        this.totalPage = page.getTotalPages();

        System.out.println("currentPage = " + currentPage);
        System.out.println("totalPage = " + totalPage);
    }

    public long getNextPageNumber(){
        return currentPage + 1;
    }

    public long getPreviousPageNumber(){
        return currentPage - 1;
    }

    public boolean isVisible(){
        return isPreviousVisible() || isNextVisible();
    }

    public boolean isPreviousVisible(){ return currentPage > 1; }

    public boolean isNextVisible() {return currentPage < totalPage; }

    public List<PageElement> getPageElement(){
        List<PageElement> pageElements =  new PageElementsBuilder(currentPage, totalPage).build();

        for(PageElement p : pageElements)
            System.out.println("p->" + p.toString());

        return pageElements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaginationInfo that = (PaginationInfo) o;

        if (currentPage != that.currentPage) return false;
        if (totalPage != that.totalPage) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (currentPage ^ (currentPage >>> 32));
        result = 31 * result + (int) (totalPage ^ (totalPage >>> 32));
        return result;
    }
}
