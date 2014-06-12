package com.ilyl01.weather;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.expect;

/**
 * Unit test for simple App.
 */
//@RunWith(JUnit4.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(ServiceHelper.class)
public class AppTest
{
	private final JSONObject json = new JSONObject("{\"testDouble1\":0.92,\"testDouble2\":0.08,\"testString\":\"testStr\"}");
	
	@Before
	public void setupMock() throws Exception{
		PowerMock.mockStatic(ServiceHelper.class);
		expect(ServiceHelper.generateServiceURL("UnknownLocation")).andReturn("unknown.location").anyTimes();
		expect(ServiceHelper.generateServiceURL("KnownLocation")).andReturn("known.location").anyTimes();
		expect(ServiceHelper.fetchJSONString("unknown.location")).andReturn("{\"message\":\"Error: Not found city\",\"cod\":\"404\"}").anyTimes();
		expect(ServiceHelper.fetchJSONString("known.location")).andReturn("{\"coord\":{\"lon\":21.63,\"lat\":47.53},\"sys\":{\"message\":0.0635,\"country\":\"HU\",\"sunrise\":1402367756,\"sunset\":1402425003},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01n\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":27,\"pressure\":1017,\"humidity\":47,\"temp_min\":27,\"temp_max\":27},\"wind\":{\"speed\":7.7,\"deg\":350,\"gust\":14.4},\"clouds\":{\"all\":0},\"dt\":1402425900,\"id\":721472,\"name\":\"Debrecen\",\"cod\":200}").anyTimes();
		expect(ServiceHelper.fetchJSONString("WrongURL")).andThrow(new MalformedURLException()).anyTimes();
		PowerMock.replay(ServiceHelper.class);
	}
	
    @Test
    public void testAppInstance(){
    	App app = new App();
    	assertNotNull(app);
    }
    
    @Test
    public void testGenerateServiceURL(){
    	Assert.assertEquals(ServiceHelper.generateServiceURL("KnownLocation"),"known.location");
    }
    
    @Test(expected = MalformedURLException.class)
    public void testFetchJSONStringWithMalformedURL() throws IOException{
    	ServiceHelper.fetchJSONString("WrongURL");
    }

    @Test
    public void testFetchJSONStringWorkingURL() throws IOException{
    	String s = ServiceHelper.fetchJSONString("known.location");
    	assertTrue(s.length()>60);
    }

    @Test
    public void testAppendDouble(){
    	StringBuilder s = new StringBuilder();
    	App.appendDouble(s, json, "TestDouble1", "testDouble1", "cm");
    	assertEquals(s.toString(), "TestDouble1: 0.92cm"+System.getProperty("line.separator"));
    	App.appendDouble(s, json, "TestDouble2", "testDouble2", "km");
    	assertEquals(s.toString(), "TestDouble1: 0.92cm"+System.getProperty("line.separator")+"TestDouble2: 0.08km"+System.getProperty("line.separator"));
    }
    
    @Test
    public void testAppendString(){
    	StringBuilder s = new StringBuilder();
    	App.appendString(s, json, "TestString", "testString");
    	assertEquals(s.toString(), "TestString: testStr"+System.getProperty("line.separator"));
    }
    
    @Test
    public void testGenerateOutputWithUnknownLocation() throws Exception{
    	String s = App.generateOutput("UnknownLocation");
    	assertEquals(s, "Error: Not found city");
    }
    
    @Test
    public void testMainWithUnknownLocation() throws Exception{
    	String[] args = new String[1];
    	args[0]="UnknownLocation";
    	App.main(args);
    }

    @Test
    public void testMainWithKnownLocation(){
    	String[] args = new String[1];
    	args[0]="KnownLocation";
    	App.main(args);
    }

    @Test
    public void testMainWithoutLocation(){
    	String[] args = new String[0];
    	App.main(args);
    }

}

