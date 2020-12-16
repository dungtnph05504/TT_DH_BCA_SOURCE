package com.nec.asia.nic.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;


public class DateHandler extends JsonSerializer<Date>{

	@Override
	public void serialize(Date date, JsonGenerator jsonGen, SerializerProvider prov)
			throws IOException, JsonProcessingException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String formattedDate = sdf.format(date);
         jsonGen.writeString(formattedDate);	
	}
	

}
