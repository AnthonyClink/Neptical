package com.clinkworks.neptical.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.clinkworks.neptical.spi.ConfigurationService;

public class SettingsManagementServiceUnitTest {

	
	private Configuration configuration;
	
	private ConfigurationService configurationService;
	
	@Before
	public void setupSettingsService(){
		configuration = new PropertiesConfiguration();
		configurationService = new ConfigurationManagementService(configuration);
	}
	

	@Test
	public void ensureConfigurationServiceCanExpandProperties(){		
		configurationService.addProperty("nspace.runtime", "user,app");
		assertEquals("app", configuration.getStringArray("nspace.runtime")[1]);
	}
	
	@Test
	public void ensureConfigurationServiceCanFlattenProperties(){
		configurationService.addProperty("nspace.runtime", "user");
		configurationService.addProperty("nspace.runtime", "app");
		assertEquals("user,app", configurationService.getAsflattenedProperties().getProperty("nspace.runtime"));

	}
	
	@Test
	public void ensureConfigurationServiceProperlyExpandsAndFlattensIntegers(){
		configurationService.setProperty("number", 1);
		assertEquals(1, configurationService.getInt("number"));
		configurationService.setProperty("number", "1.5");
		assertEquals("1.5", configurationService.getAsflattenedProperties().getProperty("number"));
		assertEquals(new BigDecimal(1.5), configurationService.getBigDecimal("number"));
	}
	
	@Test
	public void ensureConfigurationServiceProperlyConvertsConfigurationToJsonModel(){
		
	}
	
}
