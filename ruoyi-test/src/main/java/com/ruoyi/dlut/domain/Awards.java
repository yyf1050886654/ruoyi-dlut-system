package com.ruoyi.dlut.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 指标管理对象 awards
 * 
 * @author yyf
 * @date 2024-02-29
 */
public class Awards extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long sort;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String awardName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long priceLevel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long boolTemp;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long kind;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal credit;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String info;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setAwardName(String awardName) 
    {
        this.awardName = awardName;
    }

    public String getAwardName() 
    {
        return awardName;
    }
    public void setPriceLevel(Long priceLevel) 
    {
        this.priceLevel = priceLevel;
    }

    public Long getPriceLevel() 
    {
        return priceLevel;
    }
    public void setBoolTemp(Long boolTemp) 
    {
        this.boolTemp = boolTemp;
    }

    public Long getBoolTemp() 
    {
        return boolTemp;
    }
    public void setKind(Long kind) 
    {
        this.kind = kind;
    }

    public Long getKind() 
    {
        return kind;
    }
    public void setCredit(BigDecimal credit) 
    {
        this.credit = credit;
    }

    public BigDecimal getCredit() 
    {
        return credit;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sort", getSort())
            .append("awardName", getAwardName())
            .append("priceLevel", getPriceLevel())
            .append("boolTemp", getBoolTemp())
            .append("kind", getKind())
            .append("credit", getCredit())
            .append("info", getInfo())
            .toString();
    }
}
