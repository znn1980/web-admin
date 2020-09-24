package com.ifacebox.web.admin.im.server;

import com.ifacebox.web.admin.im.model.*;
import com.ifacebox.web.admin.im.service.ChatUserService;
import com.ifacebox.web.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author znn
 */
@ServerEndpoint("/admin/im/chat/{userId}")
@Component
public class ChatWebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketServer.class);
    private static final String PING = "ping";
    private static final String PONG = "pong";
    private static final Map<Integer, Map<String, ChatUserSession>> USER_SESSION_POOLS = new ConcurrentHashMap<>();
    private static ChatUserService chatUserService;

    @Autowired
    public void setChatUserService(ChatUserService chatUserService) {
        ChatWebSocketServer.chatUserService = chatUserService;
    }

    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId, Session session) {
        ChatUser chatUser = chatUserService.queryById(userId);
        Map<String, ChatUserSession> sessionPools = USER_SESSION_POOLS.get(userId);
        if (ObjectUtils.isEmpty(sessionPools)) {
            sessionPools = new ConcurrentHashMap<>(10);
            sessionPools.put(session.getId(), new ChatUserSession(chatUser, session));
            USER_SESSION_POOLS.put(userId, sessionPools);
        } else {
            sessionPools.put(session.getId(), new ChatUserSession(chatUser, session));
        }
        if (logger.isInfoEnabled()) {
            logger.info("用户【{}/{}】进入，在线人数【{}/{}】。"
                    , session.getId(), chatUser.getUsername(), USER_SESSION_POOLS.size(), sessionPools.size());
        }
        ChatMessageResponse<ChatSystemMessage> response = new ChatMessageResponse<>();
        response.setType(ChatMessageType.onlineMessage);
        response.setData(new ChatSystemMessage(userId, ChatToMessageType.friend
                , "用户【" + chatUser.getUsername() + "】上线"));
        this.sendMessage(session.getId(), JsonUtils.writeValueAsString(response));
    }


    @OnClose
    public void onClose(@PathParam("userId") Integer userId, Session session) {
        Map<String, ChatUserSession> sessionPools = USER_SESSION_POOLS.get(userId);
        ChatUserSession userSession = sessionPools.get(session.getId());
        ChatUser chatUser = userSession.getChatUser();
        sessionPools.remove(session.getId());
        if (sessionPools.size() == 0) {
            USER_SESSION_POOLS.remove(userId);
        }
        if (logger.isInfoEnabled()) {
            logger.info("用户【{}/{}】退出，在线人数【{}/{}】。"
                    , session.getId(), chatUser.getUsername(), USER_SESSION_POOLS.size(), sessionPools.size());
        }
        ChatMessageResponse<ChatSystemMessage> response = new ChatMessageResponse<>();
        response.setType(ChatMessageType.offlineMessage);
        response.setData(new ChatSystemMessage(userId, ChatToMessageType.friend
                , "用户【" + chatUser.getUsername() + "】下线"));
        this.sendMessage(session.getId(), JsonUtils.writeValueAsString(response));
    }

    @OnError
    public void onError(@PathParam("userId") Integer userId, Session session, Throwable throwable) {
        Map<String, ChatUserSession> sessionPools = USER_SESSION_POOLS.get(userId);
        ChatUserSession userSession = sessionPools.get(session.getId());
        if (logger.isErrorEnabled()) {
            logger.error("用户【{}/{}】异常:{}", session.getId(), userSession.getChatUser().getUsername(), throwable.toString(), throwable);
        }
    }

    @OnMessage
    public void onMessage(@PathParam("userId") Integer userId, Session session, String message) {
        Map<String, ChatUserSession> sessionPools = USER_SESSION_POOLS.get(userId);
        ChatUserSession userSession = sessionPools.get(session.getId());
        ChatUser chatUser = userSession.getChatUser();
        if (logger.isInfoEnabled()) {
            logger.info("用户【{}/{}】信息:{}", session.getId(), chatUser.getUsername(), message);
        }
        ChatMessageRequest request;
        if (PING.equalsIgnoreCase(message)
                || ObjectUtils.isEmpty(request = JsonUtils.readValue(message, ChatMessageRequest.class))) {
            this.sendMessage(session.getId(), PONG);
        } else {
            if (ChatMessageType.chatMessage == request.getType()) {
                ChatSendMessage sendMessage = request.getData();
                ChatMineMessage mineMessage = sendMessage.getMine();
                ChatToMessage toMessage = sendMessage.getTo();
                ChatFromMessage fromMessage = new ChatFromMessage(mineMessage, toMessage);
                if (ChatToMessageType.friend == toMessage.getType()) {
                    if (ObjectUtils.isEmpty(USER_SESSION_POOLS.get(toMessage.getId()))) {
                        ChatMessageResponse<ChatSystemMessage> response = new ChatMessageResponse<>();
                        response.setType(ChatMessageType.systemMessage);
                        response.setData(new ChatSystemMessage(toMessage.getId(), ChatToMessageType.friend, "对方不在线"));
                        this.sendMessage(session, chatUser.getUsername(), JsonUtils.writeValueAsString(response));
                    } else {
                        ChatMessageResponse<ChatFromMessage> response = new ChatMessageResponse<>();
                        response.setType(ChatMessageType.chatMessage);
                        response.setData(fromMessage);
                        this.sendMessage(toMessage.getId(), session.getId(), JsonUtils.writeValueAsString(response));
                    }
                }
                if (ChatToMessageType.group == toMessage.getType()) {
                    ChatMessageResponse<ChatFromMessage> response = new ChatMessageResponse<>();
                    response.setType(ChatMessageType.chatMessage);
                    response.setData(fromMessage);
                    this.sendMessage(session.getId(), JsonUtils.writeValueAsString(response));
                }
            }
        }
    }

    public void sendMessage(Session session, String username, String message) {
        if (logger.isInfoEnabled()) {
            logger.info("用户【{}/{}】信息:{}", session.getId(), username, message);
        }
        session.getAsyncRemote().sendText(StringUtils.isEmpty(message) ? PONG : message);
    }

    public void sendMessage(String sessionId, String message) {
        for (Integer userId : USER_SESSION_POOLS.keySet()) {
            this.sendMessage(userId, sessionId, message);
        }
    }

    public void sendMessage(Integer userId, String sessionId, String message) {
        Map<String, ChatUserSession> sessionPools = USER_SESSION_POOLS.get(userId);
        for (ChatUserSession userSession : sessionPools.values()) {
            Session session = userSession.getSession();
            ChatUser chatUser = userSession.getChatUser();
            if (session.getId().equals(sessionId)) {
                continue;
            }
            this.sendMessage(session, chatUser.getUsername(), message);
        }
    }

    static class ChatUserSession {
        private ChatUser chatUser;
        private Session session;

        public ChatUserSession(ChatUser chatUser, Session session) {
            this.setChatUser(chatUser);
            this.setSession(session);
        }

        public ChatUser getChatUser() {
            return chatUser;
        }

        public void setChatUser(ChatUser chatUser) {
            this.chatUser = chatUser;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }
    }
}
