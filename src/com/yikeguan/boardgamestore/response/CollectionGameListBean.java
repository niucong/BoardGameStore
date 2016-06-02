package com.yikeguan.boardgamestore.response;

import java.util.ArrayList;
import java.util.List;

public class CollectionGameListBean {

	private PageBean page;
	private long visitId;
	private List<GamesBean> games;
	private ConditionBean condition;

	public ConditionBean getCondition() {
		return condition;
	}

	public void setCondition(ConditionBean condition) {
		this.condition = condition;
	}

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}

	public long getVisitId() {
		return visitId;
	}

	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}

	public List<GamesBean> getGames() {
		if (games == null)
			games = new ArrayList<GamesBean>();
		return games;
	}

	public void setGames(List<GamesBean> games) {
		this.games = games;
	}

}
