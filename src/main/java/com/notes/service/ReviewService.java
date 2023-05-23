package com.notes.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.notes.domain.Notes;
import com.notes.domain.Review;
import com.notes.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

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
}
