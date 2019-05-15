package com.ilifesmart.file;

public class RegionItem {

	private final String name;
	private final String code;
	private final String category;

	private RegionItem(Builder builder) {
		this.name = builder.name;
		this.code = builder.code;
		this.category = builder.category;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getCategory() {
		return category;
	}

	public static class Builder {
		private String name;
		private String code;
		private String category;

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public Builder category(String category) {
			this.category = category;
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
						.append(",category: ").append(category)
						.toString();
	}
}
