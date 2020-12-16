package com.nec.asia.nic.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * type safe tool for reading and writing a DTO object
 *  
 * @author bright_zheng
 * 
 */
public class DTOUtil<T extends Serializable> {
	/**
	 * read to get and DTO object from input stream
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public T readDTO(InputStream in) throws IOException,
			ClassNotFoundException {
		BufferedInputStream brs = new BufferedInputStream(in);
		ObjectInputStream si = new ObjectInputStream(brs);
		
		@SuppressWarnings("unchecked")
		T obj = (T)si.readObject();
		si.close();
		brs.close();
		si = null;
		brs = null;
		
		return obj;
	}

	/**
	 * write a DTO object to input stream
	 * @param out
	 * @param object
	 * @throws IOException
	 */
	public void writeDTO(OutputStream out, T object) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(out);
		ObjectOutputStream so = new ObjectOutputStream(bos);
		so.writeObject(object);
		so.flush();
		so = null;
		bos.close();
		bos = null;
	}
	
	public void writeCompressedDTO(OutputStream out, T object) throws IOException {		
		BufferedOutputStream bos = new BufferedOutputStream(out);
		GZIPOutputStream gz = new GZIPOutputStream(bos);
		ObjectOutputStream oos = new ObjectOutputStream(gz);
		oos.writeObject(object);
		oos.flush();		
		oos.close();
		oos = null;
		gz.close();
		gz = null;
		bos.close();
		bos = null;

	}
	
	public T readCompressedDTO(InputStream in) throws IOException, ClassNotFoundException {

		BufferedInputStream brs = new BufferedInputStream(in);
	      GZIPInputStream gs = new GZIPInputStream(brs);
	      ObjectInputStream ois = new ObjectInputStream(gs);
	      @SuppressWarnings("unchecked")
	      T obj = (T) ois.readObject();
	      ois.close();
	      gs.close();
	      brs.close();

	      
		return obj;
	}
}