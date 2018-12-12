package com.ilifesmart.javaprogect.entry;

import static java.lang.Thread.sleep;

public class MOBaseThreadSafe {
    protected String mName;
    protected String mCgy;
    protected String mCls;
    protected String mId;
    protected String mNid;
    protected String mAgtId;

    public synchronized String getAgtId() {
        return mAgtId;
    }

    public synchronized MOBaseThreadSafe setAgtId(String agtId) {
        mAgtId = agtId;
        return this;
    }

    public synchronized String getName() {
        return mName;
    }

    // 针对类对象施加同步锁.
    public synchronized MOBaseThreadSafe setName(String mName) {
        this.mName = mName;
        try {
            sleep(2000);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
        System.out.println("mName =-");
        return this;
    }

    public synchronized String getCgy() {
        return mCgy;
    }

    public synchronized MOBaseThreadSafe setCgy(String mCgy) {
        this.mCgy = mCgy;
        return this;
    }

    public synchronized String getCls() {
        return mCls;
    }

    public synchronized MOBaseThreadSafe setCls(String mCls) {
        this.mCls = mCls;
        return this;
    }

    public synchronized String getId() {
        return mId;
    }

    public synchronized MOBaseThreadSafe setmId(String mId) {
        this.mId = mId;
        return this;
    }

    public synchronized String getNid() {
        return mNid;
    }

    public synchronized MOBaseThreadSafe setNid(String mNid) {
        this.mNid = mNid;
        return this;
    }

    // 子类重载就是锁重入机制咯.
    public synchronized void OnChg(MOBaseThreadSafe chg) {
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

    public void commitNewValues() {

    }

    // 类本身的锁机制.
    public synchronized static void isSuitAble(MOBaseThreadSafe mo) {
        System.out.println("mName ----- " + mo.getName());
        try {
            sleep(2000);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

    }
}
