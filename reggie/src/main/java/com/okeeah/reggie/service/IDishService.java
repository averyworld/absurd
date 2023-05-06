package com.okeeah.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.okeeah.reggie.dto.DishDTO;
import com.okeeah.reggie.entity.Dish;

import java.util.List;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
public interface IDishService extends IService<Dish> {
    void add(DishDTO dishDTO);

    Page<DishDTO> page4dishDTO(Page<Dish> queryPage, LambdaQueryWrapper<Dish> dishLambdaQueryWrapper);

    DishDTO getById4DTO(Long id);

    void updateById4DTO(DishDTO dishDTO);
    void deleteByIds(List<Long> ids);


    List<DishDTO> listWithFlavors(LambdaQueryWrapper<Dish> dishLambdaQueryWrapper);
}
