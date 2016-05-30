package com.yikeguan.boardgamestore.response;

import java.util.ArrayList;
import java.util.List;

public class TrendListBean {

	private PageBean page;
	private String condition;
	private long visitId;
	private List<TrendBean> trends;

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public long getVisitId() {
		return visitId;
	}

	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}

	public List<TrendBean> getTrends() {
		if (trends == null)
			trends = new ArrayList<TrendBean>();
		return trends;
	}

	public void setTrends(List<TrendBean> trends) {
		this.trends = trends;
	}

}
