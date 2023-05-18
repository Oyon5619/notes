package com.notes.controller;

import com.notes.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    /**
     * 获取当前用户的所有分组名称
     * */
    @GetMapping("/getGroups")
    public String[] getGroups(String account){
        return groupService.getGroupsName(account);
    }

    /**
     * 添加分组
     * */
    @GetMapping("/addGroup/{account}/{newGroupName}")
    public String[] addGroup(@PathVariable String account, @PathVariable String newGroupName){
        return groupService.addGroup(account,newGroupName);
    }

    /**
     *删除分组
     **/
    @GetMapping("/deleteGroup")
    public String[] deleteGroup(@PathVariable String account, @PathVariable String groupName){
        return groupService.deleteGroup(account,groupName);
    }

}
