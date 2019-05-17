package com.ilifesmart.region;

import java.util.HashMap;
import java.util.Map;

public class RegionItem {

	/*
	* name:   当前名称
	* code:   当前区域代码
	* parent: 上级区域代码
	* index:  层级深度(0:省级,1:市级,2:县/区级)
	* */

	private final String name;
	private final String code;
	private final String parent;
	private final int index;

	/* 文档中无效的UI显示名称,允许层级显示，最终结果置为"" */
	private static final Map<String,String> map = new HashMap<>();

	static {
		map.put("市辖区", "");
		map.put("县", "");
	}

	private RegionItem(Builder builder) {
		this.name = builder.name;
		this.code = builder.code;
		this.parent = builder.parent;
		this.index = builder.index;
	}

	public String getName() {
		return name;
	}

	public String getDispName() {
		String result = map.get(name);
		return (result == null) ? name : result;
	}

	public String getCode() {
		return code;
	}

	public String getParent() {
		return parent;
	}

	public int getIndex() {
		return index;
	}

	public static class Builder {
		private String name;
		private String code;
		private String parent;
		private int index;

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public Builder parent(String category) {
			this.parent = category;
			return this;
		}

		public Builder index(int index) {
			this.index = index;
			return this;
		}

		public RegionItem build() {
			return new RegionItem(this);
		}
	}

	@Override
	public String toString() {
		return new StringBuilder()
						.append("name: ").append(name)
						.append(",code: ").append(code)
						.append(",parent: ").append(parent)
						.toString();
	}
}
