package com.cloud.monitor;

import java.util.List;
import java.util.Map;
/**
 * @author summer
 */
public interface DataReport {
	List<Map<String, String>> getReportData();
	ReportType getReptype();
}
