package com.notes.bean;

import com.notes.domain.LogTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

@Lazy(false)
@Component
@EnableScheduling
@Slf4j
public class ScheduledTask implements SchedulingConfigurer {

    private static volatile ScheduledTaskRegistrar registrar;
    private static volatile ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<Integer, ScheduledFuture<?>>();
    private static volatile ConcurrentHashMap<Integer, CronTask> cronTasks = new ConcurrentHashMap<Integer, CronTask>();

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setScheduler(Executors.newScheduledThreadPool(20));
        this.registrar = registrar;
    }

    public void refresh(List<LogTask> tasks){
        //取消已经删除的策略任务
        Set<Integer> sids = scheduledFutures.keySet();
        for (Integer sid : sids) {
            if(!exists(tasks, sid)){
                scheduledFutures.get(sid).cancel(false);
            }
        }

        for (LogTask logTask : tasks) {
//            ScheduledTaskRunnable t = new ScheduledTaskRunnable(logTask.getTask_id(), logTask.getRule_db_id());
            String expression = logTask.getExpression();
            //计划任务表达式为空则跳过
            if(StringUtils.isEmpty(expression)){
                continue;
            }
            //计划任务已存在并且表达式未发生变化则跳过
            if(scheduledFutures.containsKey(logTask.getTask_id()) && cronTasks.get(logTask.getTask_id()).getExpression().equals(expression)){
                continue;
            }
            //如果策略执行时间发生了变化，则取消当前策略的任务
            if(scheduledFutures.containsKey(logTask.getTask_id())){
                scheduledFutures.get(logTask.getTask_id()).cancel(false);
                scheduledFutures.remove(logTask.getTask_id());
                cronTasks.remove(logTask.getTask_id());
            }
            CronTask task = new CronTask(new Runnable() {
                @Override
                public void run() {
                    //每个计划任务实际需要执行的具体业务逻辑
                    System.out.println("正在执行的任务ID: "+logTask.getTask_id()+" |执行的cron表达式: "+logTask.getExpression());
                }
            }, expression);
            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
            cronTasks.put(logTask.getTask_id(), task);
            scheduledFutures.put(logTask.getTask_id(), future);
        }
    }


    private boolean exists(List<LogTask> tasks, Integer tid){
        for(LogTask logTask:tasks){
            if(logTask.getTask_id() == tid){
                return true;
            }
        }
        return false;
    }


    @PreDestroy
    public void destroy() {
        registrar.destroy();
    }

}
