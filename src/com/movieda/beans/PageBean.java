package com.movieda.beans;

public class PageBean {

	// 최소 페이지 번호
	private int min;
	// 최대 페이지 번호
	private int max;
	// 이전 페이지 번호
	private int prevPage;
	// 다음 페이지 번호
	private int nextPage;
	// 전체 페이지 개수
	private int pageCnt;
	// 현재 페이지 번호
	private int currentPage;
	
	// contentCnt: 전체 글 개수, currentPage: 현재글 번호
	// contentPageCnt: 페이지당 글 개수, paginationCnt: 페이지 버튼 개수
	
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		
		// 현재 페이지 번호
		this.currentPage = currentPage;
		
		// 전체 페이지수는 전체 글에서 페이지당 개수를 나누면 됨
		// 하지만 전체 글 95개인 경우 나누면 10 페이지가 필요한데 9페이지가 만들어짐
		// 이를 고려하면 일의 자리수가 존재할때 전체 페이지 수를 1 증가 시킬 필요가 있음
		pageCnt = contentCnt / contentPageCnt;
		if(contentCnt % contentPageCnt > 0) pageCnt ++;
		
		// 
		min = ((currentPage - 1) / contentPageCnt) * contentPageCnt + 1;
		max = min + paginationCnt - 1;
		
		// 최대 페이지가 전체 페이지 수를 넘어가면 안됨
		if(max > pageCnt) {
			max = pageCnt;
		}
		
		prevPage = min - 1;
		nextPage = max + 1;
		
		// 다음 페이지 번호가 전체 페이지 수를 넘어가면 안됨
		if(nextPage > pageCnt) {
			nextPage = pageCnt;
		}
		
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
