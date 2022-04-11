package com.supplychainfinance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supplychainfinance.pojo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface LoginUserMapper extends BaseMapper<LoginUser> {
}
