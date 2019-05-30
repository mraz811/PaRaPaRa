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
	private Double ts_daywork        ;
	private Double ts_nightwork        ;
	private Double ts_workhour        ;
	
	private Double earlywork;
	private Double daywork;
	private Double nightwork;
	
	public TimeDto() {
	}

	public TimeDto(String ts_seq, int alba_seq, String ts_date, String ts_datetime, Double ts_daywork,
			Double ts_nightwork, Double ts_workhour) {
		super();
		this.ts_seq = ts_seq;
		this.alba_seq = alba_seq;
		this.ts_date = ts_date;
		this.ts_datetime = ts_datetime;
		this.ts_daywork = ts_daywork;
		this.ts_nightwork = ts_nightwork;
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

	public Double getTs_daywork() {
		return ts_daywork;
	}

	public void setTs_daywork(Double ts_daywork) {
		this.ts_daywork = ts_daywork;
	}

	public Double getTs_nightwork() {
		return ts_nightwork;
	}

	public void setTs_nightwork(Double ts_nightwork) {
		this.ts_nightwork = ts_nightwork;
	}

	public Double getTs_workhour() {
		return ts_workhour;
	}

	public void setTs_workhour(Double ts_workhour) {
		this.ts_workhour = ts_workhour;
	}

	public Double getEarlywork() {
		return earlywork;
	}

	public void setEarlywork(Double earlywork) {
		this.earlywork = earlywork;
	}

	public Double getDaywork() {
		return daywork;
	}

	public void setDaywork(Double daywork) {
		this.daywork = daywork;
	}

	public Double getNightwork() {
		return nightwork;
	}

	public void setNightwork(Double nightwork) {
		this.nightwork = nightwork;
	}

	@Override
	public String toString() {
		return "TimeDto [ts_seq=" + ts_seq + ", alba_seq=" + alba_seq + ", ts_date=" + ts_date + ", ts_datetime="
				+ ts_datetime + ", ts_daywork=" + ts_daywork + ", ts_nightwork=" + ts_nightwork + ", ts_workhour="
				+ ts_workhour + ", earlywork=" + earlywork + ", daywork=" + daywork + ", nightwork=" + nightwork + "]";
	}

	
}
