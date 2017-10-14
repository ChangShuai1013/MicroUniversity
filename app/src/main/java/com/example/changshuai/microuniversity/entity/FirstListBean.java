package com.example.changshuai.microuniversity.entity;

public class FirstListBean {
	private String title;
	private String time;
	private String href;
	public FirstListBean() {}
	
	public FirstListBean(String title, String time, String href) {
		super();
		this.title = title;
		this.time = time;
		this.href = href;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "MainBean [title=" + title + ", time=" + time + ", href=" + href + "]";
	}

}
