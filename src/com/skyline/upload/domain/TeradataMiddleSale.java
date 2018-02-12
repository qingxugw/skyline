package com.skyline.upload.domain;

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
@Table(name = "TERADATA_MIDDLE_SALE")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeradataMiddleSale {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String middleId;
    /**
     * 用户编号
     */
    @Column(length = 20)
    private String userNo;
    /**
     * 地址
     */
    @Column(length = 200)
    private String address;
    /**
     * 商品编号
     */
    @Column(length = 20)
    private String productNo;
    /**
     * 商品名称
     */
    @Column(length = 20)
    private String productName;
    /**
     * 售价
     */
    @Column
    private BigDecimal money;
    /**
     * 商品标价
     */
    @Column
    private BigDecimal price;
    /**
     * 数量
     */
    @Column
    private BigDecimal number;

    /**
     * 单件利润
     */
    @Column
    private BigDecimal profit;
    /**
     * 销售额
     */
    @Column
    private BigDecimal totalMoney;
    /**
     * 总利润
     */
    @Column
    private BigDecimal totalProfit;
    /**
     * 销售日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;

    /**
     * 月份 YYYYMM
     */
    @Column(length = 10)
    private String month;

    public String getMiddleId() {
        return middleId;
    }

    public void setMiddleId(String middleId) {
        this.middleId = middleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
