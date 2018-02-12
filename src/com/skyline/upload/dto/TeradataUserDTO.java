package com.skyline.upload.dto;

import com.skyline.base.dto.BasePropertiesDTO;

import java.util.Date;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午6:45
 */
public class TeradataUserDTO extends BasePropertiesDTO {
    /**
     * 主键
     */
    private String userId;
    /**
     * 用户编码
     */
    private String userNo;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 地址
     */
    private String address;
    /**
     * 性别
     */
    private String gender;
    /**
     * 生日
     */
    private Date birthday;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
