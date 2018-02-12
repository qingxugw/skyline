package com.skyline.upload.domain;

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
@Entity
@Table(name = "TERADATA_MONTH")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeradataMonth {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(length = 45)
    private String monthId;
    /**
     * 表名
     */
    @Column(length = 30)
    private String tableName;
    /**
     * 最大月份   YYYYMM
     */
    @Column(length = 10)
    private String maxMonth;
    /**
     * 第二大月份
     */
    @Column(length = 10)
    private String secondMonth;
    /**
     * 备用字段1
     */
    @Column(length = 10)
    private String standby1;
    /**
     * 备用字段2
     */
    @Column(length = 10)
    private String standby2;
    /**
     * 备用字段3
     */
    @Column(length = 10)
    private String standby3;

    public String getMonthId() {
        return monthId;
    }

    public void setMonthId(String monthId) {
        this.monthId = monthId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(String maxMonth) {
        this.maxMonth = maxMonth;
    }

    public String getSecondMonth() {
        return secondMonth;
    }

    public void setSecondMonth(String secondMonth) {
        this.secondMonth = secondMonth;
    }

    public String getStandby1() {
        return standby1;
    }

    public void setStandby1(String standby1) {
        this.standby1 = standby1;
    }

    public String getStandby2() {
        return standby2;
    }

    public void setStandby2(String standby2) {
        this.standby2 = standby2;
    }

    public String getStandby3() {
        return standby3;
    }

    public void setStandby3(String standby3) {
        this.standby3 = standby3;
    }
}
