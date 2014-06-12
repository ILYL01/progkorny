package com.ilyl01.weather;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.URLDataSource;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
/**
 * Unit test for ServiceHelper.
 */
@RunWith(JUnit4.class)
public class ServiceHelperTest
{
	private URLDataSource uds;
	private int udsIndex;
	private static int udsArray[] = new int[5];

	@BeforeClass
	public static void setupSpecMock(){
		udsArray[0]=65;
		udsArray[1]=66;
		udsArray[2]=67;
		udsArray[3]=-1;
		udsArray[4]=-1;
	}
	
	@Before
	public void setupMock() throws Exception{
		uds = createNiceMock(URLDataSource.class);
		expect(uds.getInputStream()).andReturn(new InputStream() {
			@Override
			public int read() throws IOException {
				return udsArray[udsIndex++];
			}
		}).anyTimes();
		replay(uds);
		
		udsIndex=0;
	}

	@Test
	public void testGenerateServiceURL(){
		assertEquals("http://api.openweathermap.org/data/2.5/weather?q=TestLocation&mode=json&units=metric", ServiceHelper.generateServiceURL("TestLocation"));
	}
	
	@Test
	public void testFetchJSONString() throws IOException{
		assertEquals("ABC",ServiceHelper.fetchJSONString(uds));
	}

}

