package com.wang.util.excel;

import org.apache.poi.ss.usermodel.Row;

/**
 * @author tiny on 2018/10/31.
 */
public interface FillDataCallback<T> {
    /**
     * 数据填充回调
     *
     * @param row  当前行
     * @param data 当前迭代数据
     */
    void call(Row row, T data);
}
