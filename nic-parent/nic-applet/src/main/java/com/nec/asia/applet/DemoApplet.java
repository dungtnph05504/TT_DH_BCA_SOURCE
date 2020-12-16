package com.nec.asia.applet;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo applet
 * 
 * key points:
 * 1. make nric-application as one of dependencies for reusable domain beans
 * 2. copy to nric-application project upon package
 * 3. applet is easily testable by unit test
 * 
 * @author bright_zheng
 * 
 */
public class DemoApplet extends JApplet {
	private static final long serialVersionUID = 8795082864610954934L;
	
	Logger logger = LoggerFactory.getLogger(DemoApplet.class);
	
	JButton jbtnOne;
	JButton jbtnTwo;

	JLabel jlab;

	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					guiInit(); // initialize the GUI
				}
			});
		} catch (Exception exc) {
			logger.error("Can't create because of " + exc);
		}
	}

	// Called second, after init(). Also called
	// whenever the applet is restarted.
	public void start() {
		logger.debug("start");
	}

	// Called when the applet is stopped.
	public void stop() {
		logger.debug("stop");
	}

	// Called when applet is terminated. This is
	// the last method executed.
	public void destroy() {
		logger.debug("destroy");
	}

	// Setup and initialize the GUI.
	private void guiInit() {
		logger.debug("begin to init");
		// Set the applet to use flow layout.
		setLayout(new FlowLayout());

		// Create two buttons and a label.
		jbtnOne = new JButton("One");
		jbtnOne.setName("one");
		jbtnTwo = new JButton("Two");
		jbtnTwo.setName("two");

		jlab = new JLabel("Press a button.");
		jlab.setName("label");

		// Add action listeners for the buttons.
		jbtnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent le) {
				logger.debug("button one[name=one] clicked");
				jlab.setText("Button One pressed.");
			}
		});

		jbtnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent le) {
				logger.debug("button one[name=two] clicked");
				jlab.setText("Button Two pressed.");
			}
		});

		// Add the components to the applet's content pane.
		getContentPane().add(jbtnOne);
		getContentPane().add(jbtnTwo);
		getContentPane().add(jlab);
		
		logger.debug("GUI init done");
	}
}