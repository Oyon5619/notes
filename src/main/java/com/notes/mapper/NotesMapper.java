package com.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.notes.domain.Notes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotesMapper extends BaseMapper<Notes> {

}
