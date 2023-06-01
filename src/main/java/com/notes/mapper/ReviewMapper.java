package com.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.notes.domain.Review;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper extends BaseMapper<Review> {
}
