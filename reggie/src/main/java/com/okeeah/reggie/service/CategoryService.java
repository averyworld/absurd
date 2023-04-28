package com.okeeah.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.okeeah.reggie.entity.Category;



public interface CategoryService extends IService<Category> {

    void deleteById(Long id);
}
