package com.lzw.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<Long> selectId(@Param("cm") Map<String, Object> param);
}
