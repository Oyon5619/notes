package com.notes.vo;

import com.notes.domain.Sign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarTable {
    List<Sign> calendar; // 本月日历表
    Map<String,Boolean> table;
    int nums; // 本月天数
    String month; // 日历表时间（年-月）
    int continuous; // 本月连续打卡次数
    String currentDate; // 当前日期
    Map<String,Integer> numberTable; // 当天签到人数
}
