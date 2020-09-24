package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterRoleDao {

    @Select({"<script>"
            , "SELECT * FROM master_role"
            , "</script>"})
    List<MasterRole> query();

    @Select({"<script>"
            , "SELECT COUNT(*) FROM master_role"
            , "</script>"})
    Long total();

    @Select({"<script>"
            , "SELECT * FROM master_role WHERE id = #{id}"
            , "</script>"})
    MasterRole queryById(Integer id);

    @Select({"<script>"
            , "SELECT * FROM master_role WHERE role_name = #{roleName}"
            , "</script>"})
    MasterRole queryByRoleName(String roleName);

    @Insert({"<script>"
            , "INSERT INTO master_role"
            , "( role_name, role_explain, status_id, create_user )"
            , "VALUES ( #{roleName}, #{roleExplain}, #{statusId}, #{createUser} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterRole masterRole);

    @Update({"<script>"
            , "UPDATE master_role SET"
            , "role_name = #{roleName}, role_explain = #{roleExplain}, status_id = #{statusId}, update_user = #{updateUser}, update_time = now()"
            , " WHERE id = #{id}"
            , "</script>"})
    Integer edit(MasterRole masterRole);

    @Delete({"<script>"
            , "DELETE FROM master_role WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
