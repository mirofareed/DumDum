<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>JSP JavaMail Example</title>
</head>
<body>
	<%@ page import="java.util.*"%>
	<%@ page import="javax.mail.*"%>
	<%@ page import="javax.mail.internet.*"%>
	<%@ page import="javax.activation.*"%>
	<%@ page import="java.util.*,javax.mail.*"%>
	<%@ page import="javax.mail.internet.*"%>
	<%
		//Creating a result for getting status that messsage is delivered or not!
		String host = "smtp.gmail.com";
		final String user = "dumdumnewproject@gmail.com";//change accordingly
		final String password = "amirafareed";//change accordingly

		String to = request.getParameter("guest");//change accordingly

		//Get the session object

		try {
			Properties props = new Properties();
			props.put("mail.smtp.user", user);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "25");
			props.put("mail.debug", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");

			props.put("mail.smtp.EnableSSL.enable", "true");

			props.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");

			Session see = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user,
									password);
						}
					});

			//Compose the message
			try {
				MimeMessage message = new MimeMessage(see);
				message.setFrom(new InternetAddress(
						"dumdumnewproject@gmail.com"));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
				message.setSubject("Be My Guest");
				message.setText("This is simple program of sending email using JavaMail API");
				// add attatchment

				MimeBodyPart messageBodyPart = new MimeBodyPart();

				Multipart multipart = new MimeMultipart();

				messageBodyPart = new MimeBodyPart();
				String file = "/home/miro/Downloads/1434388690_Folder-My-documents-icon.png";
				String fileName = "/home/miro/Downloads/1434388690_Folder-My-documents-icon.png";
				DataSource source = new FileDataSource(file);
				messageBodyPart
						.setText("http://172.16.1.80:8070/finalProj/test.jsp?folder_id="
								+ request.getParameter("fileid")
								+ "&perm="
								+ request.getParameter("item"));
				//		(new DataHandler(source));
				//	messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);

				//send the message
				Transport.send(message);

				out.println("message sent successfully...");
				response.sendRedirect("success.jsp");

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	%>
	<title>Sending Mail in JSP</title>
	<h1>
		<center>
			<font color="blue">Sending Mail Using JSP</font>
	</h1>
	<b><center>
			<font color="red">
</body>
</html>