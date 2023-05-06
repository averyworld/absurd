package com.okeeah.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okeeah.reggie.advice.CustomException;
import com.okeeah.reggie.dto.SetmealDto;
import com.okeeah.reggie.entity.Category;
import com.okeeah.reggie.entity.Setmeal;
import com.okeeah.reggie.entity.SetmealDish;
import com.okeeah.reggie.mapper.SetmealMapper;
import com.okeeah.reggie.service.CategoryService;
import com.okeeah.reggie.service.ISetmealDishService;
import com.okeeah.reggie.service.ISetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
@Service
public class ISetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>implements ISetmealService {
    @Autowired
    private ISetmealDishService iSetmealDishService;

    @Autowired
    private CategoryService iCategoryService;

    @Override
    public void saveSetmealAndDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
            iSetmealDishService.save(setmealDish);
        }

    }

    @Override
    public Page<SetmealDto> page4SetmealDTO(Page<Setmeal> pageQuery, LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper) {
        Page<Setmeal> oriPage = this.page(pageQuery, setmealLambdaQueryWrapper);
        Page<SetmealDto> result = new Page<>();
        BeanUtils.copyProperties(oriPage, result, "records");

        List<Setmeal> records = oriPage.getRecords();
        List<SetmealDto> newRecords = new ArrayList<>();
        for (Setmeal setmeal : records) {

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Category category = iCategoryService.getById(setmeal.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            newRecords.add(setmealDto);
        }

        result.setRecords(newRecords);
        return result;

    }

    @Override
    public SetmealDto findById4DTO(Long id) {

        SetmealDto result = new SetmealDto();

        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal, result);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = iSetmealDishService.list(queryWrapper);
        result.setSetmealDishes(setmealDishes);
        return result;


    }

    @Override
    public void update4DTO(SetmealDto setmealDto) {
        this.updateById(setmealDto);

//       先按条件删除该套餐原来的关联菜品
        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.eq(SetmealDish::getSetmealId, setmealDto.getId());
        iSetmealDishService.remove(qw);

//        添加该套餐新的关联菜品
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
            iSetmealDishService.save(setmealDish);
        }


    }

    @Override
    public void deleteByIds(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId, ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(setmealLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("套餐正在售卖中,不能删除");
        }
        this.removeByIds(ids);
    }
}