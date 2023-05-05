package com.okeeah.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.okeeah.reggie.dto.DishDTO;
import com.okeeah.reggie.entity.Dish;
import com.okeeah.reggie.service.IDishService;
import com.okeeah.reggie.vo.R;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 菜品管理 前端控制器
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/5/5
 */


@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private IDishService dishService;

    @PostMapping
    public R add(@RequestBody DishDTO dishDTO){
        dishService.add(dishDTO);
        return  R.success("新增菜品成功");
    }
    //分页
    @GetMapping("/page")
    public R Page(@RequestParam(required = false) String name,
                  int page,
                  int pageSize){
        Page<Dish> queryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Dish::getName,name);
        Page<DishDTO> result = dishService.page4dishDTO(queryPage,queryWrapper );
        return  R.success(result);
    }

   // 回显修改
   @GetMapping("/{id}")
    public R findById(@PathVariable("id")Long id){
       DishDTO byId = dishService.getByI4DTO(id);
       return  R.success(byId);
   }

   //修改菜品
   @PutMapping
   public R update(@RequestBody DishDTO dishDTO) {
       dishService.updateById4DTO(dishDTO);
       return R.success("修改菜品成功");
   }

    //修改停售状态
    @PostMapping("/status/{state}")
    public R updateStatus (Long ids,@PathVariable("state")int state){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,ids);
        // 方法1:只传一个修改对象
        Dish dish = new Dish();
        dish.setStatus(state);
        dishService.update(dish,queryWrapper);
        return R.success("修改菜品状态成功");
    }

    @DeleteMapping
    public R deleteByIds(@RequestParam Long ids){
        dishService.deleteByIds(ids);
        return R.success("删除菜品成功");
    }


}
