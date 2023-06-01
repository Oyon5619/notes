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
    public String signIn(String account) {
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            String signDate = String.format("%04d-%02d-%02d",year,month,day);
            Sign sign = new Sign(account,year,month,day, signDate);
            signMapper.insert(sign);
            return signDate;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取签到表（整个系统）
     *
     */
    public CalendarTable getSignTable(int year, int month, String account) {
        try {
            CalendarTable table = new CalendarTable();
            List<Sign> list = signMapper.getAllSign(); // 当月签到记录
            Map<String,Sign> map = new HashMap<>(); // 筛选
            Map<String,Boolean> m = new HashMap<>();
            Map<String,Integer> n = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                Sign sign = list.get(i);
                if(sign.getSigner().equals(account)){ // 如果是当前登录用户
                    sign.setHasSign(true); // 有签到
                    map.put(list.get(i).getSignDate(),sign);
                }else{ // 未匹配到当前登录用户（只能写入一次）
                    if(!map.containsKey(sign.getSignDate())){ // 未装配
                        sign.setHasSign(false); // 暂时未签到
                        map.put(sign.getSignDate(),sign);
                    }
                }
            }
            int days = getMonthDays(year,month);
            int continuous = 0;
            List<Sign> ret = new ArrayList<>(); // 返回日历表
            for (int i = 1; i <= days ; i++) {
                String key =String.format("%04d-%02d-%02d",year,month,i);
                if(map.containsKey(key)){ // 当天有人签到（前面已经处理过一次数据）
                    if(map.get(key).isHasSign()){ // 有签到
                        continuous++;
                    }else{
                        continuous = 0;
                    }
                    ret.add(map.get(key));
                }else{ // 当天无人签到
                    continuous = 0;
                    Sign sign = new Sign();
                    sign.setHasSign(false); // 未签到
                    sign.setYear(year);
                    sign.setMonth(month);
                    sign.setDay(i);
                    sign.setSignDate(key);
                    sign.setCardNumber(0);
                    ret.add(sign);
                }
            }
            for (int i = 0; i < ret.size(); i++) {
                m.put(ret.get(i).getSignDate(),ret.get(i).isHasSign());
                n.put(ret.get(i).getSignDate(),ret.get(i).getCardNumber());
            }
            table.setCalendar(ret);
            table.setTable(m);
            table.setNums(m.size());
            table.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            table.setNumberTable(n);
            table.setMonth(String.format("%04d-%02d",year,month));
            table.setContinuous(continuous);
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断用户是否打卡
     * */
    public boolean isSignIn(String account){
        QueryWrapper<Sign> wrapper = new QueryWrapper<>();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        wrapper.eq("sign_date",date);
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
