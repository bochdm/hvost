package com.hvost.support;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kseniaselezneva on 13/02/15.
 */
public class PageElementsBuilder {
    private final long currentPage;
    private final long totalPage;
    private long       startPage;
    private long       endPage;

    public PageElementsBuilder(long currentPage, long totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public List<PageElement> build(){
        List<PageElement> elements = new ArrayList<PageElement>();

        findStartPage();
        findEndPage();

        if (startPage > 1)
            addFirstPage(elements);

        addPageElement(elements);

        if (endPage < totalPage)
            addLastPage(elements);

        return elements;
    }

    private void findStartPage(){
        long previousTwoPages = Math.max((currentPage - 2), 1L);
        startPage = previousTwoPages;
    }

    private void findEndPage(){
        long nextThreePages = currentPage + 3;
        long desiredLastPage = Math.max(6, nextThreePages);
        endPage = Math.min(desiredLastPage, totalPage);
    }

    private void addPageElement(List<PageElement> elements){
        for (long n = startPage; n <= endPage; n++ ){
            boolean isCurrentPage = n == currentPage;
            boolean isNavigable = !isCurrentPage;

            elements.add(new PageElement(n, isNavigable, isCurrentPage));
        }
    }

    private void addEllipsis(List<PageElement> elements){
        elements.add(new PageElement("...", false, false));
    }

    private void addLastPage(List<PageElement> elements){
        if (endPage < totalPage -1)
            addEllipsis(elements);

        elements.add(new PageElement(totalPage, true, false));
    }

    private void addFirstPage(List<PageElement> elements){
        elements.add(new PageElement(1, true, false));
        if (startPage > 2)
            addEllipsis(elements);
    }

}


