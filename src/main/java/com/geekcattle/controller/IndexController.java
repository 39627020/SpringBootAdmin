/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.controller;

import com.geekcattle.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    public String test(Model model){
        String rq = DateUtil.getCurrentTime();
        System.out.println(rq);
        model.addAttribute("rq", rq);
        return "test/test";
    }

    @ResponseBody
    @RequestMapping(value = "/testPost",method = {RequestMethod.POST})
    public Map<String,Object> testpost(HttpServletRequest request){
    		String date = request.getParameter("rq");
    		Map<String,Object> result = new HashMap<String,Object>();
    		result.put("Date", date);
        return result;
    }



    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }


}
