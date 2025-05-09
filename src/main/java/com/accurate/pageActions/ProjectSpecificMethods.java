package com.accurate.pageActions;

import java.util.Calendar;
import java.util.Date;
import java.lang.Math;


import org.openqa.selenium.TakesScreenshot;

import com.accurate.automationBase.BaseClass;
import com.accurate.automationBase.FilePaths;

import org.openqa.selenium.OutputType;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProjectSpecificMethods extends BaseClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String getTimestamp() {
		return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
	}

	public static String getDatestampWithMinutes() {
		return new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(new Date());
	}

	public static String getExcelNameForWrite() {
		return "SI_SatisfiedData_" + getDatestampWithMinutes() + ".xlsx";
	}

	public static String getCaptchaNameWithMinutes() {
		return "captcha" + getDatestampWithMinutes() + ".png";
	}

	public static String getProcessLogFilenameWithMinutes() {
		return "processLog_" + getDatestampWithMinutes() + ".txt";
	}

	public static String captureScreen(String tname) throws IOException {

		String timeStamp = getTimestamp();
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = FilePaths.SCREENSHOT_HOME + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;
	}

	public static String removeSpaceInSIID(String securityInterestID) {
		return securityInterestID.replaceAll("\\s", "");
	}

	public static boolean lengthValidation(String securityInterestID) {
		String[] chars = securityInterestID.split("");
		return (chars.length == 12) ? true : false;
	}

//	public String extractTextFromImage(String path) throws TesseractException {
//		ITesseract image = new Tesseract();
//		image.setDatapath(FilePaths.TESSDATA_HOME);
//		image.setLanguage("eng");
//		return image.doOCR(new File(path));
//	}

	public static void writeLogIntoTxtFile(String data) throws IOException {
		try {
			String processLogsPath="";
			if (processLogsPath != null) {	
				File file = new File(processLogsPath);
				FileWriter writer = new FileWriter(processLogsPath, true);
				BufferedWriter bf = new BufferedWriter(writer);
				if (file.exists()) {
					bf.write(data + "\r\n");
					bf.close();
				} else if (file.createNewFile()) {
					bf.write(data + "\r\n");
					bf.close();
				}
			} else {
				File file = new File(FilePaths.PROCESS_LOGFILE_DEFAULT);
				FileWriter writer = new FileWriter(FilePaths.PROCESS_LOGFILE_DEFAULT, true);
				BufferedWriter bf = new BufferedWriter(writer);
				if (file.exists()) {
					bf.write(data + "\r\n");
					bf.close();
				} else if (file.createNewFile()) {
					bf.write(data + "\r\n");
					bf.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static long daysCount(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); CERSAI Portal is updated so that closed date format is changed
		Date startDate = format.parse(date); 
		
		Date endDate=format.parse(format.format(Calendar.getInstance().getTime()));
		
		long diff = startDate.getTime() - endDate.getTime();
		long diffDays = Math.abs(diff / 1000 / 60 / 60 / 24);
//		System.out.println(diffDays);
		return diffDays;
	}
	
//	public static void main(String[] args) {
//		try {
//			daysCount("09-08-2024");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}