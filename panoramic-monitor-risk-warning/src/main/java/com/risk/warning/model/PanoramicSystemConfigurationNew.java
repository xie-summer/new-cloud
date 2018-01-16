package com.risk.warning.model;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@Table(name = "panoramic_system_configurationNew")
public class PanoramicSystemConfigurationnew extends BaseObject {
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
     * WarnType
     * 1：阈值预警
     */
    private Integer warn_type;
    
    /**
     * WarningName
     */
    private String warning_name;
    
    /**
     * FactoryName
     */
    private String factory_name;
    
    /**
     * SectionName
     */
    private String section_name;
    
    /**
     * DeviceName
     */
    private String device_name;
    
    /**
     * StrSub
     */
    private String str_sub;
    
    /**
     * SourceName
     */
    private String sub_name;
    
    /**
     * MaxValue
     */
    private Double max_value;
    
    /**
     * MinValue
     */
    private Double min_value;
    

    /**
     * DataBaseAddress
     */
    private String data_base_address;

    /**
     * DataBaseName
     */
    private String data_base_name;

    /**
     * DataBaseTable
     */
    private String data_base_table;

    /**
     * DataBaseType
     */
    private String data_base_type;

    /**
     * StrEvent
     */
    private String str_event;
    
    /**
     * Available
     */
    private Boolean available;
    
    /**
     * intervaltime
     */
    private Integer interval_time;
    
    /**
     * StrSubEvent
     */
    private String str_sub_event;
    
    /**
     * StrSubEventName
     */
    private String str_sub_event_name;
    
    /**
     * StrSubEventValue
     */
    private String str_sub_event_value;
    
    /**
     * MaxLevel
     */
    private Integer max_level;
    
    /**
     * LevelUpTime
     */
    private Integer level_up_Time;
    
    /**
     * LogicType
     */
    private Integer logic_type;
    
    /**
     * StrDateEvent
     */
    private String str_date_event;
    /**
     * StrDateEventType
     */
    private Integer str_date_event_type;
    
    

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
     * 获取WarnType
     *
     * @return warn_type - WarnType
     */
    public Integer getWarnType() {
    	return warn_type;    	
    }
    

    /**
     * 设置WarnType
     *
     * @param warntype WarnType
     */
    public void setWarnType(Integer warntype) {
        this.warn_type = warntype;
    } 
    
    /**
     * 获取StrSub
     *
     * @return str_sub - StrSub
     */
    public String getStrSub() {
        return str_sub;
    }



    /**
     * 设置StrSub
     *
     * @param strsub StrSub
     */
    public void setStrSub(String strsub) {
        this.str_sub = strsub;
    } 
    
    /**
     * 获取SubName
     *
     * @return sub_name - SubName
     */
    public String getSubName() {
    	return sub_name;    	
    }
    

    /**
     * 设置SubName
     *
     * @param subname SubName
     */
    public void setSourceName(String subname) {
        this.sub_name = subname;
    } 
    
    /**
     * 获取MaxValue
     *
     * @return max_value - MaxValue
     */
    public Double getMaxValue() {
    	return max_value;    	
    }
    

    /**
     * 设置MaxValue
     *
     * @param maxvalue MaxValue
     */
    public void setMaxValue(Double maxvalue) {
        this.max_value = maxvalue;
    } 

    
    /**
     * 获取MinValue
     *
     * @return min_value - MinValue
     */
    public Double getMinValue() {
    	return min_value;    	
    }
    

    /**
     * 设置MinValue
     *
     * @param minvalue MinValue
     */
    public void setMinValue(Double minvalue) {
        this.min_value = minvalue;
    } 
    

    /**
     * 获取DataBaseAddress
     *
     * @return data_base_address - DataBaseAddress
     */
    public String getDataBaseAddress() {
    	return data_base_address;    	
    }
    

    /**
     * 设置DataBaseAddress
     *
     * @param databaseaddress DataBaseAddress
     */
    public void setDataBaseAddress(String databaseaddress) {
        this.data_base_address = databaseaddress;
    } 
    

    /**
     * 获取DataBaseName
     *
     * @return data_base_name - DataBaseName
     */
    public String getDataBaseName() {
    	return data_base_name;    	
    }
    

    /**
     * 设置DataBaseName
     *
     * @param databasename DataBaseName
     */
    public void setDataBaseName(String databasename) {
        this.data_base_name = databasename;
    } 
    

    /**
     * 获取DataBaseTable
     *
     * @return data_base_table - DataBaseTable
     */
    public String getDataBaseTable() {
    	return data_base_table;    	
    }
    

    /**
     * 设置DataBaseTable
     *
     * @param databasetable DataBaseTable
     */
    public void setDataBaseTable(String databasetable) {
        this.data_base_table = databasetable;
    } 
    

    /**
     * 获取DataBaseType
     *
     * @return data_base_type - DataBaseType
     */
    public String getDataBaseType() {
    	return data_base_type;    	
    }
    

    /**
     * 设置DataBaseType
     *
     * @param databasetype DataBaseType
     */
    public void setDataBaseType(String databasetype) {
        this.data_base_type = databasetype;
    } 
    

