package com.nec.asia.nic.comp.integration.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.List;



/**
 * File name Filter.
 *
 * @author alvin_chua
 */
public class FileFilter implements FilenameFilter {
	
	/** The extensions set. */
	protected List extensionsSet;
 	
	/**
	 * Instantiates a new file filter.
	 *
	 * @param filterList the filter list
	 */
	public FileFilter (List filterList) {
		this.extensionsSet=filterList;
	}

	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept (File dir, String name) {
		final Iterator exts = extensionsSet.iterator();
		while (exts.hasNext()) {
		  String ext=exts.next().toString();
		  if (("*".equals(ext))||("*.*".equals(ext))||(name.toLowerCase().endsWith(ext))){
			  return true;
		  }
		}
		return false;
	}
}
