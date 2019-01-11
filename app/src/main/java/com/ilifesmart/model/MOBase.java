package com.ilifesmart.model;

import com.ilifesmart.listener.INotiListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MOBase {

	private List<INotiListener> registers = Collections.synchronizedList(new ArrayList<>());

	public void registerListener(INotiListener listener) {
		if (listener != null) {
			if (!registers.contains(listener)) {
				registers.add(listener);
			}
		}
	}

	public void unRegisterListener(INotiListener listener) {
		if (listener != null) {
			if (registers.contains(listener)) {
				registers.remove(listener);
			}
		}
	}

	public int getListenersSize() {
		return registers.size();
	}
}
