package com.delai.bees.tops.dao;

import com.delai.bees.tops.entity.LineProduct;
import com.delai.bees.tops.entity.domain.LineProdcutTimeCrossingSum;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/24.
 */
public interface LineProductMapper extends AbstractMapper<LineProduct, Integer> {

    /**
     * 根据时间交叉计算产量
     * @param begin
     * @param end
     * @return
     */
    LineProdcutTimeCrossingSum calProductsAccordingTimeCrossing(@Param("begin") Date begin, @Param("end") Date end);
}
