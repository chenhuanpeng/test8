package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class VisitPatient {
    /**
     * 性别 1男,2女
     * @mock 1
     */
    protected Integer gender;
    /**
     * 出生日期 格式： yyyy-MM-dd 或者 yyyyMMdd
     * @mock 2023-01-01
     */
    protected String birthday;
    /**
     * 年龄
     * @mock 20
     */
    protected Integer age;
    /**
     * 年龄类型 YEAR, DAY, MONTH
     * @mock YEAR
     */
    protected String ageType;
    /**
     * 是否怀孕，1/0
     * 1: 怀孕 ，0：未怀孕
     */
    protected String pregnancy;
    /**
     * 是否结婚
     * 0：未婚 ，1: 已婚， 2:其他
     */
    protected String marriage;
}
