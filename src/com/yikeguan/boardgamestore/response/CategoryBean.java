package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class CategoryBean implements Serializable {

	private int bgg_category_id;
	private String category_name;
	private String category_description;

	public int getBgg_category_id() {
		return bgg_category_id;
	}

	public void setBgg_category_id(int bgg_category_id) {
		this.bgg_category_id = bgg_category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_description() {
		return category_description;
	}

	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}
}
