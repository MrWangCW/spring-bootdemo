package com.wang.util.excel;

import org.apache.poi.ss.usermodel.Row;

/**
 * @author tiny on 2018/10/31.
 */
@FunctionalInterface
public interface ReadDataCallback<T> {
    /**
     * 读取数据回调
     * @param row   行项
     * @return      数据封装
     */
    T call(Row row);
}
