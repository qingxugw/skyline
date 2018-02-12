package com.skyline.base.domain;

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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public  class BaseProperties implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 版本号 乐观锁
     */
    @Version
    @Column(name = "version")
    private int version;
     /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /**
     * 创建人
     */
    @ManyToOne(targetEntity = TdUser.class)
    @JoinColumn(name = "createUserID")
    @Basic(fetch = FetchType.LAZY)
    private TdUser createUser;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;
    /**
     * 修改人
     */
    @ManyToOne(targetEntity = TdUser.class)
    @JoinColumn(name = "modifyUserID")
    @Basic(fetch = FetchType.LAZY)
    private TdUser modifyUser;

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

    public TdUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(TdUser createUser) {
        this.createUser = createUser;
    }

    public TdUser getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(TdUser modifyUser) {
        this.modifyUser = modifyUser;
    }
}
