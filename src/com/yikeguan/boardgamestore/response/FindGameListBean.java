package com.yikeguan.boardgamestore.response;

import java.util.ArrayList;
import java.util.List;

public class FindGameListBean {

	private PageBean page;
	private long visitId;
	private List<GameBean> games;
	private ConditionBean publisher;

	public ConditionBean getPublisher() {
		return publisher;
	}

	public void setPublisher(ConditionBean publisher) {
		this.publisher = publisher;
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

	public List<GameBean> getGames() {
		if (games == null)
			games = new ArrayList<GameBean>();
		return games;
	}

	public void setGames(List<GameBean> games) {
		this.games = games;
	}

}
