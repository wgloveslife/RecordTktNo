package com.jetair.mub2gmjob.service.recordTktNo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cares.ceaec.web.wsdto.b2g.order.OrderDetailsSearchCond;
import com.cares.ceaec.web.wsdto.b2g.order.OrderDetailsSearchResponse;
import com.cares.ceaec.web.wsdto.b2g.order.TktInfo;
import com.cares.ceaec.webservice.facade.order.B2GOrderManagerFacadeServicePortType;
import com.jetair.mub2gm.common.config.AppConfig;
import com.jetair.mub2gm.domain.orderLog.TravelOrderLogDo;
import com.jetair.mub2gm.dto.order.TicketInfoResDto;
import com.jetair.mub2gm.utils.CommonUtil;
import com.jetair.mub2gmjob.domain.recordTktNo.TicketDo;
import com.jetair.mub2gmjob.persistence.mapper.recordTktNo.TicketMonitorMapper;

/**
 * 
 * @author WangGang
 * @date 2017年3月8日 上午10:01:14
 * @version
 *
 */

@Service("ticketMonitorService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TicketMonitorServiceImpl implements TicketMonitorService {

	@Autowired
	private TicketMonitorMapper ticketMonitorMapper;

	@Autowired
	@Qualifier("b2GOrderManagerFacadeServicePortType")
	private B2GOrderManagerFacadeServicePortType b2GOrderManagerFacadeServicePortType;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void recordTktNo() {
		List<TravelOrderLogDo> queryTravelOrderLog = new ArrayList<TravelOrderLogDo>();
		List<TicketInfoResDto> ticketList = new ArrayList<TicketInfoResDto>();
		//查询出近期新增的订单以及用户信息
		queryTravelOrderLog = ticketMonitorMapper.queryTravelOrderLog();
		
		//记录票号持久化对象
		TicketDo ticketDo = new TicketDo();
		
		for (TravelOrderLogDo travelOrderLogDo : queryTravelOrderLog) {//遍历近期新增的订单，记录其出票情况
			
			//获取票号
			ticketList = this.queryTktNo(travelOrderLogDo);
			
			for (TicketInfoResDto ticketInfoResDto : ticketList) {
				if (travelOrderLogDo.getPaxName().equals(ticketInfoResDto.getPaxName())) {	//当前票号是否为乘机人本人票号
					if (StringUtils.isBlank(ticketInfoResDto.getTicketNo())) {	//当前订单是否存在票号，不存在则不记录（即未出票）
						continue;
					}
					ticketDo.setAirlineBillingCode(ticketInfoResDto.getTicketNo().substring(0, 3));
					ticketDo.setTicketNo(ticketInfoResDto.getTicketNo().substring(4));		//票号
					ticketDo.setPaxId(travelOrderLogDo.getPassengerId());
					//持久化票号信息
					ticketMonitorMapper.insertTktInfo(ticketDo);
				}
			}
		}
	}
	
	/**
	 * 根据订单号查询票号
	 * @param travelOrderLogDo	调用接口的信息
	 * @return
	 */
	private List<TicketInfoResDto> queryTktNo(TravelOrderLogDo travelOrderLogDo) {
		List<TicketInfoResDto> ticketList = new ArrayList<TicketInfoResDto>();
		//入参对象
		OrderDetailsSearchCond orderDetailsSearchCond = new OrderDetailsSearchCond();
		//接口响应参数
		OrderDetailsSearchResponse orderDetailsSearchResponse = null;
		
		orderDetailsSearchCond.setAcctNo(travelOrderLogDo.getUserId().substring(0, 6));
		orderDetailsSearchCond.setOpSq(travelOrderLogDo.getOrderNum());//订单号
		orderDetailsSearchCond.setAcctNoA(travelOrderLogDo.getUserId()); // 用户ID, usrid:C00002000001
		orderDetailsSearchCond.setUSRDESC(CommonUtil.getUsrDesc(travelOrderLogDo.getUserKamno()));	//账户描述  3/KA/大客户号/NULL/NULL
		orderDetailsSearchCond.setUSRID(AppConfig.B2G_USRID);	//调用WS权限验证时的账号
		orderDetailsSearchCond.setUSRPWD(AppConfig.B2G_USRPWD);//调用WS权限验证时的密码
		orderDetailsSearchCond.setUSRSQ(CommonUtil.getUsrSq(travelOrderLogDo.getUserId()));
		
		//开始调用WS
		orderDetailsSearchResponse = b2GOrderManagerFacadeServicePortType.orderDetailsSearch(orderDetailsSearchCond);
		
		if (StringUtils.isBlank(orderDetailsSearchResponse.getRTNERRID())) {
			//乘机人的航段信息
			List<TktInfo> tktInfos = orderDetailsSearchResponse.getTktInfo().getTktInfo();
			
			for (TktInfo tktInfo : tktInfos) {
				TicketInfoResDto ticketInfoResDto = new TicketInfoResDto();
				
				if (travelOrderLogDo.getPaxName().equals(tktInfo.getPaxNm())) {
					ticketInfoResDto.setTicketNo(tktInfo.getTktNo());	//乘机人票号
					ticketInfoResDto.setPaxName(tktInfo.getPaxNm());	//乘机人姓名
					
					ticketList.add(ticketInfoResDto);
				}
			}
		}
		
		return ticketList;
	}
}
