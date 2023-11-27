package org.codebase.emailsendertest;

public class MailSender {
    //    private void sendEmail() {
//        // Sender's email address
//        final String username = "abdulrehmancs17@gmail.com";
//
//        // Sender's email password
//        final String password = "jul2398Arj@";
//
//        // Recipient's email address
//        String to = "malikarj98@gmail.com";
//
//        // Sender's properties
//        Properties props = new Properties();
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.setProperty("mail.host", "smtp.gmail.com");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.quitwait", "false");
//
//        // Create a session with the sender's credentials
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            // Create a default MimeMessage object
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header
//            message.setFrom(new InternetAddress(username));
//
//            // Set To: header field of the header
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//            // Set Subject: header field
//            message.setSubject("Testing JavaMail API");
//
//            // Now set the actual message
//            message.setText("Hello, this is a test email from JavaMail API.");
//
//            // Send the message
//            Transport.send(message);
//
//            Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
//            System.out.println("Email sent successfully!");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
