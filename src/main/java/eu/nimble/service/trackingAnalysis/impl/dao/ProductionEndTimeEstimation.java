package eu.nimble.service.trackingAnalysis.impl.dao;

public class ProductionEndTimeEstimation {
	private String epc;
	private String productionEndTime;
	private String message;

	
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	public String getProductionEndTime() {
		return productionEndTime;
	}
	public void setProductionEndTime(String productionEndTime) {
		this.productionEndTime = productionEndTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
