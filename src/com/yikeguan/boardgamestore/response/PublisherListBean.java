package com.yikeguan.boardgamestore.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublisherListBean implements Serializable {

	private PageBean page;
	private long visitId;
	private List<PublisherBean> publishers;

	public List<PublisherBean> getPublishers() {
		if (publishers == null)
			publishers = new ArrayList<PublisherBean>();
		return publishers;
	}

	public void setPublishers(List<PublisherBean> publishers) {
		this.publishers = publishers;
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
