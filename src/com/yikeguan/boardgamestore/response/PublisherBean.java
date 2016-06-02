package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class PublisherBean implements Serializable {

	private int id;
	private String publisher_name;
	private String shortcut_image_path;
	private int bgg_publisher_id;
	private String image_path;
	private String publisher_website;
	private String publisher_description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public String getShortcut_image_path() {
		if (shortcut_image_path != null
				&& !shortcut_image_path.startsWith("http"))
			return "http://" + shortcut_image_path;
		return shortcut_image_path;
	}

	public void setShortcut_image_path(String shortcut_image_path) {
		this.shortcut_image_path = shortcut_image_path;
	}

	public int getBgg_publisher_id() {
		return bgg_publisher_id;
	}

	public void setBgg_publisher_id(int bgg_publisher_id) {
		this.bgg_publisher_id = bgg_publisher_id;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getPublisher_website() {
		return publisher_website;
	}

	public void setPublisher_website(String publisher_website) {
		this.publisher_website = publisher_website;
	}

	public String getPublisher_description() {
		return publisher_description;
	}

	public void setPublisher_description(String publisher_description) {
		this.publisher_description = publisher_description;
	}

}
