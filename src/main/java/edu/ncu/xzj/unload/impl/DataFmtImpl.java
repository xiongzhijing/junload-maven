package edu.ncu.xzj.unload.impl;

import edu.ncu.xzj.unload.api.IDataFmt;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;

/**
 * @author 熊志京
 * @version 1.0
 * @classname DataFmt
 * @description 查询结果按行格式化
 * @date 2022/3/19 22:59
 */
public class DataFmtImpl implements IDataFmt {

    private String delimiter;
    private SimpleDateFormat simpleDateFormat;

    public DataFmtImpl(String delimiter, SimpleDateFormat sdf) {
        this.delimiter = delimiter;
        simpleDateFormat = sdf;
    }

    @Override
    public StringBuilder format(ResultSet rs) {
        final StringBuilder sb = new StringBuilder();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            final int cols = meta.getColumnCount();

            String value = "";
            for (int i = 0; i < cols; i++) {
                if (meta.getColumnType(i + 1) == JDBCType.DATE.getVendorTypeNumber()) {
                    value = simpleDateFormat.format(rs.getDate(i + 1));
                } else {
                    value = rs.getString(i + 1);
                    if (value == null) value = "";

                }
                sb.append(value.trim()).append(delimiter);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return sb;
    }
}
