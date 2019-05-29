package com.surfaceview;

public class SnowBean {
	private final int speedX;
	private final int speedY;
	private final int x;
	private final int y;
	private final float scale;

	private SnowBean(SnowBeanBuilder builder) {
		this.speedX = builder.speedX;
		this.speedY = builder.speedY;
		this.x = builder.x;
		this.y = builder.y;
		this.scale = builder.scale;
	}

	public class SnowBeanBuilder {
		private int speedX;
		private int speedY;
		private int x;
		private int y;
		private float scale;

		public SnowBeanBuilder speedX(int speedX) {
			this.speedX = speedX;
			return this;
		}

		public SnowBeanBuilder speedY(int speedY) {
			this.speedY = speedY;
			return this;
		}

		public SnowBeanBuilder x(int x) {
			this.x = x;
			return this;
		}

		public SnowBeanBuilder y(int y) {
			this.y = y;
			return this;
		}

		public SnowBeanBuilder speedX(float scale) {
			this.scale = scale;
			return this;
		}

		public SnowBean build() {
			return new SnowBean(this);
		}
	}

	@Override
	public String toString() {
		return "SnowBean{" +
						"speedX=" + speedX +
						", speedY=" + speedY +
						", x=" + x +
						", y=" + y +
						", scale=" + scale +
						'}';
	}
}
