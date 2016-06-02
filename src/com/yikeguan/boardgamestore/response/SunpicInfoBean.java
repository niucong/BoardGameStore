package com.yikeguan.boardgamestore.response;

import java.io.Serializable;
import java.util.List;

public class SunpicInfoBean implements Serializable {

	private List<ResourceBean> resources;
	private String obj_desc;
	private String data_type;
	private long create_time;
	private String obj_name;
	private String obj_id;
	private String member_id;

	private LoginBean member;

	private String main_game_name;
	private String shortcut_image_path;
	private String subdomain;
	private String year_published;
	private String designer;
	private String min_player_number;
	private String max_player_number;
	private String min_age;
	private String play_time;
	private String stat_with_subdomain_type;
	private String mechanic;
	private String detailed_description;
	private int bgg_game_id;

	public String getShortcut_image_path() {
		return shortcut_image_path;
	}

	public void setShortcut_image_path(String shortcut_image_path) {
		this.shortcut_image_path = shortcut_image_path;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public String getYear_published() {
		return year_published;
	}

	public void setYear_published(String year_published) {
		this.year_published = year_published;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getMin_player_number() {
		return min_player_number;
	}

	public void setMin_player_number(String min_player_number) {
		this.min_player_number = min_player_number;
	}

	public String getMax_player_number() {
		return max_player_number;
	}

	public void setMax_player_number(String max_player_number) {
		this.max_player_number = max_player_number;
	}

	public String getMin_age() {
		return min_age;
	}

	public void setMin_age(String min_age) {
		this.min_age = min_age;
	}

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}

	public String getStat_with_subdomain_type() {
		return stat_with_subdomain_type;
	}

	public void setStat_with_subdomain_type(String stat_with_subdomain_type) {
		this.stat_with_subdomain_type = stat_with_subdomain_type;
	}

	public String getMechanic() {
		return mechanic;
	}

	public void setMechanic(String mechanic) {
		this.mechanic = mechanic;
	}

	public String getDetailed_description() {
		return detailed_description;
	}

	public void setDetailed_description(String detailed_description) {
		this.detailed_description = detailed_description;
	}

	public int getBgg_game_id() {
		return bgg_game_id;
	}

	public void setBgg_game_id(int bgg_game_id) {
		this.bgg_game_id = bgg_game_id;
	}

	public String getMain_game_name() {
		return main_game_name;
	}

	public void setMain_game_name(String main_game_name) {
		this.main_game_name = main_game_name;
	}

	public LoginBean getMember() {
		return member;
	}

	public void setMember(LoginBean member) {
		this.member = member;
	}

	public List<ResourceBean> getResources() {
		return resources;
	}

	public void setResources(List<ResourceBean> resources) {
		this.resources = resources;
	}

	public String getObj_desc() {
		return obj_desc;
	}

	public void setObj_desc(String obj_desc) {
		this.obj_desc = obj_desc;
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

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

}
