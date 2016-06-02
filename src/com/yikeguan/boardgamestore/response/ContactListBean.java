package com.yikeguan.boardgamestore.response;

import java.util.List;

public class ContactListBean {

	private ConditionBean condition;
	private PageBean page;
	private long visitId;
	private List<ContactBean> messages;

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

	public List<ContactBean> getMessages() {
		return messages;
	}

	public void setMessages(List<ContactBean> messages) {
		this.messages = messages;
	}

}
