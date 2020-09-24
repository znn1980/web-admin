package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterUserGroup;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterUserGroupDao {

    @Select({"<script>"
            , "SELECT * FROM master_user_group WHERE user_id = #{userId}"
            , "</script>"})
    List<MasterUserGroup> queryByUserId(Integer userId);

    @Select({"<script>"
            , "SELECT * FROM master_user_group WHERE group_id = #{groupId}"
            , "</script>"})
    List<MasterUserGroup> queryByGroupId(Integer groupId);

    @Insert({"<script>"
            , "INSERT INTO master_user_group ( user_id, group_id ) VALUES ( #{userId}, #{groupId} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterUserGroup userGroup);

    @Delete({"<script>"
            , "DELETE FROM master_user_group WHERE user_id = #{userId}"
            , "</script>"})
    Integer delete(Integer userId);
}
