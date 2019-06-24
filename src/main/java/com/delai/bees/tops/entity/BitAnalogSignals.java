package com.delai.bees.tops.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Created by Mybatis Generator on 2019/03/24
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BitAnalogSignals implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Bit_Analog_Signals.Id
     *
     * @mbg.generated Sun Mar 24 11:42:48 CST 2019
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Bit_Analog_Signals.Name
     *
     * @mbg.generated Sun Mar 24 11:42:48 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Bit_Analog_Signals.Val
     *
     * @mbg.generated Sun Mar 24 11:42:48 CST 2019
     */
    private Double val;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Bit_Analog_Signals.Ts
     *
     * @mbg.generated Sun Mar 24 11:42:48 CST 2019
     */
    private Date ts;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Bit_Analog_Signals.Signal_Id
     *
     * @mbg.generated Sun Mar 24 11:42:48 CST 2019
     */
    private String signalId;
}