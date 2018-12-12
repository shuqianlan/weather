package com.ilifesmart.javaprogect.nature_module_models;

/**
 * Created by hlkhjk_ok on 17/9/20.
 */

public class Io extends MOBase{

    private static final String TAG = "Io";
    protected Ep mEp;
    protected int mType;
    protected double mVal; //
    protected double mRv;
    protected String mIdx;
    protected String mVidx;
    protected String mMe;

    public Io() {
        setCgy("io");
    }

    public int getType() {
        return mType;
    }

    public Io setType(int type) {
        mType = type;
        return this;
    }

    public double getVal() {
        return mVal;
    }

    public Io setVal(double val) {
        mVal = val;
        return this;
    }

    public String getIdx() {
        return mIdx;
    }

    public Io setIdx(String idx) {
        mVidx = mIdx = idx;
        return this;
    }

    public String getMe() {
        return mMe;
    }

    public Io setMe(String me) {
        mMe = me;
        return this;
    }

    public double getRv() {
        return mRv;
    }

    public void setRv(double mRv) {
        this.mRv = mRv;
    }

    public synchronized void onChg(Object mo) {
        super.onChg(mo);
    }

    public String getVidx() {
        return mVidx;
    }

    public Io setVidx(String vidx) {
        mVidx = vidx;
        return this;
    }

    public Ep getEp() {
        return mEp;
    }

    public Io setEp(Ep mEp) {
        this.mEp = mEp;
        return this;
    }

    @Override
    public void update() {
        super.update();

    }

    public Double getDispValue() {
        if (mType == 0x1e) {
            return null;
        }

        return mRv;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(super.toString())
                .append(", type: ").append(mType)
                .append(", val: ").append(mVal)
                .append(", rv: ").append(mRv)
                .append(", idx: ").append(mIdx)
                .append(", vidx: ").append(mVidx)
                .toString();
    }
}
