package com.clinkworks.neptical.spi;

import java.util.List;

public class LoaderCriteria implements LoaderCriterian{
	
	private final List<LoaderCriterian> loaderCriteria;
	
	public LoaderCriteria(List<LoaderCriterian> loaderCritera){
		this.loaderCriteria = loaderCritera;
	}

	@Override
	public boolean canLoad(LoaderCriteria loaderCriteria) {
		
		for(LoaderCriterian criterian : getCriteria()){
			if(!criterian.canLoad(loaderCriteria)){
				return false;
			}
		}
		
		return true;	
	}
	
	public List<LoaderCriterian> getCriteria(){
		return loaderCriteria;
	}
	
}
