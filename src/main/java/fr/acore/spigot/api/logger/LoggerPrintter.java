package fr.acore.spigot.api.logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public interface LoggerPrintter {
	
	default public void println(File file, PrintWriter writer, Object... messages) throws IOException {
		for(Object message : messages) {
			println(file, writer, String.valueOf(message), false);
		}
	}
	
	default public void println(File file, PrintWriter writer, boolean close, Object... messages) throws IOException {
		for(Object message : messages) {
			println(file, writer, String.valueOf(message), close);
		}
	}
	
	default public void println(File file, PrintWriter writer, String... messages) throws IOException {
		for(String message : messages) {
			println(file, writer, message, false);
		}
	}

	default public void println(File file, PrintWriter writer, String message, boolean close) throws IOException {
		if(!file.exists()) file.createNewFile();
		writer.println(formatWithTime(message));
		writer.flush();
		if(close) writer.close();
	}
	
	
	@SuppressWarnings("deprecation")
	default public String formatWithTime(String log) {
		Date d = new Date();
		log = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "] " + log;
		return log;
	}
	
	@SuppressWarnings("unchecked")
	default public <T> T[] formatWithTime(T[] logs) {
		T[] copy = logs;
		
		for(int i = 0; i < logs.length; i++) {
			Date d = new Date();
			copy[i] = (T) (d.toString() + logs[i]);
		}
		return copy;
	}
	
	
}
