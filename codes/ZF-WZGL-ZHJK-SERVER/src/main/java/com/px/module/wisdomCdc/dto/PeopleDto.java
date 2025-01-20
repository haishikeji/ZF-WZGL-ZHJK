package com.px.module.wisdomCdc.dto;

import com.px.module.common.utils.Excel;
import com.px.module.wisdomCdc.entity.PeopleModel;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "PeopleDto", description = "People数据传输对象")
public class PeopleDto extends PeopleModel {

    @Excel(name = "身份")
    private String identityCn;

    @Excel(name = "性别")
    private String sexCn;

    @Excel(name = "个案状态")
    private String caseStatusCn;

    public String getIdentityCn() {
        return identityCn;
    }

    public void setIdentityCn(String identityCn) {
        this.identityCn = identityCn;
    }

    public String getSexCn() {
        return sexCn;
    }

    public void setSexCn(String sexCn) {
        this.sexCn = sexCn;
    }

    public String getCaseStatusCn() {
        return caseStatusCn;
    }

    public void setCaseStatusCn(String caseStatusCn) {
        this.caseStatusCn = caseStatusCn;
    }
}
