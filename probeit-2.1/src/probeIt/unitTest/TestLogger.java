package probeIt.unitTest;

import probeIt.logging.Logger; 
import junit.framework.TestCase;

/**
 * @author GARZA
 *
 */
public class TestLogger extends TestCase {
	
	public TestLogger(String name){
		super(name);
	}
	
	public void testConstructor1(){
		Logger Log = new Logger();
		
		assertNotNull(Log);
		assertEquals(Log.getGlobalViewBool(), false);
		assertEquals(Log.getLocalViewBool(), false);
		assertEquals(Log.getSourceBool(), false);
		assertEquals(Log.getTrustBool(), false);
		assertEquals(Log.getViewerBool(), false);
	}
	
	public void testSetGlobalViewBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getGlobalViewBool(), false);
		
		Log.setGlobalViewBool(true);
		assertEquals(Log.getGlobalViewBool(), true);
	}
	
	public void testSetSourceBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getSourceBool(), false);
		
		Log.setSourceBool(true);
		assertEquals(Log.getSourceBool(), true);
	}
	
	public void testSetLocalViewBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getLocalViewBool(), false);
		
		Log.setLocalViewBool(true);
		assertEquals(Log.getLocalViewBool(), true);
	}
	
	public void testSetTrustBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getTrustBool(), false);
		
		Log.setTrustBool(true);
		assertEquals(Log.getTrustBool(), true);
	}
	
	public void testSetViewerBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getViewerBool(), false);
		
		Log.setViewerBool(true);
		assertEquals(Log.getViewerBool(), true);
	}
	
	public void testGetSourceBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getSourceBool(), false);
	}
	
	public void testGetTrustBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getTrustBool(), false);
	}
	
	public void testGetViewerBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getViewerBool(), false);
	}
	
	public void testGetGlobaViewBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getGlobalViewBool(), false);
	}
	
	public void testGetLocalViewBool(){
		Logger Log = new Logger();
		assertNotNull(Log);
		assertEquals(Log.getLocalViewBool(), false);
	}
	
	public static void main(String[] args) {
        junit.textui.TestRunner.run(
            TestLogger.class);
    }

}
