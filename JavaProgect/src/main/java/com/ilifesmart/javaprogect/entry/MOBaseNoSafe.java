package com.ilifesmart.javaprogect.entry;

import static java.lang.Thread.sleep;

public class MOBaseNoSafe {
	protected String mName;
	protected String mCgy;
	protected String mCls;
	protected String mId;
	protected String mNid;

	public String getAgtId() {
		return mAgtId;
	}

	public void setAgtId(String agtId) {
		mAgtId = agtId;
	}

	protected String mAgtId;

	public String getName() {
		return mName;
	}

	// 针对类对象施加同步锁.
	public MOBaseNoSafe  setName(String mName) {
		this.mName = mName;
		try {
			sleep(2000);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("mName =-");
		return this;
	}

	public String getCgy() {
		return mCgy;
	}

	public MOBaseNoSafe  setCgy(String mCgy) {
		this.mCgy = mCgy;
		return this;
	}

	public String getCls() {
		return mCls;
	}

	public MOBaseNoSafe  setCls(String mCls) {
		this.mCls = mCls;
		return this;
	}

	public String getId() {
		return mId;
	}

	public MOBaseNoSafe  setmId(String mId) {
		this.mId = mId;
		return this;
	}

	public String getNid() {
		return mNid;
	}

	public MOBaseNoSafe  setNid(String mNid) {
		this.mNid = mNid;
		return this;
	}

	// 子类重载就是锁重入机制咯.
	public void OnChg(MOBaseNoSafe  chg) {
		System.out.print("----------------- OnChg");
	}

	@Override
	public String toString() {
		return new StringBuilder().append("{")
						.append("name:").append(mName)
						.append(",cgy:").append(mCgy)
						.append(",cls:").append(mCls)
						.append(",id:").append(mId)
						.append(",nid:").append(mNid)
						.append("}").toString();
	}

	// 类本身的锁机制.
	public static void isSuitAble(MOBaseNoSafe  mo) {
		System.out.println("mName ----- " + mo.getName());
		try {
			sleep(2000);
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
}
