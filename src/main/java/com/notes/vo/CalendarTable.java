package com.notes.vo;

import com.notes.domain.Sign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarTable {
    List<Sign> calendar; // 本月日历表
    String month; // 日历表时间（年-月）
    int continuous; // 本月连续打卡次数
}
