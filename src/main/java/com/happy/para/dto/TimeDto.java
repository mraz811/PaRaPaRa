package com.happy.para.dto;

import java.io.Serializable;

public class TimeDto implements Serializable {

	/**
	 * TIMESHEET DTO
	 */
	private static final long serialVersionUID = -1234655790357755382L;
	private String ts_seq             ;
	private int alba_seq           ;
	private String ts_date            ;
	private String ts_datetime        ;
	private Double ts_workhour        ;
	
	public TimeDto() {
	}

	public TimeDto(String ts_seq, int alba_seq, String ts_date, String ts_datetime, Double ts_workhour) {
		super();
		this.ts_seq = ts_seq;
		this.alba_seq = alba_seq;
		this.ts_date = ts_date;
		this.ts_datetime = ts_datetime;
		this.ts_workhour = ts_workhour;
	}

	public String getTs_seq() {
		return ts_seq;
	}

	public void setTs_seq(String ts_seq) {
		this.ts_seq = ts_seq;
	}

	public int getAlba_seq() {
		return alba_seq;
	}

	public void setAlba_seq(int alba_seq) {
		this.alba_seq = alba_seq;
	}

	public String getTs_date() {
		return ts_date;
	}

	public void setTs_date(String ts_date) {
		this.ts_date = ts_date;
	}

	public String getTs_datetime() {
		return ts_datetime;
	}

	public void setTs_datetime(String ts_datetime) {
		this.ts_datetime = ts_datetime;
	}

	public Double getTs_workhour() {
		return ts_workhour;
	}

	public void setTs_workhour(Double ts_workhour) {
		this.ts_workhour = ts_workhour;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TimeDto [ts_seq=" + ts_seq + ", alba_seq=" + alba_seq + ", ts_date=" + ts_date + ", ts_datetime="
				+ ts_datetime + ", ts_workhour=" + ts_workhour + "]";
	}

	
	
	
	
}
