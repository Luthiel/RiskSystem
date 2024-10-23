package com.luthiel.RiskCtrlSys.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: Luthiel
 * description: Controller Demo
 * date: 2023
 */

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String testHello() {
        return "this junit5 MockMvc Test";
    }
}
