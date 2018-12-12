package com.ilifesmart.javaprogect.nature_module_models;

/**
 * Created by hlkhjk_ok on 17/9/20.
 */

public class Ep extends MOBase {

    protected String mNid;
    protected String mAgt;
    protected String mDevType;

    public Ep() {
        setCgy("ep");
    }

    public String getNid() {
        return mNid;
    }

    public Ep setNid(String nid) {
        mNid = nid;
        return this;
    }

    public String getAgt() {
        return mAgt;
    }

    public Ep setAgt(String agt) {
        mAgt = agt;
        return this;
    }

    @Override
    public String toString() {
        System.out.println("EP.toString()");
        return new StringBuilder()
                .append(super.toString())
                .append(", agt: ").append(mAgt)
                .append(", devType: ").append(mDevType)
                .append(", nid: ").append(mNid)
                .append(super.toString())
                .toString();
    }

    @Override
    public void update() {
        super.update();
    }

    public String getDevType() {
        return mDevType;
    }

    public Ep setDevType(String devType) {
        mDevType = devType;
        return this;
    }
}
