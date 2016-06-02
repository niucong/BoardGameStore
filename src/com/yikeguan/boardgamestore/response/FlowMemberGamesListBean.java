package com.yikeguan.boardgamestore.response;

import java.util.List;

public class FlowMemberGamesListBean {

	private ConditionBean condition;
	private PageBean page;
	private long visitId;
	private List<GamesBean> games;

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

	public ConditionBean getCondition() {
		return condition;
	}

	public void setCondition(ConditionBean condition) {
		this.condition = condition;
	}

	public List<GamesBean> getGames() {
		return games;
	}

	public void setGames(List<GamesBean> games) {
		this.games = games;
	}

}
