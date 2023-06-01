package com.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.notes.domain.Sign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SignMapper extends BaseMapper<Sign> {

    @Select("select sign_id, signer, year, month, day, t.sign_date,cardNumber from t_sign t , v_sign v where t.sign_date = v.sign_date")
    List<Sign> getAllSign();
}
