package com.accurate.automationBase;

import java.io.File;

/**
 * This class it consists the project constant file paths which is used in the project local repository
 * @author Kathiravan
 *
 */
public class FilePaths {

	public final static String USER_HOME=System.getProperty("user.dir")+File.separator;
	public final static String JAREXCEL = USER_HOME+"SI_Satisfaction_RegData.xlsx";
	public static final String TESTDATA_HOME=USER_HOME+"dataSource"+File.separator;
	public static final String PROPERTIES_HOME=USER_HOME+"propertiesFiles"+File.separator;
	public final static String PROPERITES_FILEPATH = PROPERTIES_HOME+"config.properties";
	public final static String PROPERTIES_RSC_HOME=USER_HOME+"src\\test\\resources\\";
	public final static String BOT_ICON = USER_HOME +"icon"+File.separator+"InvBotIcon.png";
	public final static String BOT_FAVICON = USER_HOME +"icon"+File.separator+"InvBotFavIcon.png";
	public final static String DATASOURCE_SI_SATFN_REG = TESTDATA_HOME+"SI_Satisfaction_RegData.xlsx";
	public static final String REPORT_HOME=USER_HOME+"reports"+File.separator;
	public static final String EXTENTREPORT_FILE=REPORT_HOME+"test-output\\FailedTestsScreenshots\\";
	public static final String SCREENSHOT_HOME=USER_HOME+"screenshots"+File.separator;
	public static final String AUTOIT_HOME = USER_HOME+"autoITScripts"+File.separator;
	public static final String AUTOIT_MANUALSCRIPT = AUTOIT_HOME+"withManualClick.exe";
	public static final String AUTOIT_CLICKSIGN = AUTOIT_HOME+"clickSign.exe";
	public static final String AUTOIT_ENTERPIN = AUTOIT_HOME+"enterPIN.exe";
	public static final String TESSDATA_HOME = USER_HOME+"tessdata"+File.separator;
	public static final String PROCESS_LOGFILE = USER_HOME+"processLogs"+File.separator;
	public static final String PROCESS_LOGFILE_DEFAULT = PROCESS_LOGFILE+"defaultLog.txt";
	public static final String ATTACHEMENT_HTMLREPORT = USER_HOME+"test-output"+File.separator+"emailable-report.html";
	public static final String EMAIL_PROPERTIESPATH=PROPERTIES_HOME+"email.properties";
	
}