package com.skyline.upload.domain;

import com.skyline.base.domain.BaseProperties;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午5:53
 */
@Entity
@Table(name = "TERADATA_USER")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeradataUser extends BaseProperties {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String userId;
    /**
     * 用户编码
     */
    @Column(length = 20)
    private String userNo;
    /**
     * 用户姓名
     */
    @Column(length = 40)
    private String userName;
    /**
     * 地址
     */
    @Column(length = 200)
    private String address;
    /**
     * 性别
     */
    @Column(length = 3)
    private String gender;
    /**
     * 生日
     */
    @Temporal(TemporalType.TIMESTAMP)
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
