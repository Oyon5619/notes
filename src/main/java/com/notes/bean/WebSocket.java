package com.notes.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * onlineUser 当前登录用户账号
 * */
@Component
@Slf4j
@ServerEndpoint("/websocket/{onlineUser}")
public class WebSocket {

    /**
     * 线程安全的无序的集合
     */
    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();

    /**
     * 存储在线连接数
     */
    private static final Map<String, Session> SESSION_POOL = new HashMap<>();

    /**
     * 连接成功时
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "onlineUser") String onlineUser) {
        try {
            SESSIONS.add(session);
            SESSION_POOL.put(onlineUser, session);
            log.info("【WebSocket消息】账号" + onlineUser+"登录系统");
            log.info("【WebSocket消息】有新的连接，总数为：" + SESSIONS.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam(value = "onlineUser")String onlineUser) {
        try {
            SESSIONS.remove(session);
            SESSION_POOL.remove(onlineUser);
            log.info("【WebSocket消息】账号" + onlineUser+"退出系统");
            log.info("【WebSocket消息】连接断开，总数为：" + SESSIONS.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器收到前端发送的消息后做出相应
     * */
    @OnMessage
    public void onMessage(String message,@PathParam("onlineUser") String onlineUser) {
        log.info("【WebSocket消息】"+onlineUser+"发送消息：" + message);
        //解析数据，发送消息到接收方
        JSONObject json = JSONObject.parseObject(message);
        if(json.containsKey("sender")&&json.containsKey("receiver")){//消息类别为Message
            sendOneMessage(String.valueOf(json.get("receiver")),message);
        }
    }

    /**
     * 广播消息
     *
     * @param message 消息
     */
    public void sendAllMessage(String message) {
        log.info("【WebSocket消息】广播消息：" + message);
        for (Session session : SESSIONS) {
            try {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 单点消息
     *
     * @param toUserId  接收者用户
     * @param message 消息
     */
    public void sendOneMessage(String toUserId, String message) {
        Session session = SESSION_POOL.get(toUserId);
        if (session != null && session.isOpen()) {
            try {
                synchronized (session) {
                    log.info("【WebSocket消息】"+toUserId+"收到单点消息：" + message);
                    session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 单点消息(多人)
     *
     * @param userIds 用户编号列表
     * @param message 消息
     */
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = SESSION_POOL.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【WebSocket消息】单点消息：" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检测用户是否登录
     * */
    public boolean checkUserHasLogin(String account){
        return SESSION_POOL.containsKey(account);
    }
}
