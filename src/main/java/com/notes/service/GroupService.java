package com.notes.service;

import com.notes.domain.User;
import com.notes.mapper.NotesMapper;
import com.notes.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 即考虑缓存，同时又要修改数据库
 * */
@Service
public class GroupService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NotesMapper notesMapper;

    /**
     * 缓存
     * 获取用户所有组名（解开分隔符#）（注意无分组情况）
     *
     * @param account 当前用户账号
     * @return 所有组名
     */
//    @Cacheable(value = "GroupsName", key = "#account")
    public String[] getGroupsName(String account) {
        try {
            User user = userMapper.selectById(account);
            if(user.getUserGroups()==null||user.getUserGroups().length()==0){ // null 或空字符串
                return new String[0];
            }
            String[] ret = user.getUserGroups().split("#");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /**
     * 缓存
     * 添加组名
     *判断是否为空字符串或null，是的话直接添加，否则先补#再添加
     * @param account      当前用户账号
     * @param newGroupName 新组名
     * @return 所有组名
     */
//    @CachePut(value = "GroupsName", key = "#account")
    public String[] addGroup(String account, String newGroupName) {
        try {
            User user = userMapper.selectById(account);
            String group = user.getUserGroups();
            if(group==null ||group.length()==0){
                user.setUserGroups(newGroupName);
                userMapper.updateById(user);
                return new String[]{newGroupName};
            }else{
                group = group +"#"+newGroupName;
                user.setUserGroups(group);
            }
            userMapper.updateById(user);
            return user.getUserGroups().split("#");
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
     * @param oldGroupName  原组名
     * @param newGroupName 更新组名
     * @return 所有组名
     */
//    @CachePut(value = "GroupsName", key = "#account")
    public String[] updateGroup(String account,String oldGroupName, String newGroupName) {
        try {
            User user = userMapper.selectById(account);
            user.setUserGroups(user.getUserGroups().replace(oldGroupName, newGroupName));
            userMapper.updateById(user);
            return user.getUserGroups().split("#");
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
//    @CachePut(value = "GroupsName", key = "#account")
    public String[] deleteGroup(String account, String groupName) {
        try {
            notesMapper.deleteGroup(groupName);
            User user = userMapper.selectById(account);
            String group = user.getUserGroups();
            if(group.contains("#")){ // 多个groups
                if(group.startsWith(groupName)){
                    user.setUserGroups(group.replace(groupName+"#",""));
                }else{
                    user.setUserGroups(group.replace("#"+groupName,""));
                }
                userMapper.updateById(user);
                return user.getUserGroups().split("#");
            }else{
                user.setUserGroups("");
                userMapper.updateById(user);
                return new String[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
