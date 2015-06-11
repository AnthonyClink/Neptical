package com.clinkworks.neptical.spi;

import java.util.Properties;

import org.apache.commons.configuration.Configuration;

import com.google.gson.JsonElement;

public interface ConfigurationService extends Configuration{
	public Properties getAsflattenedProperties();
	public JsonElement createJsonModel();
}
