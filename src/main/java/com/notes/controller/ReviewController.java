package com.notes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.notes.domain.Notes;
import com.notes.domain.Review;
import com.notes.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/getReviews/{currentPage}/{pageSize}")
    public IPage<Review> getReviews(@PathVariable long currentPage, @PathVariable long pageSize, @RequestBody Map<String,Object> map) {
        String account = (String) map.get("account");
        return reviewService.getReviews(currentPage,pageSize,account,new HashMap<>());
    }

    @PostMapping("/addReview")
    public boolean addReview(@RequestBody Review review){
        return reviewService.addReview(review);
    }
}
