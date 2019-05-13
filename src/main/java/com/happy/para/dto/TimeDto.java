package com.happy.para.dto;

import java.io.Serializable;

public class TimeDto implements Serializable {

	/**
	 * TIMESHEET DTO
	 */
	private static final long serialVersionUID = -1234655790357755382L;
	private int ts_seq;
	private int alba_seq;
	private String ts_date;
	private String ts_start;
	private String ts_end;
	private int ts_workhour;
	
	public TimeDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TimeDto [ts_seq=" + ts_seq + ", alba_seq=" + alba_seq + ", ts_date=" + ts_date + ", ts_start="
				+ ts_start + ", ts_end=" + ts_end + ", ts_workhour=" + ts_workhour + "]";
	}

	public int getTs_seq() {
		return ts_seq;
	}

	public void setTs_seq(int ts_seq) {
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

	public String getTs_start() {
		return ts_start;
	}

	public void setTs_start(String ts_start) {
		this.ts_start = ts_start;
	}

	public String getTs_end() {
		return ts_end;
	}

	public void setTs_end(String ts_end) {
		this.ts_end = ts_end;
	}

	public int getTs_workhour() {
		return ts_workhour;
	}

	public void setTs_workhour(int ts_workhour) {
		this.ts_workhour = ts_workhour;
	}
	
}
