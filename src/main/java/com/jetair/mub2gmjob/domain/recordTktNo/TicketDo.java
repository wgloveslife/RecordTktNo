package com.jetair.mub2gmjob.domain.recordTktNo;

/**
 * 出票存储模型
 * 
 * @author WangGang
 * @date 2017年3月8日 上午10:27:37
 * @version
 *
 */
public class TicketDo {
	private String recId;
	private String airlineBillingCode; // 航空公司结算代码，如781
	private String ticketNo; // 票号
	private String paxId; // 乘机人ID
	private String flightSegId;// 航段ID

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getAirlineBillingCode() {
		return airlineBillingCode;
	}

	public void setAirlineBillingCode(String airlineBillingCode) {
		this.airlineBillingCode = airlineBillingCode;
	}

	public String getPaxId() {
		return paxId;
	}

	public void setPaxId(String paxId) {
		this.paxId = paxId;
	}

	public String getFlightSegId() {
		return flightSegId;
	}

	public void setFlightSegId(String flightSegId) {
		this.flightSegId = flightSegId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	@Override
	public String toString() {
		return "TicketDo [recId=" + recId + ", airlineBillingCode=" + airlineBillingCode + ", ticketNo=" + ticketNo + ", paxId=" + paxId + ", flightSegId=" + flightSegId + "]";
	}
}
