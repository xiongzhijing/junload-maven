package edu.ncu.xzj.unload.impl;

import edu.ncu.xzj.unload.api.ICLIParse;
import edu.ncu.xzj.unload.api.IDataFmt;
import edu.ncu.xzj.unload.api.IUnload;
import edu.ncu.xzj.unload.api.IUnloadDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 熊志京
 * @version 1.0
 * @classname Unload
 * @description 导出实现，将命令行解析器、dao、格式化器等构造好
 * @date 2022/3/19 22:59
 */
public class UnloadImpl implements IUnload {

    private IDataFmt dataFmt;
    private ICLIParse opt;
    private IUnloadDao dao;

    final static String DEFAULT_DELI = "|@|";
    final static String DEFAULT_DATEPATTERN = "yyyy-MM-dd HH:mm:ss";

    /*
     * 1、解析命令行参数
     * 2、准备好数据库连接参数
     */
    public UnloadImpl(String[] args) {
        opt = new CLIParseImpl();
        opt.parseCLI(args);
        dao = new UnloadDaoImpl(opt);

        String delimiter = opt.getOpts().getOrDefault("delimiter", DEFAULT_DELI);
        String pattern = opt.getOpts().getOrDefault("datepattern", DEFAULT_DATEPATTERN);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        dataFmt = new DataFmtImpl(delimiter, sdf);
    }

    /* 根据命令行参数卸载数据
     * 返回值：卸出行数
     */
    @Override
    public long unload() throws IOException {
        final JdbcTemplate jdbcTemplate = dao.getJdbcTemplate();
        final String filename = opt.getOpts().get("output");
        final String sql = opt.getOpts().get("sql");
        AtomicInteger rows = new AtomicInteger();
        try (OutputStreamWriter fd = new OutputStreamWriter(new FileOutputStream(filename));) {
            jdbcTemplate.query(sql, rs -> {
                final StringBuilder sb = dataFmt.format(rs);
                try {
                    fd.write(sb.toString() + "\n");
                    int n = rows.incrementAndGet();
                    if (n % 10000 == 0 && n > 0) {
                        System.out.println(String.format("Unloaded %5d lines.", n));
                    }

                } catch (IOException e) {
                    System.err.println(e);
                }
            });
        }
        return rows.get();
    }
}
