package com.ilifesmart.region;

public class RegionItem {

	private final String name;
	private final String code;
	private final String parent;
	private final int index;

	private RegionItem(Builder builder) {
		this.name = builder.name;
		this.code = builder.code;
		this.parent = builder.parent;
		this.index = builder.index;
	}

	public String getName() {
		return name;
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
