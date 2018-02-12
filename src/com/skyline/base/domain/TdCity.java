package com.skyline.base.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 李庆旭
 * Date: 11-6-20
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "TD_CITY")
@BatchSize(size=5)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TdCity extends BaseProperties {
    /**
     * 城市ID
     */
    @Id
    @GeneratedValue(generator = "c-assigned")
    @GenericGenerator(name = "c-assigned", strategy = "assigned")
    @Column(name="cityId",length=20)
    private String cityId;
    /**
     * 市县简称
     */
    @Column(length = 30)
    private String cityShorting;
    /**
     * 市县名称
     */
    @Column(length = 100)
    private String cityName;
    /**
     * 上级市县
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private TdCity superCity;
    @OneToMany(
            cascade = { CascadeType.ALL },
            mappedBy = "superCity")
    private Set<TdCity> childCitys;
    /**
     * 市县类型
     */
    @Column(length = 3)
    private String cityType;

    public Set<TdCity> getChildCitys() {
        return childCitys;
    }

    public void setChildCitys(Set<TdCity> childCitys) {
        this.childCitys = childCitys;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCityShorting(String cityShorting) {
        this.cityShorting = cityShorting;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setSuperCity(TdCity superCity) {
        this.superCity = superCity;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdCity)) return false;

        TdCity tdCity = (TdCity) o;

        if (cityId != null ? !cityId.equals(tdCity.cityId) : tdCity.cityId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cityId != null ? cityId.hashCode() : 0;
    }
}
