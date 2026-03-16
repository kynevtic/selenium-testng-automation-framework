package com.ui.pojo;

public class Environment {
	private String url;
	private int MAXIMUM_NUMBER_OF_ATTEMPTS;

	public int getMAXIMUM_NUMBER_OF_ATTEMPTS() {
		return MAXIMUM_NUMBER_OF_ATTEMPTS;
	}

	public void setMAXIMUM_NUMBER_OF_ATTEMPTS(int MAXIMUM_NUMBER_OF_ATTEMPTS) {
		this.MAXIMUM_NUMBER_OF_ATTEMPTS = MAXIMUM_NUMBER_OF_ATTEMPTS;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
