package com.notes.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sign implements Comparable<Sign>{
    @TableId(type = IdType.ASSIGN_ID)
    private int signId;
    private String signer;
    private int year;
    private int month;
    private int day;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String signDate;
    @TableField(exist = false)
    private boolean sign; // 是否签到
    @TableField(exist = false)
    private int cardNumber; // 当天打卡人数

    public Sign(String signer, int year, int month, int day, String signDate){
        this.signer = signer;
        this.year = year;
        this.month = month;
        this.day = day;
        this.signDate = signDate;
    }

    @Override
    public int compareTo(Sign sign) {
        return this.day - sign.day;
    }
}
