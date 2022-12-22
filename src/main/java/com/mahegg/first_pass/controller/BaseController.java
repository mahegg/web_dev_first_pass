package com.mahegg.first_pass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping(path = "/logon")
    public String redirect() {
        return "forward:/index.html";
    }




}
