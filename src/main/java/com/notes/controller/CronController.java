package com.notes.controller;

import com.notes.bean.ScheduledTask;
import com.notes.bean.WebSocket;
import com.notes.domain.LogTask;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
 
/**
 * @author lzw
 * @create 2022-07-14-9:35
 * 定时任务相关
 */
@RestController
@AllArgsConstructor
public class CronController {

    @Autowired
    WebSocket webSocket;


    @GetMapping("/cron1")
    public void cron1(){
        //创建一个任务的容器
        List<LogTask> logTasks = new ArrayList<>();
        LogTask logTask1 = new LogTask();
        logTask1.setTask_id(1);
        logTask1.setExpression("0/2 * * * * ?");
        logTasks.add(logTask1);
        LogTask logTask2 = new LogTask();
        logTask2.setTask_id(2);
        logTask2.setExpression("0/5 * * * * ?");
        logTasks.add(logTask2);

        //创建一个任务线程池与任务的逻辑连接纽带
        ScheduledTask scheduledTask = new ScheduledTask();
        //调用configureTasks()方法传入ScheduledTask
        scheduledTask.configureTasks(new ScheduledTaskRegistrar());
        //调用refresh()方法传入任务数据
        scheduledTask.refresh(logTasks);
    }

    @GetMapping("/cron2")
    public void cron2(){
        //创建一个任务的容器
        List<LogTask> logTasks = new ArrayList<>();
        LogTask logTask1 = new LogTask();
        logTask1.setTask_id(3);
        logTask1.setExpression("0/3 * * * * ?");
        logTasks.add(logTask1);
        LogTask logTask2 = new LogTask();
        logTask2.setTask_id(4);
        logTask2.setExpression("0/4 * * * * ?");
        logTasks.add(logTask2);

        //创建一个任务线程池与任务的逻辑连接纽带
        ScheduledTask scheduledTask = new ScheduledTask();
        //调用configureTasks()方法传入ScheduledTask
        scheduledTask.configureTasks(new ScheduledTaskRegistrar());
        //调用refresh()方法传入任务数据
        scheduledTask.refresh(logTasks);
    }

    @GetMapping("/test")
    public String test(){
        webSocket.sendOneMessage("lisi","{'sender':'zhangsan','content':'hello','receiver':'lisi'}");
        return "true";
    }
}