package com.happy.para.dto;

import java.io.Serializable;

public class FileDto implements Serializable {

	/**
	 * 채팅에 사용되는 파일 DTO
	 */
	private static final long serialVersionUID = -6178134693453009541L;
	private int file_seq;
	private String file_tname;
	private String file_rname;
	private String file_regdate;
	private int file_size;
	private int chat_seq;
	private String file_aurl;
	private String file_rurl;
	
	public FileDto() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public String toString() {
		return "FileDto [file_seq=" + file_seq + ", file_tname=" + file_tname + ", file_rname=" + file_rname
				+ ", file_regdate=" + file_regdate + ", file_size=" + file_size + ", chat_seq=" + chat_seq
				+ ", file_aurl=" + file_aurl + ", file_rurl=" + file_rurl + ", menu_seq=" + menu_seq + "]";
	}


	public String getFile_aurl() {
		return file_aurl;
	}


	public void setFile_aurl(String file_aurl) {
		this.file_aurl = file_aurl;
	}


	public String getFile_rurl() {
		return file_rurl;
	}


	public void setFile_rurl(String file_rurl) {
		this.file_rurl = file_rurl;
	}


	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}

	public String getFile_tname() {
		return file_tname;
	}

	public void setFile_tname(String file_tname) {
		this.file_tname = file_tname;
	}

	public String getFile_rname() {
		return file_rname;
	}

	public void setFile_rname(String file_rname) {
		this.file_rname = file_rname;
	}

	public String getFile_regdate() {
		return file_regdate;
	}

	public void setFile_regdate(String file_regdate) {
		this.file_regdate = file_regdate;
	}

	public int getFile_size() {
		return file_size;
	}

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}

	public int getChat_seq() {
		return chat_seq;
	}

	public void setChat_seq(int chat_seq) {
		this.chat_seq = chat_seq;
	}
	
	private int menu_seq;

	public int getMenu_seq() {
		return menu_seq;
	}

	public void setMenu_seq(int menu_seq) {
		this.menu_seq = menu_seq;
	}
	
}
