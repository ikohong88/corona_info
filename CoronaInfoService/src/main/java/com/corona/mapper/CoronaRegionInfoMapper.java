package com.corona.mapper;

import java.util.List;

import com.corona.vo.CoronaInfoVO;
import com.corona.vo.CoronaRegionVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoronaRegionInfoMapper {
    public void insertCoronaRegionInfo(CoronaRegionVO vo);
    public List<CoronaRegionVO> selectCoronaRegionInfoByDate(String date);

    public CoronaRegionVO selectRegionalCoronaInfo(String date, String region);
    public CoronaInfoVO selectCoronaInfoRegionTotal(String date);
}
