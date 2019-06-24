package com.delai.bees.tops.dao;

import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.entity.domain.BitAnalogSignalsStats;

import java.util.Date;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/01/12
*/
@Mapper
public interface BitSignalConfigMapper extends AbstractMapper<BitSignalConfig, Long>{

    /**
     * 删除所有
     * @return
     */
    int clear();


    /**
     * Tops 统计
     * @param begin
     * @param end
     * @return
     */
    List<BitAnalogSignalsStats> statsTopsBitAnalogSignalsAccordingTimeRange(@Param("begin") Date begin, @Param("end") Date end);
    /**
     * Tops 统计
     * @param begin
     * @param end
     * @return
     */
    List<BitAnalogSignalsStats> statsTopsBitAnalogSignalsAccordingTimeRange(@Param("begin") Date begin, @Param("end") Date end, PageBounds bounds);


}