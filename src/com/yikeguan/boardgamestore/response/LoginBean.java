package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class LoginBean implements Serializable {

	private StatisInfoBean statisInfo;

	private int flow_num;
	private int re_flow_num;
	private int create_num;
	private String update_time;
	private String member_phone;
	private String create_time;
	private String load_password;
	private String member_email;
	private String load_name;
	private String member_name;
	private int member_status;
	private String session_key;
	private String member_id;

	private String head_url;
	private int id;
	private String head_path;

	private String member_mood;
	private String three_source;
	private String member_sex;
	private String isFlow;
	
	public String getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(String isFlow) {
		this.isFlow = isFlow;
	}

	public StatisInfoBean getStatisInfo() {
		return statisInfo;
	}

	public void setStatisInfo(StatisInfoBean statisInfo) {
		this.statisInfo = statisInfo;
	}

	public int getFlow_num() {
		return flow_num;
	}

	public void setFlow_num(int flow_num) {
		this.flow_num = flow_num;
	}

	public int getRe_flow_num() {
		return re_flow_num;
	}

	public void setRe_flow_num(int re_flow_num) {
		this.re_flow_num = re_flow_num;
	}

	public int getCreate_num() {
		return create_num;
	}

	public void setCreate_num(int create_num) {
		this.create_num = create_num;
	}

	public String getMember_mood() {
		return member_mood;
	}

	public void setMember_mood(String member_mood) {
		this.member_mood = member_mood;
	}

	public String getThree_source() {
		return three_source;
	}

	public void setThree_source(String three_source) {
		this.three_source = three_source;
	}

	public String getMember_sex() {
		return member_sex;
	}

	public void setMember_sex(String member_sex) {
		this.member_sex = member_sex;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHead_path() {
		return head_path;
	}

	public void setHead_path(String head_path) {
		this.head_path = head_path;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLoad_password() {
		return load_password;
	}

	public void setLoad_password(String load_password) {
		this.load_password = load_password;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}

	public String getLoad_name() {
		return load_name;
	}

	public void setLoad_name(String load_name) {
		this.load_name = load_name;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public int getMember_status() {
		return member_status;
	}

	public void setMember_status(int member_status) {
		this.member_status = member_status;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getMember_id() {
		if (member_id == null || "".equals(member_id))
			member_id = "0";
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

}
