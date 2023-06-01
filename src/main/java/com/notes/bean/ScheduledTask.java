package com.notes.bean;

import com.notes.domain.Review;
import com.notes.service.ReviewService;
import com.notes.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
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

    public void refresh(List<Review> tasks) {
        //取消已经删除的策略任务
        Set<Integer> sids = scheduledFutures.keySet();
        for (Integer sid : sids) {
            if (!exists(tasks, sid)) {
                scheduledFutures.get(sid).cancel(false);
            }
        }

        for (Review review : tasks) {
            String expression = review.getCron();
            //计划任务表达式为空则跳过
            if (StringUtils.isEmpty(expression)) {
                continue;
            }
            //计划任务已存在并且表达式未发生变化则跳过
            if (scheduledFutures.containsKey(review.getReviewId()) && cronTasks.get(review.getReviewId()).getExpression().equals(expression)) {
                continue;
            }
            //如果策略执行时间发生了变化，则取消当前策略的任务
            if (scheduledFutures.containsKey(review.getReviewId())) {
                scheduledFutures.get(review.getReviewId()).cancel(false);
                scheduledFutures.remove(review.getReviewId());
                cronTasks.remove(review.getReviewId());
            }

            CronTask task = new CronTask(new ReviewThread(review), expression);

            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
            cronTasks.put(review.getReviewId(), task);
            scheduledFutures.put(review.getReviewId(), future);
        }
    }


    private boolean exists(List<Review> tasks, Integer tid) {
        for (Review review : tasks) {
            if (review.getReviewId() == tid) {
                return true;
            }
        }
        return false;
    }

    @PreDestroy
    public void destroy() {
        registrar.destroy();
    }

    class ReviewThread extends Thread {
        private Review review;

        ReviewThread(Review review) {
            this.review = review;
        }

        @Override
        public void run() {
            ReviewService service = (ReviewService)SpringContextUtil.getBean("reviewService");
            service.remindReview(review);
            log.info("【复习计划提醒】 : " + review.getTitle() + "\t\t\t cron表达式: " + review.getCron());
        }
    }

}
