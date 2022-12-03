package com.ncs503.Babybook.models.utility;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum BloodTypeEnum {

APOSITIVO ("A+"), ANEGATIVO("A-"),BPOSITIVO("B+"), BNEGATIVO("B-"), ABPOSITIVO("AB+"), ABNEGATIVO("AB-"),
    CEROPOSITIVO("0+"), CERONEGATIVO("0-");

    private  String bloodType;

    BloodTypeEnum(String bloodType) {
        this.bloodType=bloodType;
    }

    public String getBloodType() {
        return bloodType;
    }
}
