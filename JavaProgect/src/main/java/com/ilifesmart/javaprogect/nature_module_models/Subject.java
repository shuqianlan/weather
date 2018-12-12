package com.ilifesmart.javaprogect.nature_module_models;

/**
 * Created by hlkhjk_ok on 17/9/26.
 */

public interface Subject {
    void registerObserver(Observer observer);
    void unRegisterObserver(Observer observer);
    void notifyObservers();
}
