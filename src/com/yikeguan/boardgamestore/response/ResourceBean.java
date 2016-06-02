package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class ResourceBean implements Serializable {

	private int data_id;
	private int resource_id;
	private String resource_path;
	private long update_time;
	private int status;
	private int resource_size;
	private String resource_name;
	private String data_type;
	private long create_time;
	private String data_value;
	private String resource_url;

	public int getData_id() {
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public int getResource_id() {
		return resource_id;
	}

	public void setResource_id(int resource_id) {
		this.resource_id = resource_id;
	}

	public String getResource_path() {
		return resource_path;
	}

	public void setResource_path(String resource_path) {
		this.resource_path = resource_path;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getResource_size() {
		return resource_size;
	}

	public void setResource_size(int resource_size) {
		this.resource_size = resource_size;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
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

	public String getData_value() {
		return data_value;
	}

	public void setData_value(String data_value) {
		this.data_value = data_value;
	}

	public String getResource_url() {
		return resource_url;
	}

	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}

}
