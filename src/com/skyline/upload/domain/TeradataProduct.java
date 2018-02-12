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
@Table(name = "TERADATA_PRODUCT")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeradataProduct extends BaseProperties {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String productId;
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
     * 类型
     */
    @Column(length = 100)
    private String category;
    /**
     * 金额
     */
    @Column
    private BigDecimal money;
    /**
     * 单位
     */
    @Column(length = 3)
    private String unit;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
