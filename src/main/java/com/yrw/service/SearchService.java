package com.yrw.service;

import com.yrw.pojo.ResultModel;

public interface SearchService {

    public ResultModel query(String queryString, String price, Integer page) throws Exception;
}
