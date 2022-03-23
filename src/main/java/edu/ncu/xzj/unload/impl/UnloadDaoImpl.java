package edu.ncu.xzj.unload.impl;

import com.alibaba.druid.pool.DruidDataSource;
import edu.ncu.xzj.unload.api.ICLIParse;
import edu.ncu.xzj.unload.api.IUnloadDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author 熊志京
 * @version 1.0
 * @classname UnloadDaoImpl
 * @description 数据库初始准备阶段
 * @date 2022/3/22 16:43
 */
public class UnloadDaoImpl implements IUnloadDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private ICLIParse opts;

    public UnloadDaoImpl(ICLIParse opts) {
        this.opts = opts;
        initDataSource();
    }

    protected void initDataSource() {

        final DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(opts.getOpts().get("jdbc.driver"));
        dataSource.setUrl(opts.getOpts().get("jdbc.url"));
        dataSource.setPoolPreparedStatements(true);
        dataSource.setUsername(opts.getOpts().get("jdbc.user"));
        dataSource.setPassword(opts.getOpts().get("jdbc.password"));
        dataSource.setLoginTimeout(Integer.parseInt(opts.getOpts().get("jdbc.timeout")));

        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
