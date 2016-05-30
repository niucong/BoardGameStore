package com.yikeguan.boardgamestore.response;

public class ConditionBean {

	private int contact_member_id;
	private int member_id;
	private int data_id;
	private String data_type;

	public int getContact_member_id() {
		return contact_member_id;
	}

	public void setContact_member_id(int contact_member_id) {
		this.contact_member_id = contact_member_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getData_id() {
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
}
