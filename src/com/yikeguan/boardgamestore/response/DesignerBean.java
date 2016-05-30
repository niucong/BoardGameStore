package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class DesignerBean implements Serializable {

	// private int ID;
	private String designer_name;
	private String shortcut_image_path;
	private int bgg_designer_id;
	private String image_path;
	private int designer_type;
	private String designer_description;

	// public int getID() {
	// return ID;
	// }
	//
	// public void setID(int iD) {
	// ID = iD;
	// }

	public String getDesigner_name() {
		return designer_name;
	}

	public void setDesigner_name(String designer_name) {
		this.designer_name = designer_name;
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

	public int getBgg_designer_id() {
		return bgg_designer_id;
	}

	public void setBgg_designer_id(int bgg_designer_id) {
		this.bgg_designer_id = bgg_designer_id;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public int getDesigner_type() {
		return designer_type;
	}

	public void setDesigner_type(int designer_type) {
		this.designer_type = designer_type;
	}

	public String getDesigner_description() {
		return designer_description;
	}

	public void setDesigner_description(String designer_description) {
		this.designer_description = designer_description;
	}

}
