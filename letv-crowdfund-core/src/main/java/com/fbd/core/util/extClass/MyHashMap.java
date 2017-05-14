package com.fbd.core.util.extClass;

import java.util.HashMap;

public class MyHashMap extends HashMap<String, Object> {
	/**
     * 
     */
    private static final long serialVersionUID = 1396002203131406288L;

    @Override
	public Object put(String key, Object value) {
		key = key.toLowerCase();
		return super.put(key, value);
	}

}
