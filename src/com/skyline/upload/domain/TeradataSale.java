package com.skyline.upload.domain;

import com.skyline.base.domain.BaseProperties;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午5:53
 */
@Entity
@Table(name = "TERADATA_SALE")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeradataSale extends BaseProperties {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String saleId;
    /**
     * 用户编号
     */
    @Column(length = 20)
    private String userNo;
    /**
     * 商品编号
     */
    @Column(length = 20)
    private String productNo;
    /**
     * 金额
     */
    @Column
    private BigDecimal money;
    /**
     * 数量
     */
    @Column
    private BigDecimal number;
    /**
     * 销售日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
