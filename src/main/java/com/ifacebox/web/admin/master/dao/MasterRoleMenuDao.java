package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterRoleMenu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterRoleMenuDao {

    @Select({"<script>"
            , "SELECT * FROM master_role_menu WHERE role_id = #{roleId}"
            , "</script>"})
    List<MasterRoleMenu> queryByRoleId(Integer roleId);

    @Select({"<script>"
            , "SELECT * FROM master_role_menu WHERE menu_id = #{menuId}"
            , "</script>"})
    List<MasterRoleMenu> queryByMenuId(Integer menuId);

    @Insert({"<script>"
            , "INSERT INTO master_role_menu ( role_id, menu_id ) VALUES ( #{roleId}, #{menuId} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterRoleMenu masterRoleMenu);


    @Delete({"<script>"
            , "DELETE FROM master_role_menu WHERE role_id = #{roleId}"
            , "</script>"})
    Integer delete(Integer roleId);
}
