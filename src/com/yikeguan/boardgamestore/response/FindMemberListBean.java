package com.yikeguan.boardgamestore.response;

import java.util.List;

public class FindMemberListBean {

	private PageBean page;
	private long visitId;
	private List<LoginBean> members;

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

	public List<LoginBean> getMembers() {
		return members;
	}

	public void setMembers(List<LoginBean> members) {
		this.members = members;
	}

}
