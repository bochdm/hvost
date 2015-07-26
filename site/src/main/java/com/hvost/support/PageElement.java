package com.hvost.support;


/**
 * Created by kseniaselezneva on 13/02/15.
 */
public class PageElement {
    private final String label;
    private final boolean isNavigable;
    private final boolean isCurrentPage;

    public PageElement(long pageNumber, boolean isNavigable, boolean isCurrentPage){
        this(pageNumber +"", isNavigable, isCurrentPage);
    }

    public PageElement(String label, boolean isNavigable, boolean isCurrentPage) {
        this.label = label;
        this.isNavigable = isNavigable;
        this.isCurrentPage = isCurrentPage;
    }

    public String getLabel() {
        return label;
    }

    public boolean isNavigable() {
        return isNavigable;
    }

    public boolean isCurrentPage() {
        return isCurrentPage;
    }

    @Override
    public String toString() {
        return "PageElement{" +
                "label='" + label + '\'' +
                ", isNavigable=" + isNavigable +
                ", isCurrentPage=" + isCurrentPage +
                '}';
    }
}
