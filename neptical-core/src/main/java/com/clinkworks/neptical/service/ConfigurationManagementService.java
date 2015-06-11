package com.clinkworks.neptical.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinkworks.neptical.spi.ConfigurationService;
import com.clinkworks.neptical.util.JsonUtil;
import com.google.gson.JsonElement;

public class ConfigurationManagementService implements ConfigurationService {

	private final Configuration configuration;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationManagementService.class);
	
	@Inject
	public ConfigurationManagementService(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public JsonElement createJsonModel(){
		return JsonUtil.createTemplateJsonFromNepticalConfiguration(this);
	}
	
	@Override
	public Properties getAsflattenedProperties(){
		
		final Properties flattenedProperties = new Properties();
		
		getConfiguration().getKeys().forEachRemaining((key) -> {
			flattenedProperties.setProperty(key, flattenProperty(key, configuration));
		});
		
		return flattenedProperties;
	}
	
	@Override
	public Configuration subset(String prefix) {
		return getConfiguration().subset(prefix);
	}

	@Override
	public boolean isEmpty() {
		return getConfiguration().isEmpty();
	}

	@Override
	public boolean containsKey(String key) {
		return getConfiguration().containsKey(key);
	}

	@Override
	public void addProperty(String key, Object value) {
		getConfiguration().addProperty(key, value);
	}

	@Override
	public void setProperty(String key, Object value) {
		getConfiguration().setProperty(key, value);
	}

	@Override
	public void clearProperty(String key) {
		getConfiguration().clearProperty(key);
	}

	@Override
	public void clear() {
		getConfiguration().clear();
	}

	@Override
	public Object getProperty(String key) {
		return getConfiguration().getProperty(key);
	}

	@Override
	public Iterator<String> getKeys(String prefix) {
		return getConfiguration().getKeys(prefix);
	}

	@Override
	public Iterator<String> getKeys() {
		return getConfiguration().getKeys();
	}

	@Override
	public Properties getProperties(String key) {
		return getConfiguration().getProperties(key);
	}

	@Override
	public boolean getBoolean(String key) {
		return getConfiguration().getBoolean(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return getConfiguration().getBoolean(key, defaultValue);
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return getConfiguration().getBoolean(key, defaultValue);
	}

	@Override
	public byte getByte(String key) {
		return getConfiguration().getByte(key);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		return getConfiguration().getByte(key, defaultValue);
	}

	@Override
	public Byte getByte(String key, Byte defaultValue) {
		return getConfiguration().getByte(key, defaultValue);
	}

	@Override
	public double getDouble(String key) {
		return getConfiguration().getDouble(key);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return getConfiguration().getDouble(key, defaultValue);
	}

	@Override
	public Double getDouble(String key, Double defaultValue) {
		return getConfiguration().getDouble(key, defaultValue);
	}

	@Override
	public float getFloat(String key) {
		return getConfiguration().getFloat(key);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return getConfiguration().getFloat(key, defaultValue);
	}

	@Override
	public Float getFloat(String key, Float defaultValue) {
		return getConfiguration().getFloat(key, defaultValue);
	}

	@Override
	public int getInt(String key) {
		return getConfiguration().getInt(key);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return getConfiguration().getInt(key, defaultValue);
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue) {
		return getConfiguration().getInteger(key, defaultValue);
	}

	@Override
	public long getLong(String key) {
		return getConfiguration().getLong(key);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return getConfiguration().getLong(key, defaultValue);
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		return getConfiguration().getLong(key, defaultValue);
	}

	@Override
	public short getShort(String key) {
		return getConfiguration().getShort(key);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		return getConfiguration().getShort(key, defaultValue);
	}

	@Override
	public Short getShort(String key, Short defaultValue) {
		return getConfiguration().getShort(key, defaultValue);
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		return getConfiguration().getBigDecimal(key);
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return getConfiguration().getBigDecimal(key, defaultValue);
	}

	@Override
	public BigInteger getBigInteger(String key) {
		return getConfiguration().getBigInteger(key);
	}

	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return getConfiguration().getBigInteger(key, defaultValue);
	}

	@Override
	public String getString(String key) {
		return getConfiguration().getString(key);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return getConfiguration().getString(key, defaultValue);
	}

	@Override
	public String[] getStringArray(String key) {
		return getConfiguration().getStringArray(key);
	}

	@Override
	public List<Object> getList(String key) {
		return getConfiguration().getList(key);
	}

	@Override
	public List<Object> getList(String key, List<Object> defaultValue) {
		return getConfiguration().getList(key, defaultValue);
	}



	Configuration getConfiguration(){
		return configuration;
	}
	
	public static final String flattenProperty(String key, Configuration configuration){
		
		String value = configuration.getString(key);
		Object actualValue = configuration.getProperty(key);
		String flattenedValue = null;
		
		boolean isArray = List.class.isAssignableFrom(actualValue.getClass());
		
		if(isArray){
			StringBuffer stringBuffer = new StringBuffer();
			String[] stringArray = configuration.getStringArray(key);
			for(int i = 0; i < stringArray.length; i++){
				stringBuffer.append(stringArray[i]);
				if(i + 1 < stringArray.length){
					stringBuffer.append(',');
				}
			}
			
			flattenedValue = stringBuffer.toString();
		}else{
			flattenedValue = value;
		}
		LOGGER.debug("Flattening property: " + actualValue + " resolved as " + flattenedValue);
		return flattenedValue;
	}

}
