package com.yikeguan.boardgamestore.response;

import java.io.Serializable;

public class StatisInfoBean implements Serializable {

	private int create_recommend_num;
	private int flow_game_num;
	private int create_sun_num;
	private int collection_game_num;
	private int flow_member_num;
	private int member_flow_num;

	public int getMember_flow_num() {
		return member_flow_num;
	}

	public void setMember_flow_num(int member_flow_num) {
		this.member_flow_num = member_flow_num;
	}

	private int collection_num;
	private int share_num;
	private int flow_num;
	private int data_id;
	private int visit_num;
	private String data_type;
	private int comment_num;
	private int statis_id;

	public int getCollection_num() {
		return collection_num;
	}

	public void setCollection_num(int collection_num) {
		this.collection_num = collection_num;
	}

	public int getShare_num() {
		return share_num;
	}

	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}

	public int getFlow_num() {
		return flow_num;
	}

	public void setFlow_num(int flow_num) {
		this.flow_num = flow_num;
	}

	public int getData_id() {
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public int getVisit_num() {
		return visit_num;
	}

	public void setVisit_num(int visit_num) {
		this.visit_num = visit_num;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	public int getStatis_id() {
		return statis_id;
	}

	public void setStatis_id(int statis_id) {
		this.statis_id = statis_id;
	}

	public int getCreate_recommend_num() {
		return create_recommend_num;
	}

	public void setCreate_recommend_num(int create_recommend_num) {
		this.create_recommend_num = create_recommend_num;
	}

	public int getFlow_game_num() {
		return flow_game_num;
	}

	public void setFlow_game_num(int flow_game_num) {
		this.flow_game_num = flow_game_num;
	}

	public int getCreate_sun_num() {
		return create_sun_num;
	}

	public void setCreate_sun_num(int create_sun_num) {
		this.create_sun_num = create_sun_num;
	}

	public int getCollection_game_num() {
		return collection_game_num;
	}

	public void setCollection_game_num(int collection_game_num) {
		this.collection_game_num = collection_game_num;
	}

	public int getFlow_member_num() {
		return flow_member_num;
	}

	public void setFlow_member_num(int flow_member_num) {
		this.flow_member_num = flow_member_num;
	}

}
