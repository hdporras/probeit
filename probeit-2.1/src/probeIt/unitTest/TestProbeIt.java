package probeIt.unitTest;

import javax.swing.*;
import java.awt.*;
import junit.framework.TestCase;
import probeIt.ProbeIt;


/**
 * @author ANTONIO GARZA
 *
 */
public class TestProbeIt extends TestCase{

	public TestProbeIt(String name){
		super(name);
	}

	public TestProbeIt(){}

	/**
	 * Testing the constructor ProbeIt()
	 */
	public void testConstructor1(){
		TestProbeIt TPI = new TestProbeIt();
		TestProbeIt.PIsubClass TPISC = TPI.new PIsubClass();
		TPISC.ProbeIt();

		assertNotNull(TPISC);
		assertNotNull(TPISC.getLogger());
		assertEquals(TPISC.isLoaded(), true);
		assertEquals(TPISC.isRemote(), false);
	}

	/**
	 * Testing the constructor ProbeIt(Container, JApplet)
	 */
	public void testConstructor2(){
		ProbeIt PI = new ProbeIt(new Container(), new JApplet());

		assertNotNull(PI);
		assertNotNull(PI.getLogger());
		assertEquals(PI.isLoaded(), true);
		assertEquals(PI.isRemote(), false);
	}

	/**
	 * Testing the constructor ProbeIt(JFrame)
	 */
	public void testConstructor3(){
		ProbeIt PI = new ProbeIt(new JFrame());

		assertNotNull(PI);
		assertNotNull(PI.getLogger());
		assertEquals(PI.isLoaded(), true);
		assertEquals(PI.isRemote(), false);
	}

	/**
	 * Testing the constructor ProbeIt(JFrame, ProbeItViewsConfiguration)
	 */
	public void testConstructor4(){
		
		/*
		 * Not Tested due to the scope of the ProbeItViewsConfiguration constructor not being public.
		 */
		
//		ProbeIt PI = new ProbeIt(new JFrame(), new ProbeItViewsConfiguration());
//
//		assertNotNull(PI);
//		assertNotNull(PI.getLogger());
//		assertEquals(PI.isLoaded(), true);
//		assertEquals(PI.isRemote(), false);
	}

	public void testSetRemote(){
		TestProbeIt TPI = new TestProbeIt();
		TestProbeIt.PIsubClass TPISC = TPI.new PIsubClass();
		TPISC.ProbeIt();

		assertEquals(TPISC.isRemote(), false);

		TPISC.setRemote();
		assertEquals(TPISC.isRemote(), true);
	}

	public void testGetInstance(){
		assertNotNull(ProbeIt.getInstance());
		
		TestProbeIt TPI = new TestProbeIt();
		TestProbeIt.PIsubClass TPISC = TPI.new PIsubClass();
		TPISC.ProbeIt();
		TPISC.setRemote();
		
		assertNotNull(TPISC.getInstance());
		assertEquals(TPISC.isRemote(), true);
	}

	public void testFindParentFrame(){
		/*
		 * Not yet tested due to the scope of the method not being public.
		 */
	}

	public void testOpenFullProbeIt(){
		TestProbeIt TPI = new TestProbeIt();
		TestProbeIt.PIsubClass TPISC = TPI.new PIsubClass();
		TPISC.ProbeIt();
		
		TPISC.openFullProbeIt();
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(
				TestProbeIt.class);
	}

	class PIsubClass extends ProbeIt{
		public void ProbeIt(){

		}
	}

}
