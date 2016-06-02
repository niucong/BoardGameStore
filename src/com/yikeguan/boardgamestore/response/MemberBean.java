package com.yikeguan.boardgamestore.response;

public class MemberBean {

	private LoginBean member;
	private int data_id;
	private int flow_id;
	private String data_type;
	private long create_time;
	private int member_id;

	private int comment_id;
	private int status;
	private String comment_content;

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public LoginBean getMember() {
		return member;
	}

	public void setMember(LoginBean member) {
		this.member = member;
	}

	public int getData_id() {
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public int getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(int flow_id) {
		this.flow_id = flow_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

}
