package com.ilifesmart.javaprogect.nature_module_models;


/**
 * Created by hlkhjk_ok on 17/10/20.
 */

public interface Observer {
    void onDataChanged(MOBase obj);
    void onMODestroy();
}
