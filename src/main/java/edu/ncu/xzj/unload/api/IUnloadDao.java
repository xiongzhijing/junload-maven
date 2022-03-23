package edu.ncu.xzj.unload.api;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author 熊志京
 * @version 1.0
 * @classname IUnloadDao
 * @description unload 数据库操作层接口
 * @date 2022/3/22 16:41
 */
public interface IUnloadDao {

    DataSource getDataSource();

    JdbcTemplate getJdbcTemplate();
}
