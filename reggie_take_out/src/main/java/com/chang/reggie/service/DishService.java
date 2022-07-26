package com.chang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.reggie.dto.DishDto;
import com.chang.reggie.entity.Dish;

/**
 * @author chang
 * @description 针对表【dish(菜品管理)】的数据库操作Service
 * @createDate 2022-07-25 16:15:39
 */
public interface DishService extends IService<Dish> {
    //新增菜品同时插入菜品对应的口味数据,需要操作两张表：dish、dish_flavor
     void saveWithFlavor(DishDto dishDto);
}
