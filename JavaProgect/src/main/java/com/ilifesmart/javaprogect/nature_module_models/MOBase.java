package com.ilifesmart.javaprogect.nature_module_models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by dusj on 2017/9/15.
 */

public class MOBase implements Subject {
    private static final long serialVersionUID = 8829975621220483374L;
    private static final String TAG = "MOBase";
    protected String mName;
    protected String mCgy;
    protected String mCls;
    protected String mId;
    protected String mShortId;
    protected String mAgt;
    protected boolean mOnline;
    protected int mWeight;
    protected List<Observer> mObservers;

    public MOBase() {
        mObservers = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public MOBase setName(String name) {
        mName = name;
        return this;
    }

    public String getId() {
        return mId;
    }

    public MOBase setId(String id) {
        mId = id;

        Matcher m = Pattern.compile(".*\\/([^\\/]+)$").matcher(id);
        if (m.find() && m.groupCount() == 1) {
            mShortId = m.group(1);
        }
        return this;
    }

    public String getCgy() {
        return mCgy;
    }

    public MOBase setCgy(String cgy) {
        mCgy = cgy;
        return this;
    }

    public String getCls() {
        return mCls;
    }

    public MOBase setCls(String cls) {
        mCls = cls;
        return this;
    }

    public String getAgt() {
        return mAgt;
    }

    public MOBase setAgt(String mAgt) {
        this.mAgt = mAgt;
        return this;
    }

    public String getShortId() {
        return mShortId;
    }

    public MOBase setShortId(String mShortId) {
        this.mShortId = mShortId;
        return this;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public MOBase setOnline(boolean mStatus) {
        this.mOnline = mStatus;
        return this;
    }

    public void update() {
        // impl initial
    }

    public synchronized void onChg(Object mo) {
        if (mo == null) {
            return;
        }

        update();
        notifyObservers();
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer == null) {
            return;
        }

        synchronized (mObservers) {
            if (!mObservers.contains(observer))
                mObservers.add(observer);
        }
    }

    public synchronized void onDestroy() {
        if (mObservers != null) {
            Iterator<Observer> iterator = mObservers.iterator();
            while (iterator.hasNext()) {
                Observer observer = iterator.next();
                iterator.remove();
                mObservers.remove(observer);
                observer.onMODestroy();
            }
        }
    }

    @Override
    public void unRegisterObserver(Observer observer) {
        if (null == observer) {
            return;
        }
        int index = mObservers.indexOf(observer);
        if (index >= 0) {
            synchronized (mObservers) {
                if (mObservers.contains(observer))
                    mObservers.remove(index);
            }
        }
    }

    @Override
    public void notifyObservers() {
        for(Observer observer:mObservers) {
            observer.onDataChanged(this);
        }
    }

    @Override
    public String toString() {
        System.out.println("############### ");
        return new StringBuilder()
                .append("id: ").append(mId)
                .append(", name: ").append(mName)
                .append(", cgy: ").append(mCgy)
                .append(", cls: ").append(mCls)
                .toString();
    }

}
