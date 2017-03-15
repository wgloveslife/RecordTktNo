package com.jetair.mub2gmjob.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jetair.mub2gm.domain.orderLog.TravelOrderLogDo;
import com.jetair.mub2gmjob.persistence.mapper.recordTktNo.TicketMonitorMapper;
import com.jetair.mub2gmjob.service.recordTktNo.TicketMonitorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:applicationContext-db-test.xml","classpath:cxf-client-test-inner.xml"})
public class QueryTest {
	
	@Autowired
	private TicketMonitorMapper ticketMonitorMapper;
	
	@Autowired
	@Qualifier("ticketMonitorService")
	TicketMonitorService ticketMonitorService;
	
	/*@Test
	public void testQuery() {
		
		ticketMonitorService.recordTktNo();
		//List<String> queryPaxId = ticketMonitorMapper.queryPaxId("02892998");
		List<String> queryPaxId = ticketMonitorMapper.queryPaxId("02893001");
		
		System.out.println(queryPaxId.toString());
	}
	
	@Test
	public void testQueryPaxId() {
		
		//List<String> queryPaxId = ticketMonitorMapper.queryPaxId("02892998");
		List<String> queryPaxId = ticketMonitorMapper.queryPaxId("02893001");
		
		System.out.println(queryPaxId.toString());
	}*/
	
	@Test
	public void testQueryTravelOrderLog() {
		List<TravelOrderLogDo> queryTravelOrderLog = ticketMonitorMapper.queryTravelOrderLog();
		
		if (null != queryTravelOrderLog) {
			for (TravelOrderLogDo travelOrderLogDo : queryTravelOrderLog) {
				System.out.println(travelOrderLogDo.getUserId() + "\t" + travelOrderLogDo.getUserKamno());
			}
		}
		System.out.println(queryTravelOrderLog.size());
	}
	
	
}
