package com.delai.bees.tops.web.response;

import lombok.*;

import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BitSignalTop200RecordDTO {
    private Long id;
    private Integer index;
    private String key;
    private String parentName;
    private Integer val;
    private Date ts;
    private Long duration;

    private String begin = "2018年04月12日 09:09:10";
    private String end = "2018年04月12日 09:09:10";
    private String machine;

    // 次数
    private Long count;

}
