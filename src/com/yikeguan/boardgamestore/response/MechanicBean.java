package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class MechanicBean implements Serializable {

	private int bgg_mechanic_id;
	private String mechanic_name;
	private String mechanic_description;

	public int getBgg_mechanic_id() {
		return bgg_mechanic_id;
	}

	public void setBgg_mechanic_id(int bgg_mechanic_id) {
		this.bgg_mechanic_id = bgg_mechanic_id;
	}

	public String getMechanic_name() {
		return mechanic_name;
	}

	public void setMechanic_name(String mechanic_name) {
		this.mechanic_name = mechanic_name;
	}

	public String getMechanic_description() {
		return mechanic_description;
	}

	public void setMechanic_description(String mechanic_description) {
		this.mechanic_description = mechanic_description;
	}

}
