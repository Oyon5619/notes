package com.notes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.notes.domain.Notes;
import com.notes.domain.Review;
import com.notes.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/getReviews/{currentPage}/{pageSize}")
    public IPage<Review> getReviews(@PathVariable long currentPage, @PathVariable long pageSize, @RequestBody Map<String,Object> map) {
        String account = (String) map.get("account");
        return reviewService.getReviews(currentPage,pageSize,account, (Map<String, String>) map.get("condition"));
    }

    @PostMapping("/addReview")
    public boolean addReview(@RequestBody Review review){
        return reviewService.addReview(review);
    }

    @GetMapping("/refreshReview/{account}")
    public boolean refreshReview(@PathVariable String account){
        reviewService.refreshReview(account);
        return true;
    }


    @PostMapping("/getReview/{currentPage}/{pageSize}")
    public IPage<Review> getReview(@PathVariable long currentPage, @PathVariable long pageSize,@RequestBody Map<String,Object> map) {
        String account = (String) map.get("account");
        Map<String,String> condition = (Map<String, String>) map.get("condition");
        return reviewService.getReviews(currentPage,pageSize,account,condition);
    }


    @GetMapping("/deleteReview/{reviewId}")
    public boolean deleteReview(@PathVariable Integer reviewId) {
        return reviewService.deleteReview(reviewId);
    }

    @GetMapping("/finishReview/{reviewId}")
    public boolean finishReview(@PathVariable int reviewId){
        Review review = reviewService.getReviewById(reviewId);
        return reviewService.finishReview(review);
    }

}
