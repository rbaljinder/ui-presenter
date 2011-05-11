/**
 * 
 */
package org.baljinder.presenter.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Baljinder Randhawa
 * 
 */
public class ClassUtils {

	private static Log logger = LogFactory.getLog(ClassUtils.class);

	public static Object getNewInstance(Class<? extends Object> clazz, String exceptionMessage) {
		Object newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (Throwable th) {
			throw new RuntimeException(exceptionMessage, th);
		}
		return newInstance;
	}

	/**
	 * Got it from http://forums.sun.com/thread.jspa?messageID=10011355#10011355
	 * 
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<? extends Object>> getClassesForPackage(String packageName) {
		List<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();
		try {
			// This will hold a list of directories matching the pckgname.
			// There may be more than one if a package is split over multiple jars/paths
			ArrayList<File> directories = new ArrayList<File>();
			try {
				ClassLoader cld = Thread.currentThread().getContextClassLoader();
				if (cld == null)
					throw new ClassNotFoundException("Can't get class loader.");
				// Ask for all resources for the path
				Enumeration<URL> resources = cld.getResources(packageName.replace('.', '/'));
				while (resources.hasMoreElements()) {
					URL res = resources.nextElement();
					if (res.getProtocol().equalsIgnoreCase("jar")) {
						JarURLConnection conn = (JarURLConnection) res.openConnection();
						JarFile jar = conn.getJarFile();
						for (JarEntry e : Collections.list(jar.entries())) {
							if (e.getName().startsWith(packageName.replace('.', '/')) && e.getName().endsWith(".class") && !e.getName().contains("$")) {
								String className = e.getName().replace("/", ".").substring(0, e.getName().length() - 6);
								classes.add(Class.forName(className));
							}
						}
					} else
						directories.add(new File(URLDecoder.decode(res.getPath(), "UTF-8")));
				}
			} catch (NullPointerException x) {
				throw new RuntimeException(packageName + " does not appear to be " + "a valid package (Null pointer exception)", x);
			} catch (UnsupportedEncodingException encex) {
				throw new RuntimeException(packageName + " does not appear to be " + "a valid package (Unsupported encoding)", encex);
			} catch (IOException ioex) {
				throw new RuntimeException("IOException was thrown when trying " + "to get all resources for " + packageName, ioex);
			}
			// For every directory identified capture all the .class files
			for (File directory : directories) {
				if (directory.exists()) {
					// Get the list of the files contained in the package
					String[] files = directory.list();
					for (String file : files) {
						// we are only interested in .class files
						if (file.endsWith(".class")) {
							// removes the .class extension
							classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
						}
					}
				} else {
					throw new ClassNotFoundException(packageName + " (" + directory.getPath() + ") does not appear to be a valid package");
				}
			}
		} catch (Throwable th) {
			logger.error(th);
		}
		return classes;
	}

	public static List<Class<? extends Object>> getClassessOfInterface(String thePackage, Class<? extends Object> theInterface) {
		List<Class<? extends Object>> classList = new ArrayList<Class<? extends Object>>();
		for (Class<? extends Object> discovered : getClassesForPackage(thePackage)) {
			if (Arrays.asList(discovered.getInterfaces()).contains(theInterface))
				classList.add(discovered);
		}
		return classList;
	}
}
