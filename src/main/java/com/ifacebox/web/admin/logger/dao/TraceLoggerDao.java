package com.ifacebox.web.admin.logger.dao;

import com.ifacebox.web.admin.logger.model.TraceLogger;
import com.ifacebox.web.admin.logger.model.TraceLoggerQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface TraceLoggerDao {

    @Select({"<script>"
            , "SELECT * FROM trace_logger"
            , "<where>"
            , "<if test='startTime != null and !startTime.isEmpty()'>AND create_time &gt;= #{startTime}</if>"
            , "<if test='endTime != null and !endTime.isEmpty()'>AND create_time &lt;= #{endTime}</if>"
            , "</where>"
            , "ORDER BY create_time DESC LIMIT #{offset}, #{limit}"
            , "</script>"})
    List<TraceLogger> query(TraceLoggerQuery traceLoggerQuery);

    @Select({"<script>"
            , "SELECT COUNT(*) FROM trace_logger"
            , "<where>"
            , "<if test='startTime != null and !startTime.isEmpty()'>AND create_time &gt;= #{startTime}</if>"
            , "<if test='endTime != null and !endTime.isEmpty()'>AND create_time &lt;= #{endTime}</if>"
            , "</where>"
            , "</script>"})
    Long total(TraceLoggerQuery traceLoggerQuery);

    @Insert({"<script>"
            , "INSERT INTO trace_logger"
            , "( user_id, ip,os, browser, user_agent, message, cost_millis )"
            , "VALUES ( #{userId}, #{ip}, #{os}, #{browser}, #{userAgent}, #{message}, #{costMillis} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(TraceLogger traceLogger);

    @Delete({"<script>"
            , "DELETE FROM trace_logger WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
