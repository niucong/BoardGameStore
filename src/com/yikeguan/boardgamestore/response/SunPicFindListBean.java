package com.yikeguan.boardgamestore.response;

import java.util.List;

public class SunPicFindListBean {

	private ConditionBean condition;
	private PageBean page;
	private long visitId;
	private List<SunpicInfoBean> suns;
	private List<SunpicInfoBean> recommends;

	public List<SunpicInfoBean> getRecommends() {
		return recommends;
	}

	public void setRecommends(List<SunpicInfoBean> recommends) {
		this.recommends = recommends;
	}

	public List<SunpicInfoBean> getSuns() {
		return suns;
	}

	public void setSuns(List<SunpicInfoBean> suns) {
		this.suns = suns;
	}

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

}
