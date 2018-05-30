package com.zjht.youoil.util.sort;

/**
 * 排序对象封装类
 * int sort排序值
 * Object sortObject排序对象
 * @author lijunjie
 * @since 2017-08-18 10:33:52
 * @version 1.0
 */
public class SortEntity {
    public SortEntity(){}
    public SortEntity(int sort, Object sortObject) {
        this.sort = sort;
        this.sortObject = sortObject;
    }
    private int sort;//排序值
    private Object sortObject;//排序对象
    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }
    public Object getSortObject() {
        return sortObject;
    }
    public void setSortObject(Object sortObject) {
        this.sortObject = sortObject;
    }
}
