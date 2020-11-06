package com.assetcontrol.loggingwidget.contrloller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/"})
    public String indexView(){
        return "index";
    }
}
