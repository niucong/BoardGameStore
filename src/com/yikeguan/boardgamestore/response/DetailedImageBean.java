package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class DetailedImageBean implements Serializable {

	private String image_url;
	private String image_path;

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

}
