package com.notes.controller;

import com.alibaba.fastjson.JSONObject;
import com.notes.domain.Notes;
import com.notes.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 数据统计控制器

@Slf4j
@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private NotesService notesService;

    // 获取题目总数
    @GetMapping("/totalNum")
    public Integer getTotalNumStatistic() {
        log.info("[getTotalNumStatistic]");
        return notesService.getTotalNumStatistic();
    }

    // 获取根据标签分组统计出来的题目数据 (notes_group)
    @GetMapping("/notesGroup")
    public Map<Object, Object> getNotesGroupStatistic() {
        log.info("[getNotesGroupStatistic]");
        return notesService.getNotesGroupStatistic();
    }

    // 获取根据优先级分组统计出来的题目数据
    @GetMapping("/notesPriority")
    public Map<Object, Object> getNotesPriorityStatistic() {
        log.info("[getNotesPriorityStatistic]");
        return notesService.getNotesPriorityStatistic();
    }

    // 获取根据日期分组统计出来的题目数据 (用户个人向)
    @GetMapping("/notesPublishDate")
    public String getNotesPublishDateStatistic(@RequestParam("promulgator") String promulgator,
                                               @RequestParam(value = "count", required = false) Integer count) {
        log.info("[getNotesPriorityStatistic] promulgator = {}", promulgator);
        return notesService.getNotesPublishDateStatistic(promulgator, count);
    }

}
