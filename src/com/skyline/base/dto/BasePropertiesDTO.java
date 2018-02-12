package com.skyline.base.dto;

import com.skyline.base.domain.TdUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-20
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
public  class BasePropertiesDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private int version;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 修改人
     */
    private String modifyUser;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}
