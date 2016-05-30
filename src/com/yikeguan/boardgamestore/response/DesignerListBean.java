package com.yikeguan.boardgamestore.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DesignerListBean implements Serializable {

	private PageBean page;
	private long visitId;
	private List<DesignerBean> designers;

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}

	public List<DesignerBean> getDesigners() {
		if (designers == null)
			designers = new ArrayList<DesignerBean>();
		return designers;
	}

	public void setDesigners(List<DesignerBean> designers) {
		this.designers = designers;
	}

	public long getVisitId() {
		return visitId;
	}

	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}

}
