package com.delai.bees.tops.entity.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MachineProduct {
    private String machineId;

    private String availability;

    private String performance;

    private String quality;

    private String bottlesProduced;

    private String bottlesRejected;

    private String bottlesLost;

    private String timeLost;

}