    /**
     * 获取StrEvent
     *
     * @return str_event - StrEvent
     */
    public String getStrEvent() {
    	return str_event;    	
    }
    

    /**
     * 设置StrEvent
     *
     * @param strevent StrEvent
     */
    public void setStrEvent(String strevent) {
        this.str_event = strevent;
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
     * 获取Intervaltime
     *
     * @return interval_time - Intervaltime
     */
    public Integer getIntervaltime() {
    	return interval_time;    	
    }
    

    /**
     * 设置Intervaltime
     *
     * @param intervaltime Intervaltime
     */
    public void setIntervaltime(Integer intervaltime) {
        this.interval_time = intervaltime;
    } 
    
    
    /**
     * 获取StrSubEvent
     *
     * @return str_sub_event - StrSubEvent
     */
    public String getStrSubEvent() {
    	return str_sub_event;    	
    }
    

    /**
     * 设置StrSubEvent
     *
     * @param strsubevent StrSubEvent
     */
    public void setStrSubEvent(String strsubevent) {
        this.str_sub_event = strsubevent;
    } 
    
    
    /**
     * 获取StrSubEventName
     *
     * @return str_sub_event_name - StrSubEventName
     */
    public String getStrSubEventName() {
    	return str_sub_event_name;    	
    }
    

    /**
     * 设置StrSubEventName
     *
     * @param strsubeventname StrSubEventName
     */
    public void setStrSubEventName(String strsubeventname) {
        this.str_sub_event_name = strsubeventname;
    } 
    
    
    /**
     * 获取StrSubEventValue
     *
     * @return str_sub_event_value - StrSubEventValue
     */
    public String getStrSubEventValue() {
    	return str_sub_event_value;    	
    }
    

    /**
     * 设置StrSubEventValue
     *
     * @param strsubeventvalue StrSubEventValue
     */
    public void setStrSubEventValue(String strsubeventvalue) {
        this.str_sub_event_value = strsubeventvalue;
    } 
    

    
    
    /**
     * 获取WarningName
     *
     * @return warning_name - WarningName
     */
    public String getWarningName() {
    	return warning_name;    	
    }
    

    /**
     * 设置WarningName
     *
     * @param warning_name WarningName
     */
    public void setWarningName(String warningname) {
        this.warning_name = warningname;
    } 
    
    /**
     * 获取FactoryName
     *
     * @return factory_name - FactoryName
     */
    public String getFactoryName() {
    	return factory_name;    	
    }
    

    /**
     * 设置FactoryName
     *
     * @param factoryname FactoryName
     */
    public void setFactoryName(String factoryname) {
        this.factory_name = factoryname;
    } 
    
    /**
     * 获取SectionName
     *
     * @return section_name - SectionName
     */
    public String getSectionName() {
    	return section_name;    	
    }
    

    /**
     * 设置SectionName
     *
     * @param sectionname SectionName
     */
    public void setSectionName(String sectionname) {
        this.section_name = sectionname;
    } 

    /**
     * 获取DeviceName
     *
     * @return device_name - DeviceName
     */
    public String getDeviceName() {
    	return device_name;    	
    }
    

    /**
     * 设置DeviceName
     *
     * @param devicename DeviceName
     */
    public void setDeviceName(String devicename) {
        this.device_name = devicename;
    } 
    



    /**
     * 获取MaxLevel
     *
     * @return max_level - MaxLevel
     */
    public Integer getMaxLevel() {
    	return max_level;    	
    }
    

    /**
     * 设置MaxLevel
     *
     * @param maxlevel MaxLevel
     */
    public void setMaxLevel(Integer maxlevel) {
        this.max_level = maxlevel;
    } 
    



    /**
     * 获取LevelUpTime
     *
     * @return level_up_Time - LevelUpTime
     */
    public Integer getLevelUpTime() {
    	return level_up_Time;    	
    }
    

    /**
     * 设置LevelUpTime
     *
     * @param intervaltime LevelUpTime
     */
    public void setLevelUpTime(Integer leveluptime) {
        this.level_up_Time = leveluptime;
    } 



    /**
     * 获取LogicType
     *
     * @return logic_type - LogicType
     */
    public Integer getLogicType() {
    	return logic_type;    	
    }
    


    /**
     * 设置LogicType
     *
     * @param logictype LogicType
     */
    public void setLogicType(Integer logictype) {
        this.logic_type = logictype;
    } 

    /**
     * 获取StrDateEvent
     *
     * @return str_date_event - StrDateEvent
     */
    public String getStrDateEvent() {
    	return str_date_event;    	
    }
    

    /**
     * 设置StrDateEvent
     *
     * @param str_date_event StrDateEvent
     */
    public void setStrDateEvent(String strdateevent) {
        this.str_date_event = strdateevent;
    } 
    



    /**
     * 获取StrDateEventType
     *
     * @return str_date_event_type - StrDateEventType
     */
    public Integer getStrDateEventType() {
    	return str_date_event_type;    	
    }
    

    /**
     * 设置StrDateEventType
     *
     * @param strdateeventtype StrDateEventType
     */
    public void setStrDateEventType(Integer strdateeventtype) {
        this.str_date_event_type = strdateeventtype;
    } 
    
    
    @Override
    public Serializable realId() {
        return null;
    }
}