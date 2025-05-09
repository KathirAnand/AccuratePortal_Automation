package com.accurate.utilities;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;

import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.accurate.automationBase.BaseClass;
import com.accurate.automationBase.FilePaths;

public class EmailUtility extends BaseClass {

	/**
	 * 
	 */

	public void sendEmail() {

		Properties emailProps = new Properties();
		FileInputStream emailIPStream = null;
		try {
			emailIPStream = new FileInputStream(FilePaths.EMAIL_PROPERTIESPATH);
			emailProps.load(emailIPStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {

			if (emailProps.getProperty("AUTHENTICATOR") != null
					&& emailProps.getProperty("AUTHENTICATOR_PASSWORD") != null) {
				if (emailProps.getProperty("NO_OF_RECIEVER") != null && Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER"))>0) {
//					URL url = new URL(System.getProperty("user.dir") + "\\test-output\\emailable-report.html");

					// Create the email message
					MultiPartEmail email = new MultiPartEmail();
					email.setHostName("smtp.office365.com");
					email.setSmtpPort(587);
					email.setAuthenticator(new DefaultAuthenticator(emailProps.getProperty("AUTHENTICATOR"),
							emailProps.getProperty("AUTHENTICATOR_PASSWORD")));
					email.setSSLOnConnect(false); // or true if using SSL
					email.setStartTLSEnabled(true); // or false if using SSL
					email.setFrom(emailProps.getProperty("AUTHENTICATOR"));
					// ashwanthkumar.rk@veritasfin.in

					int numberOfReciever=Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER"));
					for(int i=1;i<=numberOfReciever;i++) {
						email.addTo(emailProps.getProperty("TO_RECIEVER_"+i));
					}
//					email.addTo(emailProps.getProperty("TO"));
//					email.addTo("ashwanthkumar.rk@veritasfin.in");
					
					if(emailProps.getProperty("SUBJECT")!=null) {
						email.setSubject(emailProps.getProperty("SUBJECT"));
					}else {
						email.setSubject("InvBot Automation Report");
					}
					email.setMsg(emailProps.getProperty("WELCOME")
							+ emailProps.getProperty("CONTENT")
							+ "\n" + emailProps.getProperty("THANKS") + emailProps.getProperty("COMP_NAME")
					       +"\n"+emailProps.getProperty("DISCLAIMER"));

					// Create the attachment for HTML Report
//					EmailAttachment attachment = new EmailAttachment();
//					attachment.setPath(FilePaths.ATTACHEMENT_HTMLREPORT);
//					attachment.setDisposition(EmailAttachment.ATTACHMENT);
//					attachment.setDescription("InvBot Error Report");
//					attachment.setName("emailable-report.html");
					String excelPath="";
					if (excelPath != null) {
						// Create the attachment for processed Logs
						EmailAttachment excelAttachment = new EmailAttachment();
						excelAttachment.setPath(excelPath);
						excelAttachment.setDisposition(EmailAttachment.ATTACHMENT);
						excelAttachment.setDescription("Satisfied SI Report");
						excelAttachment.setName("Satisfied SI.xlsx");
						email.attach(excelAttachment);
					}
//					if (processLogsPath != null) {
//						// Create the attachment for processed Logs
//						EmailAttachment processLogAttachment = new EmailAttachment();
//						processLogAttachment.setPath(processLogsPath);
//						processLogAttachment.setDisposition(EmailAttachment.ATTACHMENT);
//						processLogAttachment.setDescription("Processed Logs");
//						processLogAttachment.setName("processLogName.txt");
//						email.attach(processLogAttachment);
//					}

					// add the attachment
//					email.attach(attachment);

					// send the email
					email.send();
					emailIPStream.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void sendSimpleEmail() throws EmailException {

		Email email = new SimpleEmail();
		email.setHostName("smtp.office365.com");
		email.setSmtpPort(587); // or 465 for SSL
		email.setAuthenticator(new DefaultAuthenticator("kathiravana@inventsoftlabs.com", "!Daredewin@123"));
		email.setSSLOnConnect(false); // or true if using SSL
		email.setStartTLSEnabled(true); // or false if using SSL
		email.setFrom("kathiravana@inventsoftlabs.com");
		email.addTo("suryaprakashs@inventsoftlabs.com");
		
		email.setSubject("Test Email");
		email.setMsg("InvBot Automation Report");
		email.setMsg(
				"Hi Team,\n" + "Find the attached Excel containing Satisfied Security Interest with Transaction ID \n"
						+ "\n" + "Thanks with best regards \n" + "Invent Softlabs (India) Pvt Ltd");
		email.send();

	}
	public static void main(String[] args) {
		EmailUtility eu = new EmailUtility();
		eu.sendEmail();
	}

}