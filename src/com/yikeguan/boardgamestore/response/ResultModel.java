/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * Copyright 2010-2013 DataComo Communications Technology INC.
 * 
 * This source file is a part of MID_WINS_API_V1.0 project. 
 * date: 2013-11-12
 *
 */
package com.yikeguan.boardgamestore.response;

/**
 * 
 * @author zhujigao
 * @date 2013-11-12 下午4:01:18
 * @update developer zhujigao
 * @update date 2013-11-12 下午4:01:18
 * @version v1.0.0
 */
public class ResultModel {

	/**
	 * 响应结果集
	 */
	private Object result;

	/**
	 * 响应码
	 */
	private int code;

	private String desc;

	// /**
	// * API版本
	// */
	// private String version;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	// public String getVersion() {
	// return version;
	// }
	//
	// public void setVersion(String version) {
	// this.version = version;
	// }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
