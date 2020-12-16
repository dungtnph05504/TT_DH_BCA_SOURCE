package com.nec.asia.applet;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for demo applet.
 * 
 * @author bright_zheng
 *
 */
public class DemoAppletTest{
	private AppletViewer viewer;
	private FrameFixture applet;

	@Before
	public void setUp() {
		// get the viewer using AppletLauncher or create a new AppletViewer
		//viewer = AppletLauncher.applet(new DemoApplet()).start();
		viewer = AppletLauncher.applet(DemoApplet.class).start();
		applet = new FrameFixture(viewer);
		applet.show();
	}

	@Test
	public void buttonOneClick() {
		applet.label("label").requireText("Press a button.");
		applet.button("one").click();
		applet.label("label").requireText("Button One pressed.");
	}

	@Test
	public void buttonTwoClick() {
		applet.label("label").requireText("Press a button.");
		applet.button("two").click();
		applet.label("label").requireText("Button Two pressed.");
	}

	@After
	public void tearDown() {
		viewer.unloadApplet();
		applet.cleanUp();
	}
}
