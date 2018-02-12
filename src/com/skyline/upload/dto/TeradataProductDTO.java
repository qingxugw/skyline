package com.skyline.upload.dto;

import com.skyline.base.domain.BaseProperties;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-11 下午5:53
 */
public class TeradataProductDTO extends BaseProperties {

    /**
     * 主键
     */
    private String productId;
    /**
     * 商品编号
     */
    private String productNo;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 类型
     */
    private String category;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 单位
     */
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
