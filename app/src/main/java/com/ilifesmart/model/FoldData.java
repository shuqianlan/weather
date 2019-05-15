package com.ilifesmart.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoldData<T> {
	private final String title;
	private final String disp;
	private final boolean isSelected;
	private final T element;
	private final List<FoldData> mods;

	/*
	* String: key：T.uuid(唯一匹配)
	* T:父容器下的子数据
	*
	* */

	private FoldData(FoldBuilder<T> builder) {
		this.title = builder.title;
		this.disp  = builder.disp;
		this.isSelected = builder.isSelected;
		this.element = builder.element;
		this.mods = builder.mods;
	}

	public String getTitle() {
		return title;
	}

	public String getDisp() {
		return disp;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public T getElement() {
		return element;
	}

	public List<FoldData> getMods() {
		return mods;
	}

	public static class FoldBuilder<T> {
		private String title = "Item-0";
		private String disp = ":伊甸园 :地狱";
		private boolean isSelected;
		private T element;
		private List<FoldData> mods = new ArrayList<>();

		public FoldBuilder title(String title) {
			this.title = title;
			return this;
		}

		public FoldBuilder disp(String disp) {
			this.disp = disp;
			return this;
		}

		public FoldBuilder selected(boolean selected) {
			isSelected = selected;
			return this;
		}

		public FoldBuilder mods(List<FoldData> mods) {
			this.mods = mods;
			return this;
		}

		public FoldBuilder element(T element) {
			this.element = element;
			return this;
		}

		public FoldData build() {
			return new FoldData(this);
		}
	}

	@Override
	public String toString() {
		return new StringBuilder()
						.append("title:").append(title)
						.append(",disp:").append(disp)
						.append(",isSelected:").append(isSelected)
						.append(",moids:").append(Arrays.toString(mods.toArray()))
						.toString();
	}
}
