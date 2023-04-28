package com.okeeah.reggie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.okeeah.reggie.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    //查询所有分类
    List<Category> findAll();

    //保存
    void save(Category category);

    //更新
    void update(Category category);

    //从菜品表统计当前分类下的菜品数量
    Integer countDishByCategoryId(Long categoryId);

    //从套餐表统计当前分类下的套餐数量
    Integer countSetmealByCategoryId(Long categoryId);

    //删除
    void delete(Long id);


}
