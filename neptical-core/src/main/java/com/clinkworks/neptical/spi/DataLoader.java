package com.clinkworks.neptical.spi;

import com.clinkworks.neptical.datatype.NepticalData;

public interface DataLoader {
	public NepticalData loadData(LoaderCriteria loaderCriteria);
}
