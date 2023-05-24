package com.notes.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.notes.domain.Review;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {

    @Autowired
    ReviewService reviewService;

    @KafkaListener(topics = "review")
    public void noteListener(ConsumerRecord<String, String> record, Acknowledgment ack) {
        JSON json = JSON.parseObject(record.value());
        Review review = JSONObject.toJavaObject(json, Review.class);
        reviewService.remindReview(review);
        ack.acknowledge();
    }
}