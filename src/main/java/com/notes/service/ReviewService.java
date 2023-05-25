package com.notes.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.notes.bean.ScheduledTask;
import com.notes.bean.WebSocket;
import com.notes.domain.Review;
import com.notes.domain.User;
import com.notes.mapper.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    WebSocket webSocket;

    @Autowired
    UserService userService;

    @Value("${spring.mail.username}")
    String username;

    @Autowired
    private JavaMailSender mailSender;


    public Review getReviewById(int reviewId){
        return reviewMapper.selectById(reviewId);
    }

    public boolean addReview(Review review){
        try{
            reviewMapper.insert(review);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 查询Review（条件看前端）
     * */
    public IPage<Review> getReviews(long currentPage, long pageSize, String account, Map<String, String> condition){
        if(condition.isEmpty()){
            QueryWrapper<Review> wrapper = new QueryWrapper<>();
            wrapper.eq("promulgator",account);
            wrapper.eq("deleted", false);
            IPage<Review> page = new Page<>(currentPage, pageSize);
            reviewMapper.selectPage(page,wrapper);
            return page;
        }
        else{
            QueryWrapper<Review> wrapper = new QueryWrapper<>();
            wrapper.eq("promulgator",account);
            wrapper.eq("deleted", false);
            if(!condition.get("cycle").isEmpty())
                if (!condition.get("cycle").equals("全部"))
                    wrapper.eq("cycle", condition.get("cycle"));
            if(!condition.get("status").isEmpty())
                if(!condition.get("status").equals("全部"))
                    wrapper.eq("status", condition.get("status"));
            if (!condition.get("content").isEmpty())
                wrapper.like("title", "%"+condition.get("content")+"%");
            IPage<Review> page = new Page<>(currentPage, pageSize);
            reviewMapper.selectPage(page,wrapper);
            return page;
        }
    }

    /**
     * 完成Review（状态改为 ‘已完成’）
     * */
    public boolean finishReview(Review review){
        try{
            review.setStatus("已完成");
            reviewMapper.updateById(review);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除Review
     * */
    public boolean deleteReview(int reviewId){
        try{
            Review review = reviewMapper.selectById(reviewId);
            review.setDeleted(true);
            reviewMapper.updateById(review);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //通过ws协议提醒用户复习
    public void remindReview(Review review){
        review.setNum(review.getNum()+1);// 复习次数加一
        reviewMapper.updateById(review);
        JSONObject json = new JSONObject();
        if(webSocket.checkUserHasLogin(review.getPromulgator())){ // 如果用户有登录
            log.info("【任务提醒】"+review.getPromulgator()+"用户已登录,发送任务提醒");
            json.put("title",review.getTitle());
            json.put("content",review.getContent());
            webSocket.sendOneMessage(review.getPromulgator(),json.toJSONString());
        }else{
            log.info("【任务提醒】"+review.getPromulgator()+"用户未登录,发送邮箱提醒");
            User user = userService.getUserByAccount(review.getPromulgator());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);//自己给自己发邮件
            message.setTo(user.getEmail());
            message.setSubject("错题管理系统【复习计划提醒】");
            message.setText("亲爱的"+user.getUsername()+",你的复习计划【"+review.getTitle()+"】到时间啦，记得上线查看喔 ~");
            try{
                mailSender.send(message);
            }catch (MailException e){
                e.printStackTrace();
            }
        }

    }

    public void refreshReview(String account){
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("promulgator",account);
        wrapper.eq("deleted", false);
        wrapper.eq("status", "进行中");
        List<Review> reviews = reviewMapper.selectList(wrapper);
        for (int i = 0; i < reviews.size(); i++) {
            log.info("【导入复习计划提醒】"+reviews.get(i).getTitle()+"\t\t\tcron表达式"+reviews.get(i).getCron());
        }
        ScheduledTask scheduledTask = new ScheduledTask();
        //调用configureTasks()方法传入ScheduledTask
        scheduledTask.configureTasks(new ScheduledTaskRegistrar());
        //调用refresh()方法传入任务数据
        scheduledTask.refresh(reviews);
    }
}
