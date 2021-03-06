package ch.unibe.scg.doodle.server.views;

import static ch.unibe.scg.doodle.server.views.DoodleLocationCodes.DOODLE_DEBUG_PREFIX;
import static ch.unibe.scg.doodle.server.views.DoodleLocationCodes.JAVA_FILE_LINK_PREFIX;
import static ch.unibe.scg.doodle.server.views.DoodleLocationCodes.EXTERNAL_LINK_PREFIX;
import static ch.unibe.scg.doodle.server.views.DoodleLocationCodes.LIGHTBOX_CLOSE;
import static ch.unibe.scg.doodle.server.views.DoodleLocationCodes.LIGHTBOX_STACK_OFFSET;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.util.SystemUtil;

/**
 * Listener for browser which DoodleDebug renders into. Listens to location
 * changes (link calls for instance) and checks if it contains a message for the
 * DoodleDebug server instance.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleLocationListener implements LocationListener {

	@Override
	public void changing(LocationEvent event) {
		if (event.location.startsWith(EXTERNAL_LINK_PREFIX)) {
			handleExternalLink(event);
		} else if (event.location.startsWith(DOODLE_DEBUG_PREFIX))
			handleDoodledebugLocationEvent(event);
		else if (event.location.startsWith(JAVA_FILE_LINK_PREFIX))
			handleJavaFileLocationEvent(event);
	}

	void handleExternalLink(LocationEvent event) {
		URL url;
		try {
			url = new URL(event.location.replaceFirst(EXTERNAL_LINK_PREFIX, ""));
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		}
		SystemUtil.openInBrowser(url);
		event.doit = false;
	}

	private void handleDoodledebugLocationEvent(LocationEvent event) {
		try {
			DoodleServer doodleServer = DoodleServer.instance();

			int id = Integer.parseInt(event.location.replaceFirst(
					DOODLE_DEBUG_PREFIX, ""));
			if (DoodleDebugProperties.developMode())
				System.out.println("SERVER: Received message from javascript: "
						+ id);
			event.doit = false; // prevent any actual changing of location

			if (id == LIGHTBOX_CLOSE) {
				doodleServer.lightboxClosed();
				return;
			} else if (id < 0) {
				int fromZero = (-id + LIGHTBOX_STACK_OFFSET);
				int toCutOff = fromZero * -(fromZero % 2 * 2 - 1) / 2;
				doodleServer.cutoffFromStack(toCutOff);
				return;
			}

			doodleServer.drawObjectWithID(id);
		} catch (NumberFormatException e) {
			// nothing to do here (must have been a real link)
		}
	}

	private void handleJavaFileLocationEvent(LocationEvent event) {
		DoodleServer doodleServer = DoodleServer.instance();

		String[] parts = event.location.split(":");
		String className = parts[parts.length - 2];
		int lineNumber = Integer.parseInt(parts[parts.length - 1]);

		event.doit = false;

		doodleServer.openJavaFile(className, lineNumber);
	}

	@Override
	public void changed(LocationEvent event) {
		// nothing to do here
	}

}
