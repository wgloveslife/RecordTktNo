package com.jetair.mub2gmjob.persistence.mapper.recordTktNo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jetair.mub2gm.domain.orderLog.TravelOrderLogDo;
import com.jetair.mub2gmjob.domain.recordTktNo.TicketDo;

/**
 * 出票数据层接口
 * @author WangGang
 * @date 2017年3月8日 上午10:02:03
 * @version
 *
 */
@Repository("ticketMonitorMapper")
public interface TicketMonitorMapper {
	
	/**
	 * 查出新增的订单相关信息，用于调用接口查询确定票号
	 * @return 
	 */
	List<TravelOrderLogDo> queryTravelOrderLog();
	
	/**
	 * 插入旅客和订单相关联的票号
	 */
	void insertTktInfo(TicketDo ticketDo);
	
}
