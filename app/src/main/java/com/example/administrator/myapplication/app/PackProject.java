package com.example.administrator.myapplication.app;

import java.io.Serializable;

/**
 * code-time: 2018/8/9
 * code-author: by shdf
 * coder-wechat: zcm656025633
 * exp:
 **/
public class PackProject implements Serializable {
    private Integer packProjectId; //主键
    private String packProjectName; //项目名称
    private String packProjectWbs; //打包项目WBS码
    private String packProjectNumber; //业务编码
    private String org;
    private String orgCode;
    private Integer deleteMark; //删除标记
//    private IntegratedProject integratedProject; //综合计划批次id
    private boolean isSelected;

    public String getPackProjectNumber() {
        return packProjectNumber;
    }

    public void setPackProjectNumber(String packProjectNumber) {
        this.packProjectNumber = packProjectNumber;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getPackProjectId() {
        return packProjectId;
    }

    public void setPackProjectId(Integer packProjectId) {
        this.packProjectId = packProjectId;
    }

    public String getPackProjectName() {
        return packProjectName;
    }

    public void setPackProjectName(String packProjectName) {
        this.packProjectName = packProjectName;
    }

    public String getPackProjectWbs() {
        return packProjectWbs;
    }

    public void setPackProjectWbs(String packProjectWbs) {
        this.packProjectWbs = packProjectWbs;
    }

    public String getPackProjectNumebr() {
        return packProjectNumber;
    }

    public void setPackProjectNumebr(String packProjectNumebr) {
        this.packProjectNumber = packProjectNumebr;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

//    public IntegratedProject getIntegratedProject() {
//        return integratedProject;
//    }
//
//    public void setIntegratedProject(IntegratedProject integratedProject) {
//        this.integratedProject = integratedProject;
//    }
}
