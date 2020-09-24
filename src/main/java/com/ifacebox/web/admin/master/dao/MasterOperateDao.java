package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterOperate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterOperateDao {

    @Select({"<script>"
            , "SELECT * FROM master_operate WHERE parent_id = #{parentId} ORDER BY order_id"
            , "</script>"})
    List<MasterOperate> queryByParentId(Integer parentId);

    @Select({"<script>"
            , "SELECT master_operate.* FROM"
            , "master_operate, master_role_operate, master_group_role, master_user_group, master_role, master_group, master_user"
            , "WHERE master_user.id = master_user_group.user_id"
            , "AND master_user_group.group_id = master_group.id"
            , "AND master_user_group.group_id = master_group_role.group_id "
            , "AND master_group_role.role_id = master_role.id"
            , "AND master_group_role.role_id = master_role_operate.role_id"
            , "AND master_role_operate.operate_id = master_operate.id"
            , "AND master_group.status_id = 1 AND master_role.status_id = 1"
            , "AND master_operate.attr_id = 1 AND master_operate.status_id = 1"
            , "AND master_user.id = #{userId}"
            , "</script>"})
    List<MasterOperate> queryByUserId(Integer userId);

    @Select({"<script>"
            , "SELECT * FROM master_operate WHERE id = #{id}"
            , "</script>"})
    MasterOperate queryById(Integer id);

    @Select({"<script>"
            , "SELECT * FROM master_operate WHERE title = #{title}"
            , "</script>"})
    MasterOperate queryByTitle(String title);

    @Insert({"<script>"
            , "INSERT INTO master_operate"
            , "( parent_id, title, address, method, attr_id, icon_cls, status_id, order_id, create_user )"
            , "VALUES ( #{parentId}, #{title}, #{address}, #{method}, #{attrId}, #{iconCls}, #{statusId}, #{orderId}, #{createUser} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterOperate masterOperate);

    @Update({"<script>"
            , "UPDATE master_operate SET"
            , " parent_id = #{parentId}, title = #{title}, address = #{address}, method = #{method}, attr_id = #{attrId}"
            , ", icon_cls = #{iconCls}, status_id = #{statusId}, order_id = #{orderId}, update_user = #{updateUser}, update_time = now()"
            , " WHERE id = #{id}"
            , "</script>"})
    Integer edit(MasterOperate masterOperate);

    @Delete({"<script>"
            , "DELETE FROM master_operate WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
