package com.yikeguan.boardgamestore.response;

public class MessageBean {

	private LoginBean member;
	private long update_time;
	private int receive_member_id;
	private int send_member_id;
	private long create_time;
	private int message_id;
	private int is_read;
	private String message_content;
	private int contact_member_id;
	private int member_id;

	public LoginBean getMember() {
		return member;
	}

	public void setMember(LoginBean member) {
		this.member = member;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public int getReceive_member_id() {
		return receive_member_id;
	}

	public void setReceive_member_id(int receive_member_id) {
		this.receive_member_id = receive_member_id;
	}

	public int getSend_member_id() {
		return send_member_id;
	}

	public void setSend_member_id(int send_member_id) {
		this.send_member_id = send_member_id;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

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
}
