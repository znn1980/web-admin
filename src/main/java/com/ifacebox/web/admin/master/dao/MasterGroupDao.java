package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterGroup;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterGroupDao {

    @Select({"<script>"
            , "SELECT * FROM master_group"
            , "</script>"})
    List<MasterGroup> query();

    @Select({"<script>"
            , "SELECT COUNT(*) FROM master_group"
            , "</script>"})
    Long total();

    @Select({"<script>"
            , "SELECT * FROM master_group WHERE id = #{id}"
            , "</script>"})
    MasterGroup queryById(Integer id);

    @Select({"<script>"
            , "SELECT * FROM master_group WHERE group_name = #{groupName}"
            , "</script>"})
    MasterGroup queryByGroupName(String groupName);

    @Insert({"<script>"
            , "INSERT INTO master_group"
            , "( group_name, group_explain, status_id, create_user )"
            , "VALUES ( #{groupName}, #{groupExplain}, #{statusId}, #{createUser} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterGroup masterGroup);

    @Update({"<script>"
            , "UPDATE master_group SET"
            , "group_name = #{groupName}, group_explain = #{groupExplain}, status_id = #{statusId}, update_user = #{updateUser}, update_time = now()"
            , " WHERE id = #{id}"
            , "</script>"})
    Integer edit(MasterGroup masterGroup);

    @Delete({"<script>"
            , "DELETE FROM master_group WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
