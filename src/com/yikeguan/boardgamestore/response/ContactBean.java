package com.yikeguan.boardgamestore.response;

public class ContactBean {

	private LoginBean member;
	private int unread_num;
	private long update_time;
	private int all_message_num;
	private int contact_id;
	private long create_time;
	private int is_read;
	private int contact_member_id;
	private int member_id;

	public LoginBean getMember() {
		return member;
	}

	public void setMember(LoginBean member) {
		this.member = member;
	}

	public int getUnread_num() {
		return unread_num;
	}

	public void setUnread_num(int unread_num) {
		this.unread_num = unread_num;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public int getAll_message_num() {
		return all_message_num;
	}

	public void setAll_message_num(int all_message_num) {
		this.all_message_num = all_message_num;
	}

	public int getContact_id() {
		return contact_id;
	}

	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
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
