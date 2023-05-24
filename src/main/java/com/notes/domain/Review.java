package com.notes.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("t_review")
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @TableId(type = IdType.ASSIGN_ID)
    private int reviewId; // 计划id
    private String promulgator; // 发布者
    private String title; // 计划标题
    private String content; // 详细内容
    private String cycle; // 复习周期
    private String reviewTime; // 复习时间
    private String cron; //cron表达式
    private int num; // 复习次数
    private String status;//复习状态
    private boolean deleted; //删除标志
    private String updateTime; // 更新时间
}
