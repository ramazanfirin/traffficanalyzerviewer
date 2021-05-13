package trafficanalyzerviewer.domain;

import java.time.Instant;

public class VideoRecordQueryVM {

	Long id;
	Instant insertDate;
	Long lineId;
	String vehicleType;
	Long duration;
	
	
	
	public VideoRecordQueryVM() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoRecordQueryVM(Long id,String vehicleType,Instant insertDate,Long lineId,Long duration) {
		super();
		this.id = id;
		this.insertDate = insertDate;
		this.lineId = lineId;
		this.vehicleType = vehicleType;
		this.duration = duration;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Instant getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Instant insertDate) {
		this.insertDate = insertDate;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	
}
