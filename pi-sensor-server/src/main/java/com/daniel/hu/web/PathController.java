package com.daniel.hu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Daniel on 17/01/2017.
 */
@Controller
@RequestMapping("/home")
public class PathController {

    @RequestMapping("/index")
    public String index(ModelAndView modelAndView){
        return "index";
    }
}
