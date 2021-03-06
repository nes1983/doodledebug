package ch.unibe.scg.doodle.server;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ch.unibe.scg.doodle.simon.SimonServer;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements IStartup {

	// The plug-in ID
	public static final String PLUGIN_ID = "DoodleDebug"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private SimonServer simonServer;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// use SIMON instead of RMI
		// startSimonServer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		stopSimonServer();
		plugin = null;
		super.stop(context);
	}

	private void startSimonServer() {
		try {
			System.out.println("Starting SIMON server...");
			simonServer = new SimonServer();
			System.out.println("Server started successfully.");
		} catch (Exception e) {
			System.out.println("Server could not be started.");
			e.printStackTrace();
		}
	}

	private void stopSimonServer() {
		simonServer.stop();
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public void earlyStartup() {
		startSimonServer();
	}
}
