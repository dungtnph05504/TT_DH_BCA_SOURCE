package com.nec.asia.nic.utils;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * The Class ExceptionMessageFormatter.
 *
 * @author Alvin Chua
 */
public class ExceptionMessageFormatter {
   
   /** The Constant OPEN. */
   protected static final String OPEN = "[";
   
   /** The Constant CLOSE. */
   protected static final String CLOSE = "]";
   
   /** The Constant COLON. */
   protected static final String COLON = ":";

   /**
    * Format.
    *
    * @param e the e
    * @return the string
    */
   public static String format(Exception e) {
       StringBuffer buffy = new StringBuffer();
       StringWriter stringWriter = new StringWriter();
       PrintWriter printWriter = new PrintWriter(stringWriter);
       e.printStackTrace(printWriter);
       printWriter.flush();
       buffy.append(OPEN);
       buffy.append(e.getClass().getName());
       buffy.append(CLOSE);
       buffy.append(COLON);
       buffy.append(stringWriter.toString());

       return buffy.toString();
   }

   /**
    * Format.
    *
    * @param e the e
    * @param msg the msg
    * @return the string
    */
   public static String format(Exception e, String msg) {
       StringBuffer buffy = new StringBuffer();
       StringWriter stringWriter = new StringWriter();
       PrintWriter printWriter = new PrintWriter(stringWriter);
       e.printStackTrace(printWriter);
       printWriter.flush();
       buffy.append(OPEN);
       buffy.append(e.getClass().getName());
       buffy.append(CLOSE);
       buffy.append(OPEN);
       buffy.append(msg);
       buffy.append(CLOSE);

       buffy.append(COLON);
       buffy.append(stringWriter.toString());

       return buffy.toString();
   }

     /**
      * Format.
      *
      * @param e the e
      * @return the string
      */
     public static String format(Throwable e) {
         StringBuffer buffy = new StringBuffer();
         StringWriter stringWriter = new StringWriter();
         PrintWriter printWriter = new PrintWriter(stringWriter);
         e.printStackTrace(printWriter);
         printWriter.flush();
         buffy.append(OPEN);
         buffy.append(e.getClass().getName());
         buffy.append(CLOSE);
         buffy.append(COLON);
         buffy.append(stringWriter.toString());

         return buffy.toString();
     }

     /**
      * Format.
      *
      * @param e the e
      * @param msg the msg
      * @return the string
      */
     public static String format(Throwable e, String msg) {
         StringBuffer buffy = new StringBuffer();
         StringWriter stringWriter = new StringWriter();
         PrintWriter printWriter = new PrintWriter(stringWriter);
         e.printStackTrace(printWriter);
         printWriter.flush();
         buffy.append(OPEN);
         buffy.append(e.getClass().getName());
         buffy.append(CLOSE);
         buffy.append(OPEN);
         buffy.append(msg);
         buffy.append(CLOSE);

         buffy.append(COLON);
         buffy.append(stringWriter.toString());

         return buffy.toString();
     }

}
