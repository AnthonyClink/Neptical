package com.clinkworks.neptical.datatype;

public interface NepticalData{
	
	public String getName();
	public Object get();
	public Class<? extends NepticalData> getNepticalDataType();
	
}
