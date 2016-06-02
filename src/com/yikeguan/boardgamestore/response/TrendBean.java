package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class TrendBean implements Serializable {

	private LoginBean member;
	private int data_id;
	private long trends_id;
	private long update_time;
	private String trend_type;
	private String data_type;
	private String trend_content;
	private long create_time;
	// private GameBean object;
	private int member_id;
	private ResourceInfoBean resourceInfo;
	private String uuid;
	private int trend_resource_id;
	private StatisInfoBean statisInfo;
	private SunpicInfoBean object;
	private String notice_content;

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public SunpicInfoBean getObject() {
		return object;
	}

	public void setObject(SunpicInfoBean object) {
		this.object = object;
	}

	public ResourceInfoBean getResourceInfo() {
		return resourceInfo;
	}

	public void setResourceInfo(ResourceInfoBean resourceInfo) {
		this.resourceInfo = resourceInfo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getTrend_resource_id() {
		return trend_resource_id;
	}

	public void setTrend_resource_id(int trend_resource_id) {
		this.trend_resource_id = trend_resource_id;
	}

	public StatisInfoBean getStatisInfo() {
		return statisInfo;
	}

	public void setStatisInfo(StatisInfoBean statisInfo) {
		this.statisInfo = statisInfo;
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

	public long getTrends_id() {
		return trends_id;
	}

	public void setTrends_id(long trends_id) {
		this.trends_id = trends_id;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public String getTrend_type() {
		return trend_type;
	}

	public void setTrend_type(String trend_type) {
		this.trend_type = trend_type;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getTrend_content() {
		return trend_content;
	}

	public void setTrend_content(String trend_content) {
		this.trend_content = trend_content;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	// public GameBean getObject() {
	// return object;
	// }
	//
	// public void setObject(GameBean object) {
	// this.object = object;
	// }

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

}
