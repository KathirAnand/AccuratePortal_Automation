package com.accurate.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;

import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.accurate.automationBase.BaseClass;
import com.accurate.automationBase.FilePaths;

public class EmailUtility extends BaseClass {

	/**
	 * 
	 */
	public void sendEmail(int totalInvoice, int balanceInvoice, String reportPath) {

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
				if (emailProps.getProperty("NO_OF_RECIEVER") != null
						&& Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER")) > 0) {
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

					int numberOfReciever = Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER"));
					for (int i = 1; i <= numberOfReciever; i++) {
						email.addTo(emailProps.getProperty("TO_RECIEVER_" + i));
					}
//					email.addTo(emailProps.getProperty("TO"));
//					email.addTo("ashwanthkumar.rk@veritasfin.in");

					if (emailProps.getProperty("SUBJECT") != null) {
						email.setSubject(emailProps.getProperty("SUBJECT"));
					} else {
						email.setSubject("Accurate Portal Automation - Invoice Transfer Report");
					}

					String balanceMessage;
					if (balanceInvoice > 0) {
						balanceMessage = balanceInvoice + " Invoices are not moved";
					} else {
						balanceMessage = "All Invoices are transfered sucessfully";
					}

					email.setMsg(emailProps.getProperty("WELCOME") + "\n" + "Total Number of Invoices are "
							+ totalInvoice + ". " + balanceMessage + "\n" + emailProps.getProperty("CONTENT") + "\n"
							+ emailProps.getProperty("THANKS") + emailProps.getProperty("COMP_NAME") + "\n"
							+ emailProps.getProperty("DISCLAIMER"));

					// Create the attachment for HTML Report
//					EmailAttachment attachment = new EmailAttachment();
//					attachment.setPath(reportPath);
//					attachment.setDisposition(EmailAttachment.ATTACHMENT);
//					attachment.setDescription("Accurate Portal Automation Report");
//					attachment.setName("accurate-automation.html");
//					String excelPath="";
					if (reportPath != null) {
						// Create the attachment for processed Logs
						EmailAttachment excelAttachment = new EmailAttachment();
						excelAttachment.setPath(reportPath);
						excelAttachment.setDisposition(EmailAttachment.ATTACHMENT);
						excelAttachment.setDescription("Accurate Portal Automation Report");
//						excelAttachment.setName("Accurate Portal Automation Report"");
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
//	public static void main(String[] args) {
//		EmailUtility eu = new EmailUtility();
//		eu.sendEmail(4,2,"D:\\GitHub\\Accurate_Portal_Automation\\test-output\\reports\\AccuratePortal_2025-05-22_17-05-35.html","D:\\GitHub\\Accurate_Portal_Automation\\test-output\\screenshots\\AccurateSyncPage_20250522_170811.png");
//	}

	public void sendEmail(int totalInvoice, int balanceInvoice, String reportPath, String screenshotPath,String screenshotPath2) {
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
				if (emailProps.getProperty("NO_OF_RECIEVER") != null
						&& Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER")) > 0) {

					// Create HtmlEmail instead of MultiPartEmail
					HtmlEmail email = new HtmlEmail();
					email.setHostName("smtp.office365.com");
					email.setSmtpPort(587);
					email.setAuthenticator(new DefaultAuthenticator(emailProps.getProperty("AUTHENTICATOR"),
							emailProps.getProperty("AUTHENTICATOR_PASSWORD")));
					email.setSSLOnConnect(false);
					email.setStartTLSEnabled(true);
					email.setFrom(emailProps.getProperty("AUTHENTICATOR"));

					int numberOfReciever = Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER"));
					for (int i = 1; i <= numberOfReciever; i++) {
						email.addTo(emailProps.getProperty("TO_RECIEVER_" + i));
					}

					LocalDate currentDate = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					String formattedDate = currentDate.format(formatter);

					if (emailProps.getProperty("SUBJECT") != null) {
						email.setSubject(emailProps.getProperty("SUBJECT") + " for " + formattedDate);
					} else {
						email.setSubject("Accurate Portal Automation - Invoice Transfer Report");
					}

					String balanceMessage;
					if (balanceInvoice > 0) {
						balanceMessage = balanceInvoice + " Invoices are not moved";
					} else {
						balanceMessage = "All Invoices are transfered sucessfully";
					}

					// Embed the image and get the Content-ID
//	                String imagePath = "path/to/your/image.png"; // Replace with actual image path
					String cid = email.embed(new File(screenshotPath));

					// Create HTML content with embedded image
					String htmlMsg = "<html><body style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 800px; margin: 0 auto;\">"
							+ "<h2 style=\"color: #2c5aa0; border-bottom: 2px solid #2c5aa0; padding-bottom: 10px;\">"
							+ emailProps.getProperty("WELCOME") + "</h2>"
							+ "<p style=\"font-size: 16px; margin: 15px 0;\">Total Number of Invoices are "
							+ totalInvoice + ". " + balanceMessage + "</p>" + "<p style=\"margin: 15px 0;\">"
							+ emailProps.getProperty("CONTENT") + "</p>"
							+ "<div style=\"text-align: center; margin: 20px 0;\">" + "<img src=\"cid:" + cid
							+ "\" style=\"max-width: 100%; height: auto; border: 1px solid #ddd; border-radius: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);\" alt=\"Report Image\">"
							+ "</div>" + "<p style=\"margin: 15px 0;\">" + emailProps.getProperty("THANKS")
							+ emailProps.getProperty("COMP_NAME") + "</p>"
							+ "<p style=\"font-size: 12px; color: #666; margin-top: 20px; padding-top: 15px; border-top: 1px solid #eee;\"><small>"
							+ emailProps.getProperty("DISCLAIMER") + "</small></p>" + "</body></html>";

					// Set the HTML message
					email.setHtmlMsg(htmlMsg);

					// Set alternative text message for non-HTML email clients
					String textMsg = emailProps.getProperty("WELCOME") + "\n" + "Total Number of Invoices are "
							+ totalInvoice + ". " + balanceMessage + "\n" + emailProps.getProperty("CONTENT") + "\n"
							+ emailProps.getProperty("THANKS") + emailProps.getProperty("COMP_NAME") + "\n"
							+ emailProps.getProperty("DISCLAIMER");
					email.setTextMsg(textMsg);

					// Attach the report if available
					if (reportPath != null) {
						EmailAttachment excelAttachment = new EmailAttachment();
						excelAttachment.setPath(reportPath);
						excelAttachment.setDisposition(EmailAttachment.ATTACHMENT);
						excelAttachment.setDescription("Accurate Portal Automation Report");
						email.attach(excelAttachment);
					}

					// Send the email
					email.send();
					emailIPStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendEmail(int totalInvoice, int balanceInvoice, String reportPath,String screenshotPath) {
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
	            if (emailProps.getProperty("NO_OF_RECIEVER") != null && 
	                Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER")) > 0) {

	                // Create HtmlEmail instead of MultiPartEmail
	                HtmlEmail email = new HtmlEmail();
	                email.setHostName("smtp.office365.com");
	                email.setSmtpPort(587);
	                email.setAuthenticator(new DefaultAuthenticator(
	                    emailProps.getProperty("AUTHENTICATOR"),
	                    emailProps.getProperty("AUTHENTICATOR_PASSWORD")));
	                email.setSSLOnConnect(false);
	                email.setStartTLSEnabled(true);
	                email.setFrom(emailProps.getProperty("AUTHENTICATOR"));

	                int numberOfReciever = Integer.parseInt(emailProps.getProperty("NO_OF_RECIEVER"));
	                for (int i = 1; i <= numberOfReciever; i++) {
	                    email.addTo(emailProps.getProperty("TO_RECIEVER_" + i));
	                }
	                
	                LocalDate currentDate = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					String formattedDate = currentDate.format(formatter);
					
	                if (emailProps.getProperty("SUBJECT") != null) {
	                    email.setSubject(emailProps.getProperty("SUBJECT")+" for "+formattedDate);
	                } else {
	                    email.setSubject("Accurate Portal Automation - Invoice Transfer Report");
	                }

	                // Calculate metrics
	                int processedInvoices = totalInvoice - balanceInvoice;
	                double successRate = totalInvoice > 0 ? ((double) processedInvoices / totalInvoice) * 100 : 0;
	                String statusColor = balanceInvoice == 0 ? "#28a745" : "#ffc107";
	                String statusMessage = balanceInvoice == 0 ? "All invoices transferred successfully" : 
	                                     balanceInvoice + " invoice(s) require attention";

	                // Embed the image and get the Content-ID (if image exists)
	                String imageSection = "";
	                try {
	                    String imagePath = screenshotPath; // Replace with actual image path
	                    File imageFile = new File(imagePath);
	                    if (imageFile.exists()) {
	                        String cid = email.embed(imageFile);
	                        imageSection = 
	                            "<tr>" +
	                                "<td style='padding: 10px 0; text-align: center;'>" +
	                                    "<img src='cid:" + cid + "' style='max-width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);' alt='Process Visualization'>" +
	                                "</td>" +
	                            "</tr>";
	                    }
	                } catch (Exception e) {
	                    // Image embedding failed, continue without image
	                    System.out.println("Warning: Could not embed image - " + e.getMessage());
	                }

	                // Professional HTML email template
	                String htmlMsg = 
	                    "<!DOCTYPE html>" +
	                    "<html lang='en'>" +
	                    "<head>" +
	                        "<meta charset='UTF-8'>" +
	                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
	                        "<title>Invoice Automation Report</title>" +
	                    "</head>" +
	                    "<body style='margin: 0; padding: 0; font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; line-height: 1.6;'>" +
	                        "<table width='100%' cellpadding='0' cellspacing='0' style='background-color: #f8f9fa; padding: 20px 0;'>" +
	                            "<tr>" +
	                                "<td align='center'>" +
	                                    "<table width='600' cellpadding='0' cellspacing='0' style='background-color: #ffffff; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); overflow: hidden;'>" +
	                                        
	                                        "<!-- Header -->" +
	                                        "<tr>" +
	                                            "<td style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; text-align: center;'>" +
	                                                "<h1 style='color: #000000; margin: 0; font-size: 28px; font-weight: 300;'>" +
	                                                    (emailProps.getProperty("WELCOME") != null ? emailProps.getProperty("WELCOME") : "Automation Report") +
	                                                "</h1>" +
	                                                "<p style='color: #000000; margin: 10px 0 0 0; font-size: 16px;'>Accurate Portal - Invoice Transfer Process Completed</p>" +
	                                            "</td>" +
	                                        "</tr>" +
	                                        
	                                        "<!-- Status Banner -->" +
	                                        "<tr>" +
	                                            "<td style='padding: 0;'>" +
	                                                "<div style='background-color: " + statusColor + "; color: #ffffff; text-align: center; padding: 15px; font-size: 18px; font-weight: 600;'>" +
	                                                    " " + statusMessage +
	                                                "</div>" +
	                                            "</td>" +
	                                        "</tr>" +
	                                        
	                                        "<!-- Main Content -->" +
	                                        "<tr>" +
	                                            "<td style='padding: 30px;'>" +
	                                                
	                                                "<!-- Summary Cards -->" +
	                                                "<table width='100%' cellpadding='0' cellspacing='0' style='margin-bottom: 25px;'>" +
	                                                    "<tr>" +
	                                                        "<td width='32%' style='background-color: #e3f2fd; padding: 20px; border-radius: 8px; text-align: center; margin-right: 2%;'>" +
	                                                            "<h3 style='margin: 0 0 10px 0; color: #1976d2; font-size: 32px;'>" + totalInvoice + "</h3>" +
	                                                            "<p style='margin: 0; color: #555; font-size: 14px; font-weight: 600;'>Total Invoices</p>" +
	                                                        "</td>" +
	                                                        "<td width='2%'></td>" +
	                                                        "<td width='32%' style='background-color: #e8f5e8; padding: 20px; border-radius: 8px; text-align: center; margin: 0 2%;'>" +
	                                                            "<h3 style='margin: 0 0 10px 0; color: #388e3c; font-size: 32px;'>" + processedInvoices + "</h3>" +
	                                                            "<p style='margin: 0; color: #555; font-size: 14px; font-weight: 600;'>Transferred</p>" +
	                                                        "</td>" +
	                                                        "<td width='2%'></td>" +
	                                                        "<td width='32%' style='background-color: " + (balanceInvoice > 0 ? "#fff3e0" : "#e8f5e8") + "; padding: 20px; border-radius: 8px; text-align: center;'>" +
	                                                            "<h3 style='margin: 0 0 10px 0; color: " + (balanceInvoice > 0 ? "#f57c00" : "#388e3c") + "; font-size: 32px;'>" + String.format("%.1f%%", successRate) + "</h3>" +
	                                                            "<p style='margin: 0; color: #555; font-size: 14px; font-weight: 600;'>Success Rate</p>" +
	                                                        "</td>" +
	                                                    "</tr>" +
	                                                "</table>" +
	                                                
	                                                "<!-- Detailed Information -->" +
	                                                "<div style='background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 25px;'>" +
	                                                    "<h3 style='margin: 0 0 15px 0; color: #333; font-size: 18px;'>Process Details</h3>" +
	                                                    "<p style='margin: 0 0 10px 0; color: #666; font-size: 15px;'>" +
	                                                        (emailProps.getProperty("CONTENT") != null ? emailProps.getProperty("CONTENT") : 
	                                                         "The automated invoice transfer process has been completed. Please review the summary above and the detailed report attachment.") +
	                                                    "</p>" +
	                                                    
	                                                    (balanceInvoice > 0 ? 
	                                                        "<div style='background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 6px; padding: 15px; margin-top: 15px;'>" +
	                                                            "<p style='margin: 0; color: #856404; font-size: 14px;'>" +
	                                                                "<strong>Attention Required:</strong> " + balanceInvoice + " invoice(s) could not be transferred. " +
	                                                                "Please check the detailed report for specific error details and manual intervention requirements." +
	                                                            "</p>" +
	                                                        "</div>" 
	                                                        : "") +
	                                                "</div>" +
	                                            "</td>" +
	                                        "</tr>" +
	                                        "<!-- Image Section (if exists) -->" +
	                                        imageSection +
	                                        "<!-- Attachment Info -->" +
	                                        (reportPath != null ? 
	                                            "<tr>" +
	                                                "<td style='padding: 0 30px 20px 30px;'>" +
	                                                    "<div style='background-color: #e8f4fd; border: 1px solid #b3d9ff; border-radius: 8px; padding: 20px; text-align: center;'>" +
	                                                        "<h4 style='margin: 0 0 10px 0; color: #0366d6; font-size: 16px;'>Detailed Report Attached</h4>" +
	                                                        "<p style='margin: 0; color: #586069; font-size: 14px;'>Please find the comprehensive report with detailed transaction logs attached to this email.</p>" +
	                                                    "</div>" +
	                                                "</td>" +
	                                            "</tr>" 
	                                            : "") +
	                                        
	                                        "<!-- Footer -->" +
	                                        "<tr>" +
	                                            "<td style='background-color: #f8f9fa; padding: 25px; text-align: center; border-top: 1px solid #e9ecef;'>" +
	                                                "<p style='margin: 0 0 10px 0; color: #6c757d; font-size: 15px;'>" +
	                                                    (emailProps.getProperty("THANKS") != null ? emailProps.getProperty("THANKS") : "Best regards,") + " " +
	                                                    "<strong>" + (emailProps.getProperty("COMP_NAME") != null ? emailProps.getProperty("COMP_NAME") : "Automation Team") + "</strong>" +
	                                                "</p>" +
	                                                "<hr style='border: none; height: 1px; background-color: #dee2e6; margin: 15px 0;'>" +
	                                                "<p style='margin: 0; color: #868e96; font-size: 12px; line-height: 1.4;'>" +
	                                                    (emailProps.getProperty("DISCLAIMER") != null ? emailProps.getProperty("DISCLAIMER") : 
	                                                     "This is an automated report generated by the invoice processing system. For any questions or concerns, please contact your system administrator.") +
	                                                "</p>" +
	                                                "<p style='margin: 10px 0 0 0; color: #adb5bd; font-size: 11px;'>" +
	                                                    "Generated on " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")) +
	                                                "</p>" +
	                                            "</td>" +
	                                        "</tr>" +
	                                        
	                                    "</table>" +
	                                "</td>" +
	                            "</tr>" +
	                        "</table>" +
	                    "</body>" +
	                    "</html>";

	                // Set the HTML message
	                email.setHtmlMsg(htmlMsg);

	                // Set alternative text message for non-HTML email clients
	                String textMsg = 
	                    "INVOICE AUTOMATION REPORT\n" +
	                    "========================\n\n" +
	                    (emailProps.getProperty("WELCOME") != null ? emailProps.getProperty("WELCOME") : "Automation Report") + "\n\n" +
	                    "SUMMARY:\n" +
	                    "--------\n" +
	                    "Total Invoices: " + totalInvoice + "\n" +
	                    "Processed Successfully: " + processedInvoices + "\n" +
	                    "Pending/Failed: " + balanceInvoice + "\n" +
	                    "Success Rate: " + String.format("%.1f%%", successRate) + "\n\n" +
	                    "STATUS: " + statusMessage + "\n\n" +
	                    "DETAILS:\n" +
	                    "--------\n" +
	                    (emailProps.getProperty("CONTENT") != null ? emailProps.getProperty("CONTENT") : 
	                     "The automated invoice transfer process has been completed.") + "\n\n" +
	                    (balanceInvoice > 0 ? 
	                        "ATTENTION REQUIRED:\n" +
	                        balanceInvoice + " invoice(s) could not be processed automatically. " +
	                        "Please check the detailed report for specific error details.\n\n" : "") +
	                    (reportPath != null ? "A detailed report is attached to this email.\n\n" : "") +
	                    (emailProps.getProperty("THANKS") != null ? emailProps.getProperty("THANKS") : "Best regards,") + " " +
	                    (emailProps.getProperty("COMP_NAME") != null ? emailProps.getProperty("COMP_NAME") : "Automation Team") + "\n\n" +
	                    "---\n" +
	                    (emailProps.getProperty("DISCLAIMER") != null ? emailProps.getProperty("DISCLAIMER") : 
	                     "This is an automated report. For questions, contact your system administrator.") + "\n" +
	                    "Generated: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm"));
	                
	                email.setTextMsg(textMsg);

	                // Attach the report if available
	                if (reportPath != null) {
//	                    EmailAttachment excelAttachment = new EmailAttachment();
//	                    excelAttachment.setPath(reportPath);
//	                    excelAttachment.setDisposition(EmailAttachment.ATTACHMENT);
//	                    excelAttachment.setDescription("Accurate Portal Automation Report");
//	                    excelAttachment.setName("Invoice_Automation_Report_" + 
//	                        java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//	                    email.attach(excelAttachment);
	                    
	                    
	                    EmailAttachment excelAttachment = new EmailAttachment();
						excelAttachment.setPath(reportPath);
						excelAttachment.setDisposition(EmailAttachment.ATTACHMENT);
						excelAttachment.setDescription("Accurate Portal Automation Report");
//						excelAttachment.setName("Accurate Portal Automation Report"");
						email.attach(excelAttachment);
	                }

	                // Send the email
	                email.send();
	                emailIPStream.close();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}