package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterRoleOperate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterRoleOperateDao {

    @Select({"<script>"
            , "SELECT * FROM master_role_operate WHERE role_id = #{roleId}"
            , "</script>"})
    List<MasterRoleOperate> queryByRoleId(Integer roleId);

    @Select({"<script>"
            , "SELECT * FROM master_role_operate WHERE operate_id = #{operateId}"
            , "</script>"})
    List<MasterRoleOperate> queryByOperateId(Integer operateId);

    @Insert({"<script>"
            , "INSERT INTO master_role_operate ( role_id, operate_id ) VALUES ( #{roleId}, #{operateId} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterRoleOperate masterRoleOperate);


    @Delete({"<script>"
            , "DELETE FROM master_role_operate WHERE role_id = #{roleId}"
            , "</script>"})
    Integer delete(Integer roleId);
}
