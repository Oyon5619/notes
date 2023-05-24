package com.notes.controller;

import com.notes.mapper.SignMapper;
import com.notes.service.SignService;
import com.notes.vo.CalendarTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sign")
public class SignController {

    @Autowired
    SignService signService;

    @Autowired
    SignMapper signMapper;

    @GetMapping("/getSignTable/{year}/{month}/{account}")
    public CalendarTable getSignTable(@PathVariable int year, @PathVariable int month, @PathVariable String account){
        return signService.getSignTable(year,month,account);
    }

    @GetMapping("/isSignIn/{year}/{month}/{day}/{account}")
    public boolean isSignIn(@PathVariable int year, @PathVariable int month, @PathVariable int day, @PathVariable String account){
        return signService.isSignIn(year, month, day, account);
    }

    @GetMapping("/signIn/{account}")
    public boolean signIn(@PathVariable String account){
        return signService.signIn(account);
    }
}
