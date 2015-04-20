package com.clinkworks.neptical.junit.runners;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.clinkworks.neptical.util.Common.noOp;




//import com.clinkworks.neptical.NepticalData;
//import com.clinkworks.neptical.data.api.DataLoader.TestData;
import com.clinkworks.neptical.api.NepticalContext;
import com.clinkworks.neptical.test.modules.NepticalJUnitTestModules;
import com.clinkworks.neptical.test.modules.NepticalJUnitTestModules.BindingAnnotationForIntegerConfig;
import com.clinkworks.neptical.test.modules.NepticalJUnitTestModules.BoundIntegerModule;
import com.clinkworks.neptical.test.modules.NepticalJUnitTestModules.IntegerModule;
import com.clinkworks.neptical.test.modules.NepticalJUnitTestModules.NamedModule;
import com.clinkworks.neptical.test.modules.NepticalJUnitTestModules.StringModule;
import com.google.inject.Inject;
import com.google.inject.name.Named;

@RunWith(NepticalJUnit4Runner.class)
@NepticalContext({StringModule.class, NamedModule.class, BoundIntegerModule.class})
public class NepticalJUnit4IntegrationTest {
	
	boolean beforeMethodWasCalled = false;
	
//	@TestData("users.random-account.account")
//	private NepticalData testData;
	
	@Before
	public void before(String stringBeforeInjection){
		beforeMethodWasCalled = true;
		assertEquals(NepticalJUnitTestModules.DEFAULT_STRING, stringBeforeInjection);
	}
	
	@After
	public void afterMethodCalled(String stringBeforeInjection){
		beforeMethodWasCalled = true;
		assertEquals(NepticalJUnitTestModules.DEFAULT_STRING, stringBeforeInjection);
	}
	
	@After
	public void afterMethodWithoutParams(){
		noOp();
	}
	
	@BindingAnnotationForIntegerConfig
	@Inject
	private Integer leetInteger;
	
	@Test
	public void integrationTestExpectingTheBeforeMethodTohaveRunSuccessfully(){
		assertTrue(beforeMethodWasCalled);
	}
	
	@Test
	public void integrationTestExpectingTestContextLevelInjectionToSucceed(){
		assertNotNull(leetInteger);
		assertEquals(NepticalJUnitTestModules.LEET_INTEGER, leetInteger);
	}
	
//	@Test
//	public void integrationTestExpectingTestContextToInjectProperTestData(){
//		assertEquals("{{random-email}}", testData.find("email").getAsString());
//	}
//	
//	@Test
//	@Ignore
//	@TestData("contacts.addresses.genericAddress")
//	public void integrationTestExpectingTestContextToInjectProperTestDataInLaterContext(){
//		assertEquals("1234 my place drive", testData.find("AddressLine1"));
//	}
	
	@Test
	public void integrationTestExpectingTestLevelInjectionToSucceed(@BindingAnnotationForIntegerConfig Integer boundInteger){
		assertNotNull(boundInteger);
		assertEquals(NepticalJUnitTestModules.LEET_INTEGER, boundInteger);
	}
	
	@Test
	public void integrationTestMissingIntegerWithNamedAnnotationConfigurationExpectingNullInteger(Integer nullInteger, String stringA, String stringB, @Named("String1") String string1, @Named("String2") String string2){
		assertTrue(StringUtils.isNotBlank(string1));
		assertEquals(NepticalJUnitTestModules.DEFAULT_STRING, stringA);
		assertEquals(stringA, stringB);
		assertEquals(NepticalJUnitTestModules.STRING_ONE, string1);
		assertEquals(NepticalJUnitTestModules.STRING_TWO, string2);
		assertNull(nullInteger);
	}
	
	@Test
	@NepticalContext(IntegerModule.class)
	public void integeationConfigTestForTestLevelConfiguration(Integer integer){
		assertEquals(NepticalJUnitTestModules.DEFAULT_INTEGER, integer);
	}
	
}