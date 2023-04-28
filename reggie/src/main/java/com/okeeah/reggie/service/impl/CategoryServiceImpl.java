package com.okeeah.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okeeah.reggie.advice.CustomException;
import com.okeeah.reggie.entity.Category;
import com.okeeah.reggie.entity.Dish;
import com.okeeah.reggie.entity.Setmeal;
import com.okeeah.reggie.mapper.CategoryMapper;
import com.okeeah.reggie.service.CategoryService;
import com.okeeah.reggie.service.IDishService;
import com.okeeah.reggie.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private IDishService dishService;
    @Autowired
    private ISetmealService setmealService;

    @Override
    public void deleteById(Long id) {
        //判断id在dish表中是否存在关联数据
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("此分类下存在菜品,不允许删除😠");
        }
        //判断id在setmeal表中是否存在关联数据
        LambdaQueryWrapper<Setmeal> queryWrapper4setmeal = new LambdaQueryWrapper<>();
        queryWrapper4setmeal.eq(Setmeal::getId, id);
        int count1 = setmealService.count(queryWrapper4setmeal);
        if (count1 > 0) {
            throw new CustomException("此分类下存在套餐,不允许删除😠");
        }
        //    如果没有关联的数据可以进行删除
        this.removeById(id);


    }
}
