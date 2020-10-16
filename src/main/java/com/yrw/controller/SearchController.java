package com.yrw.controller;


import com.yrw.pojo.ResultModel;
import com.yrw.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索
     * @param queryString  查询的关键字
     * @param price     查询的价格范围
     * @param page      当前页
     * @return
     * @throws Exception
     */
    //这里RequestMapping不用写路径了，因为上面写了，这里加上RequestMapping是如果“/list”则会跳转到此方法
    @RequestMapping("/list")
    public String query(String queryString, String price, Integer page, Model model) throws Exception{

        //处理当前页
        if (StringUtils.isEmpty(page)){
            page = 1;
        }
        if (page <= 0) {
            page = 1;
        }

        //调用service查询
        ResultModel resultModel = searchService.query(queryString, price, page);
        model.addAttribute("result", resultModel);

        //查询条件回显到页面
        model.addAttribute("queryString", queryString);
        model.addAttribute("price", price);
        model.addAttribute("page", page);
        return "search";
    }

    @RequestMapping("/sucess")
    public String hello(Map<String, Object> map) {
        //map.put("hello","12345");
        return "search";
    }
}
