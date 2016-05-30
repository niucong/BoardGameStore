package com.yikeguan.boardgamestore.response;

import java.util.List;

public class ResourceInfoBean {

	private int data_id;
	private long update_time;
	private String data_type;
	private long create_time;
	private SunpicInfoBean object;
	private int member_id;
	private int trend_resource_id;
	private List<CommentBean> comments;

	public List<CommentBean> getComments() {
		return comments;
	}

	public void setComments(List<CommentBean> comments) {
		this.comments = comments;
	}

	public int getData_id() {
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
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

	public SunpicInfoBean getObject() {
		return object;
	}

	public void setObject(SunpicInfoBean object) {
		this.object = object;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getTrend_resource_id() {
		return trend_resource_id;
	}

	public void setTrend_resource_id(int trend_resource_id) {
		this.trend_resource_id = trend_resource_id;
	}

}
