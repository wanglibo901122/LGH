package com.hl.erp.datasource.entities;

public class UserBusiness {
    /*
    主键
     */
    private Long id;
    /*
    类别
   */
    private String type;
    /*
     主ID
    */
    private String keyId;
    /*
     值
    */
    private String value;
    /*
     按钮权限
    */
    private String btnStr;

    private Long tenantId;
    /*
     删除标记，0未删除，1删除，默认0
    */
    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId == null ? null : keyId.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getBtnStr() {
        return btnStr;
    }

    public void setBtnStr(String btnStr) {
        this.btnStr = btnStr == null ? null : btnStr.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }
}