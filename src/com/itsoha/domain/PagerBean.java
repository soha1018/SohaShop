package com.itsoha.domain;

import java.util.List;

/**
 * 页面分页
 */
public class PagerBean<T> {
    /**
     * 当前页面
     */
    private int currentPager;
    /**
     * 当前页面显示数据的总数
     */
    private int currentCount;
    /**
     * 类别所有项目的总数
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPager;
    private List<T> list;

    public int getTotalPager() {
        return totalPager;
    }

    public void setTotalPager(int totalPager) {
        this.totalPager = totalPager;
    }

    public int getCurrentPager() {
        return currentPager;
    }

    public void setCurrentPager(int currentPager) {
        this.currentPager = currentPager;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PagerBean{" +
                "currentPager=" + currentPager +
                ", currentCount=" + currentCount +
                ", totalCount=" + totalCount +
                ", totalPager=" + totalPager +
                ", list=" + list +
                '}';
    }
}
