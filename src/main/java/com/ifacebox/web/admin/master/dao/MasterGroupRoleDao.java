package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterGroupRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterGroupRoleDao {

    @Select({"<script>"
            , "SELECT * FROM master_group_role WHERE group_id = #{groupId}"
            , "</script>"})
    List<MasterGroupRole> queryByGroupId(Integer groupId);

    @Select({"<script>"
            , "SELECT * FROM master_group_role WHERE role_id = #{roleId}"
            , "</script>"})
    List<MasterGroupRole> queryByRoleId(Integer roleId);

    @Insert({"<script>"
            , "INSERT INTO master_group_role ( group_id, role_id ) VALUES ( #{groupId}, #{roleId} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterGroupRole groupRole);

    @Delete({"<script>"
            , "DELETE FROM master_group_role WHERE group_id = #{groupId}"
            , "</script>"})
    Integer delete(Integer groupId);
}
