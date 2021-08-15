package com.corona.mapper;

import java.util.List;

import com.corona.vo.GenAgeInfoVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenAgeInfoMapper {
    public void insertGenAgeInfo(GenAgeInfoVO vo);
    public List<GenAgeInfoVO> selectAgeInfoByDate(String date);
    public List<GenAgeInfoVO> selectGenInfoByDate(String date);
}
