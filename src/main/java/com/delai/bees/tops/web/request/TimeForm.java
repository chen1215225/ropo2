package com.delai.bees.tops.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimeForm {
    private String host;
    private String datefrom;
    private String timefrom;
    private String dateto;
    private String timeto;
}
