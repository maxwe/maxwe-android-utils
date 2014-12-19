package org.maxwe.android.epub.sample.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


import android.text.format.Time;

public class Loooooger {

    private static String LOG_SAVE_PATH = "";
    private static PrintStream infoPrintStream;
	private static String getLogName(){
		Time time = new Time();
		time.setToNow();
		int year = time.year;
		int month = time.month;
		int monthDay = time.monthDay;
		int hour = time.hour;
		int minute = time.minute;
		int second = time.second;
		return year + "_" + month + "_" + monthDay + "-" + hour + ":" + minute + ":" + second;
	}

	public static void logger(Exception e){
		try {
			File file = new File(LOG_SAVE_PATH);
			if(!file.exists()){
				file.mkdirs();
			}
			e.printStackTrace(new PrintStream(new File(LOG_SAVE_PATH + getLogName() + "-error.log")));
		} catch (FileNotFoundException e1) {
			e.printStackTrace();
		}
	}
	public static void logger(String info){
		try {
			File file = new File(LOG_SAVE_PATH);
			if(!file.exists()){
				file.mkdirs();
			}
            if(infoPrintStream == null){
                infoPrintStream = new PrintStream(new File(LOG_SAVE_PATH + getLogName() + "-info.log"));
            }
			infoPrintStream.append(info + "\r\n");

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	}
}
