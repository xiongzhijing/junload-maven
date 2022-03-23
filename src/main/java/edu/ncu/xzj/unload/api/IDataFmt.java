package edu.ncu.xzj.unload.api;

import java.sql.ResultSet;

public interface IDataFmt {
    StringBuilder format(ResultSet rs);
}
