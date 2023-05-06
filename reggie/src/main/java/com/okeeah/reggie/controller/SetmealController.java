package com.okeeah.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.okeeah.reggie.dto.SetmealDto;
import com.okeeah.reggie.entity.Setmeal;
import com.okeeah.reggie.service.ISetmealService;
import com.okeeah.reggie.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author bytedance
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
//    private Logger logger = LoggerFactory.getLogger(SetmealController.class);
    @Autowired
    private ISetmealService iSetmealService;



    @PostMapping
    public R addSetMealAndDish(@RequestBody SetmealDto setmealDto) {
        iSetmealService.saveSetmealAndDish(setmealDto);
        return R.success("新增套餐成功");
    }

    //    注意页面查询的name不一定有 需要设置required=false
    @GetMapping("/page")
    public R page(@RequestParam(required = false) String name, int page, int pageSize) {
        Page<Setmeal> pageQuery = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(StringUtils.hasText(name), Setmeal::getName, name);
//        setmealLambdaQueryWrapper.eq(Setmeal::getIsDeleted, Global.LOGIC_IS_NOT_DELETE);
        Page<SetmealDto> result = iSetmealService.page4SetmealDTO(pageQuery, setmealLambdaQueryWrapper);
        return R.success(result);
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable("id") Long id) {
//        需要返回含setmealdish的setmealdto对象
        SetmealDto setmealDto = iSetmealService.findById4DTO(id);
        return R.success(setmealDto);
    }


    //    注意接收对象需要用请求体注解
    @PutMapping
    public R updateById(@RequestBody SetmealDto setmealDto) {
        iSetmealService.update4DTO(setmealDto);
        return R.success("修改套餐成功");

    }

    //   注意在路径里包含了状态值 要用路径占位符
    //   接收的ids数组可以用long数组可以用字符串数组或字符串 ;也可以用 List集合接收但一定要加上注解requestparam;
    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmeal:category",allEntries = true)
    public R updateStatus(Long[] ids, @PathVariable("status") int status) {

        log.info("正在批量修改状态,修改状态为:{},预修改id为{}",status,ids);

//        RuntimeException runtimeException = new RuntimeException("骗你的我随便编的");
//        log.info("通知通知发生异常了:",runtimeException);


        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId, ids);
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        iSetmealService.update(setmeal, setmealLambdaQueryWrapper);
        return R.success("修改套餐状态成功");
    }

    //   大部分删除操作理应都是逻辑删除 修改is_deleted参数;而不是真的删除导致其他场景无法使用该数据[例:用户查看历史订单]
    //   前端约定了单个和多个的删除传参都是ids,一个方法即可接收
    @DeleteMapping
    public R deleteByIds(@RequestParam List<Long> ids) {
        log.info("正在执行删除操作,删除id:{}",ids);
        iSetmealService.deleteByIds(ids);
        return R.success("删除套餐成功");
    }

    @GetMapping("/list")
    //标识: 我帮你 把key==setmeal:category:+#categoryId
    @Cacheable(value="setmeal:category",key="#categoryId")
    public R list(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId, categoryId);
        queryWrapper.eq(status!=null,Setmeal::getStatus, status);

        List<Setmeal> setmealList = iSetmealService.list(queryWrapper);

        return R.success(setmealList);
    }

}
