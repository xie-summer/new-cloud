/**
 * 
 */
/**
 * @author fgh
 *
 */
package com.risk.warning.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.core.ServiceException;
import com.cloud.task.ScheduleTrigger;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;
import com.risk.warning.mapper.PanoramicSystemWarningQueryMapper;
import com.risk.warning.mapper.PanoramicSystemWarningReceiverMapper;
import com.risk.warning.mapper.PanoramicSystemWarningSourceDataMapper;
import com.risk.warning.model.PanoramicSystemWarningQuery;
import com.risk.warning.model.PanoramicSystemWarningReceiver;
import com.risk.warning.model.PanoramicSystemWarningSourceData;

/**
 * @author summer 2017/11/21.
 */
@Service("SystemWarningSrevice")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicSystemWarningSrevice{

   // private static final Logger logger = LoggerFactory.getLogger(ScheduleTriggerService.class);

    
    @Autowired
    @Qualifier("systemwarningquerymapper")
    private PanoramicSystemWarningQueryMapper systemwarningquerymapper;
    
    @Autowired
    @Qualifier("systemwarningsourcedatamapper")
    private PanoramicSystemWarningSourceDataMapper systemwarningsourcedatamapper;
    
    @Autowired
    @Qualifier("systemwarningreceiver")
    private PanoramicSystemWarningReceiverMapper systemwarningreceiver;

    @Autowired
    @Qualifier("scheduletrigger")
    private List<ScheduleTrigger> scheduletrigger;
    
    @Scheduled(cron="0/60 * *  * * ? ")  //每次调度间隔
    public void refreshTrigger() {

        try {
            //DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
            //System.out.println(sdf.format(DateTime.now().toDate())+"*********B任务每5秒执行一次进入测试");   
           
         
            //查询出数据库中所有的定时任务
            List<PanoramicSystemWarningQuery> QueryList = systemwarningquerymapper.GetStrSqlQuery();
		   	if (QueryList != null) {
	   			for (PanoramicSystemWarningQuery warningquery : QueryList) {
	   				Boolean isAdd = true;
	   				if(scheduletrigger == null || scheduletrigger.isEmpty()) {
	   						for (ScheduleTrigger job : scheduletrigger) {
                			   if(job.getId() == warningquery.getWarnConfigurationID()) {
                				   job.setExecutesql(warningquery.getQuerySql());
                				   if(job.getIntervalTime() != warningquery.getIntervalTime()) {
                					   int deltaTime =  warningquery.getIntervalTime() -job.getIntervalTime();
                				        Calendar ca = Calendar.getInstance();
                				        ca.setTime(job.getExecutetime());
                				        ca.add(Calendar.MINUTE, deltaTime); //操作时间
                				        job.setExecutetime(ca.getTime());
                				        job.setIntervalTime(warningquery.getIntervalTime());
                				   }
                				   isAdd =false;
                				   break;
                			   }
                		   }
                     }
	   				if(isAdd) {
	   					ScheduleTrigger model = new ScheduleTrigger();
	   					model.setId(warningquery.getWarnConfigurationID());
	   					model.setExecutesql(warningquery.getQuerySql());
	   					model.setIntervalTime(warningquery.getIntervalTime());   
	   					model.setExecutetime(new Date());
	   				}
                }
            }
		   	
			if(scheduletrigger != null && !scheduletrigger.isEmpty()) {
					for (ScheduleTrigger job : scheduletrigger) {
						Date dtNow = new Date();
						if(dtNow.before(job.getExecutetime())) {
							List<PanoramicSystemWarningSourceData> WarningSourceList = systemwarningsourcedatamapper.GetSourceData(job.getExecutesql());
							if(WarningSourceList != null && !WarningSourceList.isEmpty()) {
								DealWarningData(WarningSourceList,job);
							}
    				        Calendar ca = Calendar.getInstance();
    				        ca.setTime(dtNow);
    				        ca.add(Calendar.MINUTE, job.getIntervalTime()); //操作时间
    				        job.setExecutetime(ca.getTime());
						}
					}
			}
        } catch (Exception e) {
           
        }
    }
    
    public void DealWarningData(List<PanoramicSystemWarningSourceData> WarningSourceList,ScheduleTrigger job) {
    	for (PanoramicSystemWarningSourceData  Warning : WarningSourceList) {
    		systemwarningsourcedatamapper.AddWarningSource( Warning.getEventName(),Warning.getStrEvent(), Warning.getEventValue(), Warning.getStatus(), Warning.getCtime(), Warning.getSourceId(),job.getId());
    		List<PanoramicSystemWarningReceiver> receiverList = systemwarningreceiver.GetDataByWarningConfigurationID(job.getId());
    		if(receiverList != null && !receiverList.isEmpty()) {
    			
    		}
    	}
    }
    
    
}