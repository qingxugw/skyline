package com.skyline.base.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-20
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_COMPANY")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TdCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 公司ID
     */
    @Id
    @GeneratedValue(generator = "c-assigned")
    @GenericGenerator(name = "c-assigned", strategy = "assigned")
    @Column(name="companyID",length=20)
    private String companyId;
    /**
     * 公司名称
     */
    @Column(length = 200)
    private String companyName;
    /**
     * 地址
     */
    @Column(length = 200)
    private String companyAddress;
    /**
     * 联系电话
     */
    @Column(length = 50)
    private String companyPhone;
    /**
     * 联系人
     */
    @ManyToOne
    @JoinColumn(name = "principalId")
    private TdUser principal;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public TdUser getPrincipal() {
        return principal;
    }

    public void setPrincipal(TdUser principal) {
        this.principal = principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdCompany)) return false;

        TdCompany tdCompany = (TdCompany) o;

        if (companyId != null ? !companyId.equals(tdCompany.companyId) : tdCompany.companyId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        return result;
    }
}
