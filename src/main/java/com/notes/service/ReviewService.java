package com.notes.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.notes.bean.ScheduledTask;
import com.notes.bean.WebSocket;
import com.notes.domain.Notes;
import com.notes.domain.Review;
import com.notes.mapper.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean addReview(Review review){
        try{
            reviewMapper.insert(review);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public IPage<Review> getReviews(long currentPage, long pageSize, String account, Map<String, String> condition){
        if(condition.isEmpty()){
            QueryWrapper<Review> wrapper = new QueryWrapper<>();
            wrapper.eq("promulgator",account);
            wrapper.eq("deleted", false);
            IPage<Review> page = new Page<>(currentPage, pageSize);
            reviewMapper.selectPage(page,wrapper);
            return page;
        }
        return null;
    }

    //通过ws协议提醒用户复习
    public void remindReview(Review review){
        review.setNum(review.getNum()+1);// 复习次数加一
        reviewMapper.updateById(review);
        JSONObject json = new JSONObject();
        json.put("title",review.getTitle());
        json.put("content",review.getContent());
        webSocket.sendOneMessage(review.getPromulgator(),json.toJSONString());
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
