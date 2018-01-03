package com.risk.warning.model;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "panoramic_system_configurationNew")
public class PanoramicSystemConfigurationNew extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * SubID
     */
    private Integer subid;
    /**
     * WarnType
     */
    private Integer warntype;
    /**
     * SourceName
     */
    private String subname;
    /**
     * MaxValue
     */
    private Integer maxvalue;
    /**
     * MinValue
     */
    private Integer minvalue;
    
    private String databaseaddress;
    private String databasename;
    private String databasetable;
    private String databasetype;
    private String strevent;
    
    private String checkfield;
    /**
     * CheckCondition
     */
    private String checkcondition;
    /**
     * CheckValue
     */
    private Integer checkvalue;
    /**
     * Available
     */
    private Boolean available;
    /**
     * intervaltime
     */
    private Integer intervaltime;
    
    

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    } 
    
    /**
     * 获取SubID
     *
     * @return subid - SubID
     */
    public Integer getSubId() {
        return subid;
    }



    /**
     * 设置SubID
     *
     * @param subid SubID
     */
    public void setSubId(Integer subid) {
        this.subid = subid;
    } 
    
    /**
     * 获取WarnType
     *
     * @return warntype - WarnType
     */
    public Integer getWarnType() {
    	return warntype;    	
    }
    

    /**
     * 设置WarnType
     *
     * @param warntype WarnType
     */
    public void setWarnType(Integer warntype) {
        this.warntype = warntype;
    } 
    
    /**
     * 获取SubName
     *
     * @return subname - SubName
     */
    public String getSubName() {
    	return subname;    	
    }
    

    /**
     * 设置SubName
     *
     * @param subname SubName
     */
    public void setSourceName(String subname) {
        this.subname = subname;
    } 
    
    /**
     * 获取MaxValue
     *
     * @return maxvalue - MaxValue
     */
    public Integer getMaxValue() {
    	return maxvalue;    	
    }
    

    /**
     * 设置MaxValue
     *
     * @param maxvalue MaxValue
     */
    public void setMaxValue(Integer maxvalue) {
        this.maxvalue = maxvalue;
    } 

    
    /**
     * 获取MinValue
     *
     * @return minvalue - MinValue
     */
    public Integer getMinValue() {
    	return minvalue;    	
    }
    

    /**
     * 设置MinValue
     *
     * @param minvalue MinValue
     */
    public void setMinValue(Integer minvalue) {
        this.minvalue = minvalue;
    } 
    

    /**
     * 获取DataBaseAddress
     *
     * @return databaseaddress - DataBaseAddress
     */
    public String getDataBaseAddress() {
    	return databaseaddress;    	
    }
    

    /**
     * 设置DataBaseAddress
     *
     * @param databaseaddress DataBaseAddress
     */
    public void setDataBaseAddress(String databaseaddress) {
        this.databaseaddress = databaseaddress;
    } 
    

    /**
     * 获取DataBaseName
     *
     * @return databasename - DataBaseName
     */
    public String getDataBaseName() {
    	return databasename;    	
    }
    

    /**
     * 设置DataBaseName
     *
     * @param databasename DataBaseName
     */
    public void setDataBaseName(String databasename) {
        this.databasename = databasename;
    } 
    

    /**
     * 获取DataBaseTable
     *
     * @return databasetable - DataBaseTable
     */
    public String getDataBaseTable() {
    	return databasetable;    	
    }
    

    /**
     * 设置DataBaseTable
     *
     * @param databasetable DataBaseTable
     */
    public void setDataBaseTable(String databasetable) {
        this.databasetable = databasetable;
    } 
    

    /**
     * 获取DataBaseType
     *
     * @return databasetype - DataBaseType
     */
    public String getDataBaseType() {
    	return databasetype;    	
    }
    

    /**
     * 设置DataBaseType
     *
     * @param databasetype DataBaseType
     */
    public void setDataBaseType(String databasetype) {
        this.databasetype = databasetype;
    } 
    

    /**
     * 获取StrEvent
     *
     * @return strevent - StrEvent
     */
    public String getStrEvent() {
    	return strevent;    	
    }
    

    /**
     * 设置StrEvent
     *
     * @param strevent StrEvent
     */
    public void setStrEvent(String strevent) {
        this.strevent = strevent;
    } 
    
    
    
    /**
     * 获取CheckField
     *
     * @return checkfield - CheckField
     */
    public String getCheckField() {
    	return checkfield;    	
    }
    

    /**
     * 设置CheckField
     *
     * @param checkfield CheckField
     */
    public void setCheckField(String checkfield) {
        this.checkfield = checkfield;
    } 
    

    /**
     * 获取CheckCondition
     *
     * @return checkcondition - CheckCondition
     */
    public String setCheckCondition() {
    	return checkcondition;    	
    }
    

    /**
     * 设置CheckCondition
     *
     * @param checkcondition CheckCondition
     */
    public void setCheckCondition(String checkcondition) {
        this.checkcondition = checkcondition;
    } 
    
    
    /**
     * 获取CheckValue
     *
     * @return checkvalue - CheckValue
     */
    public Integer setCheckValue() {
    	return checkvalue;    	
    }
    

    /**
     * 设置CheckValue
     *
     * @param checkvalue CheckValue
     */
    public void setCheckValue(Integer checkvalue) {
        this.checkvalue = checkvalue;
    }
    

    /**
     * 获取Available
     *
     * @return available - Available
     */
    public Boolean setAvailable() {
    	return available;    	
    }
    

    /**
     * 设置Available
     *
     * @param available Available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    } 


    /**
     * 获取Rate
     *
     * @return rate - Rate
     */
    public Integer setntervaltime() {
    	return intervaltime;    	
    }
    

    /**
     * 设置Rate
     *
     * @param rate Rate
     */
    public void setIntervaltime(Integer intervaltime) {
        this.intervaltime = intervaltime;
    } 
    
    @Override
    public Serializable realId() {
        return null;
    }
}