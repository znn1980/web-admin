package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterMenu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterMenuDao {

    @Select({"<script>"
            , "SELECT * FROM master_menu WHERE parent_id = #{parentId} ORDER BY order_id"
            , "</script>"})
    List<MasterMenu> queryByParentId(Integer parentId);

    @Select({"<script>"
            , "SELECT master_menu.* FROM"
            , "master_menu, master_role_menu, master_group_role, master_user_group, master_role, master_group, master_user"
            , "WHERE master_user.id = master_user_group.user_id"
            , "AND master_user_group.group_id = master_group.id"
            , "AND master_user_group.group_id = master_group_role.group_id "
            , "AND master_group_role.role_id = master_role.id"
            , "AND master_group_role.role_id = master_role_menu.role_id"
            , "AND master_role_menu.menu_id = master_menu.id"
            , "AND master_group.status_id = 1 AND master_role.status_id = 1"
            , "AND master_menu.attr_id = 1 AND master_menu.status_id = 1"
            , "AND master_menu.parent_id = #{parentId} AND master_user.id = #{userId}"
            , "ORDER BY master_menu.order_id"
            , "</script>"})
    List<MasterMenu> queryByParentIdAndUserId(Integer parentId, Integer userId);


    @Select({"<script>"
            , "SELECT master_menu.* FROM"
            , "master_menu, master_role_menu, master_group_role, master_user_group, master_role, master_group, master_user"
            , "WHERE master_user.id = master_user_group.user_id"
            , "AND master_user_group.group_id = master_group.id"
            , "AND master_user_group.group_id = master_group_role.group_id "
            , "AND master_group_role.role_id = master_role.id"
            , "AND master_group_role.role_id = master_role_menu.role_id"
            , "AND master_role_menu.menu_id = master_menu.id"
            , "AND master_group.status_id = 1 AND master_role.status_id = 1"
            , "AND master_menu.attr_id = 1 AND master_menu.status_id = 1"
            , "AND master_user.id = #{userId}"
            , "</script>"})
    List<MasterMenu> queryByUserId(Integer userId);

    @Select({"<script>"
            , "SELECT * FROM master_menu WHERE id = #{id}"
            , "</script>"})
    MasterMenu queryById(Integer id);

    @Select({"<script>"
            , "SELECT * FROM master_menu WHERE title = #{title}"
            , "</script>"})
    MasterMenu queryByTitle(String title);

    @Insert({"<script>"
            , "INSERT INTO master_menu"
            , "( parent_id, title, address, attr_id, icon_cls, status_id, order_id, create_user )"
            , "VALUES ( #{parentId}, #{title}, #{address}, #{attrId}, #{iconCls}, #{statusId}, #{orderId}, #{createUser} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterMenu masterMenu);

    @Update({"<script>"
            , "UPDATE master_menu SET"
            , " parent_id = #{parentId}, title = #{title}, address = #{address}, attr_id = #{attrId}, icon_cls = #{iconCls}"
            , ", status_id = #{statusId}, order_id = #{orderId}, update_user = #{updateUser}, update_time = now()"
            , " WHERE id = #{id}"
            , "</script>"})
    Integer edit(MasterMenu masterMenu);

    @Delete({"<script>"
            , "DELETE FROM master_menu WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
