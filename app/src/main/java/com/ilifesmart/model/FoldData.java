package com.ilifesmart.model;

import java.util.ArrayList;
import java.util.List;

public class FoldData<T> {
	private String title = "Item-0";
	private String disp = ":伊甸园 :地狱";
	private boolean isSelected;
	private List<FoldData<T>> mods = new ArrayList<>();


	/*
	* String: key：T.uuid(唯一匹配)
	* T:父容器下的子数据
	*
	* */

	public FoldData() {

	}

	public FoldData(String title, String disp, boolean isSelected) {
		setTitle(title).setDisp(disp).setSelected(isSelected);
	}

	public String getTitle() {
		return title;
	}

	public FoldData setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDisp() {
		return disp;
	}

	public FoldData setDisp(String disp) {
		this.disp = disp;
		return this;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public FoldData setSelected(boolean selected) {
		isSelected = selected;
		return this;
	}

	public List<FoldData<T>> getMods() {
		return mods;
	}

	public FoldData<T> setMods(List<FoldData<T>> mods) {
		this.mods = mods;
		return this;
	}

}
