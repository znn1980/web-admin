package com.ifacebox.web.admin.im.dao;

import com.ifacebox.web.admin.im.model.ChatUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author znn
 */
@Mapper
@Component
public interface ChatUserDao {

    @Select({"<script>"
            , "SELECT * FROM chat_user"
            , "</script>"})
    List<ChatUser> query();

    @Select({"<script>"
            , "SELECT * FROM chat_user WHERE id = #{id}"
            , "</script>"})
    ChatUser queryById(Integer id);

    @Insert({"<script>"
            , "INSERT INTO chat_user"
            , "( id, username, status, sign, avatar )"
            , "VALUES ( #{id}, #{username}, #{status}, #{sign}, #{avatar} )"
            , "</script>"})
    Integer save(ChatUser chatUser);


    @Update({"<script>"
            , "UPDATE chat_user SET"
            , "<if test='username != null and !username.isEmpty()'>username = #{username}</if>"
            , "<if test='status != null and !status.isEmpty()'>status = #{status}</if>"
            , "<if test='sign != null and !sign.isEmpty()'>sign = #{sign}</if>"
            , "<if test='avatar != null and !avatar.isEmpty()'>avatar = #{avatar}</if>"
            , "WHERE id = #{id}"
            , "</script>"})
    Integer edit(ChatUser chatUser);


    @Delete({"<script>"
            , "DELETE FROM chat_user WHERE id = #{id}"
            , "</script>"})
    Integer delete(Integer id);
}
