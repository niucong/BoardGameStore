package com.yikeguan.boardgamestore.response;

public class VersionBean {

	private String version_desc;
	private String version_name;
	private String version_path;
	private String version_type;
	private long create_time;
	private int version_id;
	private String version_url;

	public String getVersion_desc() {
		return version_desc;
	}

	public void setVersion_desc(String version_desc) {
		this.version_desc = version_desc;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public String getVersion_path() {
		return version_path;
	}

	public void setVersion_path(String version_path) {
		this.version_path = version_path;
	}

	public String getVersion_type() {
		return version_type;
	}

	public void setVersion_type(String version_type) {
		this.version_type = version_type;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public int getVersion_id() {
		return version_id;
	}

	public void setVersion_id(int version_id) {
		this.version_id = version_id;
	}

	public String getVersion_url() {
		return version_url;
	}

	public void setVersion_url(String version_url) {
		this.version_url = version_url;
	}

}
