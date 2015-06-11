package com.clinkworks.neptical.datatype;

public interface NepticalData {
	public Object get();
	public Class<? extends NepticalData> getNepticalDataType();
}
