package com.notes.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.notes.domain.Sign;
import com.notes.mapper.SignMapper;
import com.notes.vo.CalendarTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SignService {

    @Autowired
    SignMapper signMapper;

    /**
     * 打卡
     */
    public boolean signIn(String account) {
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            String signDate = String.format("%04d-%02d-%02d",year,month,day);
            Sign sign = new Sign(account,year,month,day, signDate);
            signMapper.insert(sign);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取签到表（整个系统）
     */
    public CalendarTable getSignTable(int year, int month, String account) {
        try {
            CalendarTable table = new CalendarTable();
            QueryWrapper<Sign> wrapper = new QueryWrapper<>();
            wrapper.eq("year",year);
            wrapper.eq("month",month);
            List<Sign> list = signMapper.selectList(wrapper); // 当月签到记录
            Collections.sort(list); // 按日期排序
            int days = getMonthDays(year,month);
            int index = 0;
            int continuous = 0;
            List<Sign> ret = new ArrayList<>(); // 返回日历表
            for (int i = 1; i <= days ; i++) {
                if(list.get(index).getDay()==i){ // 已签到
                    ret.add(list.get(index));
                    index ++;
                }else{ // 未签到
                    Sign sign = new Sign();
                    sign.setSign(false); // 未签到
                    sign.setYear(year);
                    sign.setMonth(month);
                    sign.setDay(i);
                    sign.setSignDate(String.format("%04d-%02d-%02d",year,month,i));
                    sign.setCardNumber(0);
                    ret.add(sign);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断用户是否打卡
     * */
    public boolean isSignIn(int year,int month,int day,String account){
        QueryWrapper<Sign> wrapper = new QueryWrapper<>();
        wrapper.eq("year",year);
        wrapper.eq("month",month);
        wrapper.eq("day",day);
        wrapper.eq("signer",account);
        return signMapper.selectList(wrapper).size()>0;
    }

    /**
     * 获取某年某月的天数
     * */
    public int getMonthDays(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, (month - 1));
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        return cal.getActualMaximum(Calendar.DATE);
    }
}
