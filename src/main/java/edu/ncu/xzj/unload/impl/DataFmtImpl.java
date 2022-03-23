package edu.ncu.xzj.unload.impl;

import edu.ncu.xzj.unload.api.IDataFmt;

import java.sql.ResultSet;

/**
 * @author 熊志京
 * @version 1.0
 * @classname DataFmt
 * @description 查询结果按行格式化
 * @date 2022/3/19 22:59
 */
public class DataFmtImpl implements IDataFmt {

    private String delimiter;

    public DataFmtImpl(String delimiter) {
        this.delimiter = delimiter;
    }


    @Override
    public StringBuilder format(ResultSet rs) {
        final StringBuilder sb = new StringBuilder();
        try {
            final int cols = rs.getMetaData().getColumnCount();
            for (int i = 0; i < cols; i++) {
                String value = rs.getString(i + 1);
                if (value == null) value = "";
                sb.append(value.trim()).append(delimiter);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return sb;
    }
}
