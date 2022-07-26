package com.chang.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chang.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
/**
* @author chang
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-07-25 11:46:32
* @Entity .entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




