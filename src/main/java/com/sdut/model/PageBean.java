package com.sdut.model;

/**
 * 分页的实体
 * 
 * @author Administrator
 *
 */
public class PageBean {
	// 每页中显示的记录数
	private Integer pageSize;
	// 显示的页数
	private Integer page;
	// 总记录数
	private Integer count;
	// 偏移量
	private Integer pianyiliang;
	// 总页数
	private Integer pageCount;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (page == null || page < 1) {
			this.page = 1;
		} else if (page > pageCount) {
			this.page = pageCount;
		} else {

			this.page = page;
		}
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	// 计算总页数
	public void setPageCount() {
		// 总页数=总页数 / 每页显示的记录数
		pageCount = count / pageSize;
		if (count % pageSize != 0) {
			pageCount++;
		}
	}

	public Integer getPianyiliang() {
		return pianyiliang;
	}

	public void setPianyiliang() {
		pianyiliang = (page - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "PageBean [pageSize=" + pageSize + ", page=" + page + ", count=" + count + ", pianyiliang=" + pianyiliang
				+ ", pageCount=" + pageCount + "]";
	}

	public PageBean() {
	}

	/**
	 * 
	 * @param pageSize
	 *            每页中显示的记录数
	 * @param page
	 *            显示的页码
	 * @param count
	 *            总记录数
	 */
	public PageBean(Integer pageSize, Integer page, Integer count) {

		this.pageSize = pageSize;
		this.count = count;
		setPageCount();
		setPage(page);
		setPianyiliang();
	}

}
