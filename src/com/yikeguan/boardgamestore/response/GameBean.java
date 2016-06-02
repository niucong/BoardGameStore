package com.yikeguan.boardgamestore.response;

import java.io.Serializable;
import java.util.List;

public class GameBean implements Serializable {

	private String min_age;
	private String expansion;
	private String max_player_number;
	private String stat_with_subdomain_rank;
	private String stat_wtih_avg_rating;
	private String publisher;
	private String min_player_number;
	// private String id;
	private String play_time;
	private String family;
	private String detailed_more;
	private String artist;
	private String main_game_name;
	private String stat_with_subdomain_type;
	private String stat_with_board_game_rank;
	private String stat_with_avg_game_weight;
	private String honor;
	private String website;
	private String designer;
	private int bgg_game_id;
	private String detailed_description;
	private String mechanic;
	private String category;
	private String shortcut_image_path;
	private String language_independent;
	private String subdomain;
	private String image_path;
	private String year_published;
	private String reimplementation;

	// private String reimplementations;// []
	// private String honors;// []
	private List<PublisherBean> publishers;// []
	// private String familys;// []
	private List<DetailedImageBean> detailedImages;// []
	// private String subdomains;// []
	private List<DesignerBean> designers;// []
	private List<MechanicBean> mechanics;// []
	private String isFlow;
	private int grade_member_num;
	// private String expansions;// []
	// private StatisInfoBean statisInfo;
	private int data_id;
	private List<CategoryBean> categorys;// []
	private int grade_totle_num;
	private String grade_score;

	public List<DetailedImageBean> getDetailedImages() {
		return detailedImages;
	}

	public void setDetailedImages(List<DetailedImageBean> detailedImages) {
		this.detailedImages = detailedImages;
	}

	public List<CategoryBean> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<CategoryBean> categorys) {
		this.categorys = categorys;
	}

	public List<MechanicBean> getMechanics() {
		return mechanics;
	}

	public void setMechanics(List<MechanicBean> mechanics) {
		this.mechanics = mechanics;
	}

	public List<PublisherBean> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<PublisherBean> publishers) {
		this.publishers = publishers;
	}

	public List<DesignerBean> getDesigners() {
		return designers;
	}

	public void setDesigners(List<DesignerBean> designers) {
		this.designers = designers;
	}

	public int getData_id() {
		if (data_id == 0)
			data_id = bgg_game_id;
		return data_id;
	}

	public void setData_id(int data_id) {
		this.data_id = data_id;
	}

	public String getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(String isFlow) {
		this.isFlow = isFlow;
	}

	public int getGrade_member_num() {
		return grade_member_num;
	}

	public void setGrade_member_num(int grade_member_num) {
		this.grade_member_num = grade_member_num;
	}

	public int getGrade_totle_num() {
		return grade_totle_num;
	}

	public void setGrade_totle_num(int grade_totle_num) {
		this.grade_totle_num = grade_totle_num;
	}

	public String getGrade_score() {
		return grade_score;
	}

	public void setGrade_score(String grade_score) {
		this.grade_score = grade_score;
	}

	public String getMin_age() {
		return min_age;
	}

	public void setMin_age(String min_age) {
		this.min_age = min_age;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	public String getMax_player_number() {
		return max_player_number;
	}

	public void setMax_player_number(String max_player_number) {
		this.max_player_number = max_player_number;
	}

	public String getStat_with_subdomain_rank() {
		return stat_with_subdomain_rank;
	}

	public void setStat_with_subdomain_rank(String stat_with_subdomain_rank) {
		this.stat_with_subdomain_rank = stat_with_subdomain_rank;
	}

	public String getStat_wtih_avg_rating() {
		return stat_wtih_avg_rating;
	}

	public void setStat_wtih_avg_rating(String stat_wtih_avg_rating) {
		this.stat_wtih_avg_rating = stat_wtih_avg_rating;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getMin_player_number() {
		return min_player_number;
	}

	public void setMin_player_number(String min_player_number) {
		this.min_player_number = min_player_number;
	}

	// public String getId() {
	// return id;
	// }
	//
	// public void setId(String id) {
	// this.id = id;
	// }

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getDetailed_more() {
		return detailed_more;
	}

	public void setDetailed_more(String detailed_more) {
		this.detailed_more = detailed_more;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getMain_game_name() {
		return main_game_name;
	}

	public void setMain_game_name(String main_game_name) {
		this.main_game_name = main_game_name;
	}

	public String getStat_with_subdomain_type() {
		return stat_with_subdomain_type;
	}

	public void setStat_with_subdomain_type(String stat_with_subdomain_type) {
		this.stat_with_subdomain_type = stat_with_subdomain_type;
	}

	public String getStat_with_board_game_rank() {
		return stat_with_board_game_rank;
	}

	public void setStat_with_board_game_rank(String stat_with_board_game_rank) {
		this.stat_with_board_game_rank = stat_with_board_game_rank;
	}

	public String getStat_with_avg_game_weight() {
		return stat_with_avg_game_weight;
	}

	public void setStat_with_avg_game_weight(String stat_with_avg_game_weight) {
		this.stat_with_avg_game_weight = stat_with_avg_game_weight;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public int getBgg_game_id() {
		return bgg_game_id;
	}

	public void setBgg_game_id(int bgg_game_id) {
		this.bgg_game_id = bgg_game_id;
	}

	public String getDetailed_description() {
		return detailed_description;
	}

	public void setDetailed_description(String detailed_description) {
		this.detailed_description = detailed_description;
	}

	public String getMechanic() {
		return mechanic;
	}

	public void setMechanic(String mechanic) {
		this.mechanic = mechanic;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getLanguage_independent() {
		return language_independent;
	}

	public void setLanguage_independent(String language_independent) {
		this.language_independent = language_independent;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getYear_published() {
		return year_published;
	}

	public void setYear_published(String year_published) {
		this.year_published = year_published;
	}

	public String getReimplementation() {
		return reimplementation;
	}

	public void setReimplementation(String reimplementation) {
		this.reimplementation = reimplementation;
	}

}
