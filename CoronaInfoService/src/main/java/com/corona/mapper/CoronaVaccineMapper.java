package com.corona.mapper;

import java.util.List;

import com.corona.vo.CoronaVaccineVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoronaVaccineMapper {
    public void insertVaccineInfo(CoronaVaccineVO vo);
    public List<CoronaVaccineVO> selectVaccineInfoByDate(String date);

    public CoronaVaccineVO selectVaccineRegionInfo(String date, String region);
}
