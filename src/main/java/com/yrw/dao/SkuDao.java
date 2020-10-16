package com.yrw.dao;

import com.yrw.pojo.Sku;

import java.util.List;

public interface SkuDao {
    /**
     * 查询所有的Sku数据
     * @return
     **/
    public List<Sku> querySkuList();
}
