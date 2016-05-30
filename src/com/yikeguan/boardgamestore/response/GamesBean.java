package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class GamesBean implements Serializable {

	private int data_id;
	private int collection_id;
	private String data_type;
	private long create_time;
	private GameBean game;
	private int member_id;

	public int getData_id() {
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public int getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(int collection_id) {
		this.collection_id = collection_id;
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

	public GameBean getGame() {
		return game;
	}

	public void setGame(GameBean game) {
		this.game = game;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

}
