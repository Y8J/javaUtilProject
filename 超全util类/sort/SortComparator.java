package com.zjht.youoil.util.sort;

import java.util.Comparator;

/**
 * 自定义List排序对象比较器
 * 根据排序对象的sort值排序，sort值越大，排序越靠前
 * @author lijunjie
 * @since 2017-08-18 10:36:49
 * @version 1.0
 */
public class SortComparator implements Comparator<SortEntity> {

    @Override
    public int compare(SortEntity arg0, SortEntity arg1) {
        if (arg0.getSort()==arg1.getSort()) {
            return 0;
        }
        return arg0.getSort()<arg1.getSort()?1:-1;
    }

}
