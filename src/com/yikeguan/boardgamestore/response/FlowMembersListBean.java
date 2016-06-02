package com.yikeguan.boardgamestore.response;

import java.util.List;

public class FlowMembersListBean {

	private ConditionBean condition;
	private PageBean page;
	private long visitId;
	private List<MemberBean> members;

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

	public List<MemberBean> getMembers() {
		return members;
	}

	public void setMembers(List<MemberBean> members) {
		this.members = members;
	}

}
