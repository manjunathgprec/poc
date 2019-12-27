package com.anthem.emep.dckr.microsvc.dataservicexbdf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Krothama
 *
 * @param <E>
 */
public class Page<E> implements Serializable {

	/**
	 * The Serial Version Id
	 */
	private static final long serialVersionUID = 1L;

	// The Page Index to store the current page number
	private Integer pageIndex;

	// The page count for the grid
	private Integer pageCount;

	// Total rows from the query
	private long totalRows;

	// List of page items
	private List<E> pageItems = new ArrayList<E>();

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public List<E> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<E> pageItems) {
		this.pageItems = pageItems;
	}

}
