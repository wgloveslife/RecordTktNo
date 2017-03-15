package com.jetair.mub2gmjob.service.recordTktNo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 记录成功出票的订单
 * @author WangGang
 * @date 2017年3月8日 上午9:58:33
 * @version
 *
 */
@Component("ticketMonitorJob")
public class TicketMonitorJob {
	private static Logger logger = LogManager.getLogger(TicketMonitorJob.class.getName());
	
	@Autowired
	@Qualifier("ticketMonitorService")
	private TicketMonitorService ticketMonitorService;
	
	public void executeRecordTktNo() {
		try {
			logger.info("RecordTktNo Begin...");
			this.ticketMonitorService.recordTktNo();
			logger.info("RecordTktNo End...");
		} catch (Exception e) {
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement stackTraceElement = stackTrace[i];
				logger.error(stackTraceElement.toString());
			}
			
		}
	}
}
