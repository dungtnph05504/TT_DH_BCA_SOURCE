package com.nec.asia.nic.dx.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Date unmarshal(String value) {
        try {
            return dateFormat.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    public String marshal(Date value) {
        try {
            return dateFormat.format(value);
        } catch (Exception e) {
            return null;
        }
    }

}
