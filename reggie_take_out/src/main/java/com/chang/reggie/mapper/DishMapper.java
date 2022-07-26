package com.chang.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chang.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chang
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-07-25 16:15:39
* @Entity .entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




