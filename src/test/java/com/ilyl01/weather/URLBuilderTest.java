package com.ilyl01.weather;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Unit test for class URLBuilder.
 */
@RunWith(JUnit4.class)
public class URLBuilderTest{
	URLBuilder urlBuilder;
	
	@Before
	public void setup(){
    	urlBuilder = new URLBuilder("http://test.com/");
    }

    @Test
    public void testWithNameAndValue() {
    	assertEquals(urlBuilder.addQueryParam("testParam1","testValue1").getUrl(), "http://test.com/?testParam1=testValue1");
        assertEquals(urlBuilder.addQueryParam("testParam2","testValue2").getUrl(), "http://test.com/?testParam1=testValue1&testParam2=testValue2");
    }
    
    @Test
    public void testWithName(){
    	assertEquals(urlBuilder.addQueryParam("testParam1").getUrl(), "http://test.com/?testParam1");
        assertEquals(urlBuilder.addQueryParam("testParam2").getUrl(), "http://test.com/?testParam1&testParam2");
    }
    
    @Test
    public void testMixed() {
    	assertEquals(urlBuilder.addQueryParam("testParam1").getUrl(), "http://test.com/?testParam1");
        assertEquals(urlBuilder.addQueryParam("testParam2","testValue2").getUrl(), "http://test.com/?testParam1&testParam2=testValue2");
    }
    
    @Test
    public void testEmpty() {
    	assertEquals(urlBuilder.addQueryParam("").getUrl(), "http://test.com/");
    	assertEquals(urlBuilder.addQueryParam(null).getUrl(), "http://test.com/");
    }
    
}

