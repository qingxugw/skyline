package com.skyline.base.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-19
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_SUBSECTION")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TdSubsection extends BaseProperties {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 45)
    /**
     * 分部ID
     */
    private String subsectionId;
    /**
     * 分部名称
     */
    @Column(length = 100)
    private String subsectionName;
    /**
     * 分部类型
     */
    @Column(length = 3)
    private String subsectionType;
    /**
     * 分部级别
     */
    @Column(length = 3)
    private String subsectionLevel;
    /**
     * 上级分部
     */
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "superId")
    private TdSubsection superSub;
    /**
     * 下级分部列表
     */
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "superSub")
    private Set<TdSubsection> childSubsections;
    /**
     * 市县ID
     */
    @ManyToOne
    @JoinColumn(name = "cityId")
    private TdCity city;
    /**
     * 默认网点ID
     */
    @ManyToOne
    @JoinColumn(name = "defaultId")
    private TdSubsection defalult;
    /**
     * 负责人ID
     */
    @ManyToOne
    @JoinColumn(name = "principalId")
    private TdUser principal;
    /**
     * 电话1
     */
    @Column(length = 50)
    private String phone1;
    /**
     * 电话2
     */
    @Column(length = 50)
    private String phone2;
    /**
     * 传真
     */
    @Column(length = 50)
    private String fax;
    /**
     * 邮政编码
     */
    @Column(length = 6)
    private String postalCode;
    /**
     * 地址
     */
    @Column(length = 200)
    private String address;
    /**
     * 是否可用
     */
    @Type(type = "boolean")
    private boolean isAvailable;

    public String getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(String subsectionId) {
        this.subsectionId = subsectionId;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    public String getSubsectionType() {
        return subsectionType;
    }

    public void setSubsectionType(String subsectionType) {
        this.subsectionType = subsectionType;
    }

    public String getSubsectionLevel() {
        return subsectionLevel;
    }

    public void setSubsectionLevel(String subsectionLevel) {
        this.subsectionLevel = subsectionLevel;
    }

    public TdSubsection getSuperSub() {
        return superSub;
    }

    public void setSuperSub(TdSubsection superSub) {
        this.superSub = superSub;
    }

    public Set<TdSubsection> getChildSubsections() {
        return childSubsections;
    }

    public void setChildSubsections(Set<TdSubsection> childSubsections) {
        this.childSubsections = childSubsections;
    }

    public TdCity getCity() {
        return city;
    }

    public void setCity(TdCity city) {
        this.city = city;
    }

    public TdSubsection getDefalult() {
        return defalult;
    }

    public void setDefalult(TdSubsection defalult) {
        this.defalult = defalult;
    }

    public TdUser getPrincipal() {
        return principal;
    }

    public void setPrincipal(TdUser principal) {
        this.principal = principal;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdSubsection)) return false;

        TdSubsection that = (TdSubsection) o;

        if (!subsectionId.equals(that.subsectionId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return subsectionId.hashCode();
    }
}
