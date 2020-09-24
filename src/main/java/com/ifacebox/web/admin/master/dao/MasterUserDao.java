package com.ifacebox.web.admin.master.dao;

import com.ifacebox.web.admin.master.model.MasterUser;
import com.ifacebox.web.admin.master.model.MasterUserQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface MasterUserDao {

    @Select({"<script>"
            , "SELECT * FROM master_user ORDER BY create_time DESC"
            , "</script>"})
    List<MasterUser> queryByAll();

    @Select({"<script>"
            , "SELECT * FROM master_user"
            , "ORDER BY create_time DESC LIMIT #{offset}, #{limit}"
            , "</script>"})
    List<MasterUser> query(MasterUserQuery masterUserQuery);

    @Select({"<script>"
            , "SELECT COUNT(*) FROM master_user"
            , "</script>"})
    Long total(MasterUserQuery masterUserQuery);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE id = #{id}"
            , "</script>"})
    MasterUser queryById(Integer id);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE username = #{username} AND password = #{password}"
            , "</script>"})
    MasterUser queryByUsernameAndPassword(String username, String password);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE username = #{username}"
            , "</script>"})
    MasterUser queryByUsername(String username);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE mobile = #{mobile} AND password = #{password}"
            , "</script>"})
    MasterUser queryByMobileAndPassword(String mobile, String password);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE mobile = #{mobile}"
            , "</script>"})
    MasterUser queryByMobile(String mobile);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE email = #{email} AND password = #{password}"
            , "</script>"})
    MasterUser queryByEmailAndPassword(String email, String password);

    @Select({"<script>"
            , "SELECT * FROM master_user WHERE email = #{email}"
            , "</script>"})
    MasterUser queryByEmail(String email);


    @Insert({"<script>"
            , "INSERT INTO master_user"
            , "( name, username, password, mobile, email, attr_id, status_id, create_user )"
            , "VALUES ( #{name}, #{username}, #{password}, #{mobile}, #{email}, #{attrId}, #{statusId}, #{createUser} )"
            , "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MasterUser masterUser);

    @Update({"<script>"
            , "UPDATE master_user SET"
            , "name = #{name}, username = #{username}, mobile = #{mobile}, email = #{email}"
            , ", attr_id = #{attrId}, status_id = #{statusId}, update_user = #{updateUser}, update_time = now()"
            , " WHERE id = #{id}"
            , "</script>"})
    Integer edit(MasterUser masterUser);

    @Update({"<script>"
            , "UPDATE master_user SET login_ip = #{loginIp}, login_time = now() WHERE id = #{id}"
            , "</script>"})
    Integer editLogin(MasterUser masterUser);

    @Update({"<script>"
            , "UPDATE master_user SET password = #{password} WHERE id = #{id}"
            , "</script>"})
    Integer editPassword(MasterUser masterUser);

    @Delete({"<script>"
            , "DELETE FROM master_user WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
