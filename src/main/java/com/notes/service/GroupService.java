package com.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
/**
 * 即考虑缓存，同时又要修改数据库
 * */
@Service
public class GroupService {

    @Autowired
    UserService userService;

    /**
     * 缓存
     * 获取用户所有组名（解开分隔符#）（注意无分组情况）
     *
     * @param account 当前用户账号
     * @return 所有组名
     */
    @Cacheable(value = "GroupsName", key = "#account")
    public String[] getGroupsName(String account) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缓存
     * 添加组名
     *
     * @param account      当前用户账号
     * @param newGroupName 新组名
     * @return 所有组名
     */
    @CachePut(value = "GroupsName", key = "#account")
    public String[] addGroup(String account, String newGroupName) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缓存
     * 更新组名
     *
     * @param account      当前用户账号
     * @param newGroupName 更新组名
     * @return 所有组名
     */
    @CachePut(value = "GroupsName", key = "#account")
    public String[] updateGroup(String account, String newGroupName) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缓存
     * 注意！删除组的同时需要将该组下的所有错题的分组改为 '' ！
     * 注意！当没有组时用户组字段应该为''，不能有’#‘！
     * 删除组名
     *
     * @param account   当前用户账号
     * @param groupName 要删除的组名
     * @return 删除后的所有组名
     */
    @CachePut(value = "GroupsName", key = "#account")
    public String[] deleteGroup(String account, String groupName) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }






}
