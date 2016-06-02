/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * Copyright 2006-2011 DataComo Communications Technology INC.
 * 
 * This source file is a part of MC_Spider_Android_Client_V1.0 project. 
 * date: Jun 30, 2011
 *
 */
package com.yikeguan.boardgamestore.util;

import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 */
public class AppSharedPreferences {

	private Context context;

	public AppSharedPreferences(Context applicationContext) {
		this.context = applicationContext;
	}

	/**
	 * 保存数据 fileName 文件名 key 键 value 值
	 */
	@SuppressWarnings("static-access")
	public void saveStringMessage(String fileName, String key, String value) {
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(key, value);
		// 执行commit方法后，edit中设置的内容才真正写入文件中
		edit.commit();
	}

	/**
	 * 获取数据 fileName 文件名 key 键
	 */
	@SuppressLint("WorldReadableFiles")
	public String getStringMessage(String fileName, String key, String value) {
		@SuppressWarnings("deprecation")
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, Context.MODE_WORLD_READABLE);
		String setup = preferences.getString(key, value);
		return setup;
	}

	@SuppressWarnings("static-access")
	public void saveIntMessage(String fileName, String key, int value) {
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putInt(key, value);
		// 执行commit方法后，edit中设置的内容才真正写入文件中
		edit.commit();
	}

	@SuppressLint("WorldReadableFiles")
	public int getIntMessage(String fileName, String key, int defaltValve) {
		@SuppressWarnings("deprecation")
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, Context.MODE_WORLD_READABLE);
		int setup = preferences.getInt(key, defaltValve);
		return setup;
	}

	/**
	 * 保存数据 fileName 文件名 key 键 value 值
	 */
	@SuppressWarnings("static-access")
	public void saveBooleanMessage(String fileName, String key, boolean value) {
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**
	 * 获取数据 fileName 文件名 key 键
	 */
	@SuppressLint("WorldReadableFiles")
	public boolean getBooleanMessage(String fileName, String key,
			boolean defaltValve) {
		@SuppressWarnings("deprecation")
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, Context.MODE_WORLD_READABLE);
		boolean setup = preferences.getBoolean(key, defaltValve);
		return setup;
	}

	@SuppressWarnings("static-access")
	public void saveLongMessage(String fileName, String key, long value) {
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putLong(key, value);
		edit.commit();
	}

	@SuppressLint("WorldReadableFiles")
	public long getLongMessage(String fileName, String key, long defaltValve) {
		@SuppressWarnings("deprecation")
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, Context.MODE_WORLD_READABLE);
		long setup = preferences.getLong(key, defaltValve);
		return setup;
	}

	/**
	 * 删除数据 fileName 文件名 key 键 value 值
	 */
	@SuppressWarnings("static-access")
	public void deleteMessage(String fileName, String key) {
		SharedPreferences preferences = this.context.getSharedPreferences(
				fileName, context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.remove(key);
		// 执行commit方法后，edit中设置的内容才真正写入文件中
		edit.commit();
	}

	/**
	 * 保存到sharedpreference里
	 * 
	 * @param name
	 * @param age
	 * @throws Exception
	 */
	public void save(String name, int type) throws Exception {
		// getSharedPreferences第一个参数是文件名，但不需要加后缀.xml，系统默认就是xml格式
		SharedPreferences preferences = context.getSharedPreferences("search",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit(); // 一定要获得编辑器
		editor.putInt(name, type);
		editor.commit(); // 提交修改
	}

	/**
	 * 从以保存的xml文件中读取
	 * 
	 * @return
	 */
	public Map<String, Integer> getPreferences() {
		SharedPreferences preference = context.getSharedPreferences("search",
				Context.MODE_PRIVATE);
		return (Map<String, Integer>) preference.getAll();
	}
}
