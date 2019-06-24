package com.delai.bees.tops.service.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/10/8.
 */
public class IConstant {

    /**
     * 模板文件名称
     */
    public static final String TEMPLATE_FILENAME = "Overview.xlsx";

    /**
     * 模板文件SHEET
     */
    public static final String TEMPLATE_FILENAME_SHEET = "Overview";

    /**
     * 常量，DType CoreSignal/核心信号
     */
    public static final String DTYPE_CORESIGNAL = "CoreSignal";

    /**
     * 常量，DType AssociatedSignal/其他信号
     */
    public static final String DTYPE_ASSOCIATEDSIGNAL = "AssociatedSignal";

    /**
     * 核心信号/BACK
     */
    public static final String CTYPE_BACK = "BACK";

    /**
     * 核心信号/UNAVAILABLE
     */
    public static final String CTYPE_UNAVAILABLE = "UNAVAILABLE";

    /**
     * 核心信号/SPEED
     */
    public static final String CTYPE_SPEED = "SPEED";

    /**
     * 核心信号/PRODUCED
     */
    public static final String CTYPE_PRODUCED = "PRODUCED";

    /**
     * 核心信号/LACK
     */
    public static final String CTYPE_LACK = "LACK";

    /**
     * 核心信号/SECONDARY
     */
    public static final String CTYPE_SECONDARY = "SECONDARY";

    /**
     * 核心信号/PDT
     */
    public static final String CTYPE_PDT = "PDT";

    /**
     *
     */
    @Deprecated
    public static final Long MAX_OFFSET_OF_MATCH_TIME = 10 * 1000L;

    /**
     * 报告图表颜色配置/前端显示需要
     */
    @Deprecated
    public static final String[] COLORS = new String[]{"#f2797b", "#799ef2", "#c2f279", "#f279e5", "#79f2db", "#f2b879", "#9479f2", "#80f279", "#f279a4", "#79c7f2", "#eaf279", "#d679f2", "#79f2b3","#f28f79", "#7985f2", "#a9f279", "#f279cc", "#79eff2", "#f2d179", "#ad79f2", "#79f28a", "#f2798b", "#79aef2", "#d1f279", "#ef79f2", "#79f2cc","#f2a879", "#8579f2", "#90f279", "#f279b3", "#79d6f2"};


//    /**
//     * 注入机ID
//     */
//    public static final String IMPLANTER_ID = "7105b59f-575a-4bcc-9434-769725a253e8";
//
//    /**
//     * 注入机产量信号ID
//     */
//    public static final String IMPLANTER_UNIT_PRODUCT_ID = "96a0fd66-6616-4304-a270-cfcb11912577";
//    /**
//     * 吹瓶产量信号ID
//     */
//    public static final String BOTTLE_BLOWING_UNIT_PRODUCT_ID = "2a1f9156-5a48-4b88-bc5f-983b36f6c2e7";
//    /**
//     * 码垛机产量信号ID
//     */
//    public static final String STACKER_UNIT_PRODUCT_ID = "e48d81b2-4cf7-42ac-a67f-9d9c502219f5";

}
