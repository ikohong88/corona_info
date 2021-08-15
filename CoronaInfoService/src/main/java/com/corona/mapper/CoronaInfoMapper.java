package com.corona.mapper;

import java.util.List;

import com.corona.vo.CoronaInfoVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoronaInfoMapper {
    public void insertCoronaInfo(CoronaInfoVO vo);
    public List<CoronaInfoVO> selectCoronaInfoByDate(String date);
}
