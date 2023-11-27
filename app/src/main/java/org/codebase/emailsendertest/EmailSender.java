//package org.codebase.emailsendertest;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.widget.Toast;
//
//import java.util.Properties;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class EmailSender {
//
//    private final Context context;
//    private final String username = "abdulrehmancs17@gmail.com";
//    private final String password = "jul2398Arj@";
//    private final String to = "malikarj98@gmail.com";
//
//    public EmailSender(Context context) {
//        this.context = context;
//    }
//
//    public void sendEmail() {
//        // Use Executors.newSingleThreadExecutor() to run the task in a background thread
//        Executor executor = Executors.newSingleThreadExecutor();
//
//        executor.execute(new EmailSenderRunnable());
//    }
//
//    private class EmailSenderRunnable implements Runnable {
//        @Override
//        public void run() {
//            try {
//                // Sender's properties
//                Properties props = new Properties();
//                props.setProperty("mail.transport.protocol", "smtp");
//                props.setProperty("mail.host", "smtp.gmail.com");
//                props.put("mail.smtp.auth", "true");
//                props.put("mail.smtp.port", "465");
//                props.put("mail.smtp.socketFactory.port", "465");
//                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//                props.put("mail.smtp.socketFactory.fallback", "false");
//                props.setProperty("mail.smtp.quitwait", "false");
//
//                // Create a session with the sender's credentials
//                Session session = Session.getInstance(props, new Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//                // Create a default MimeMessage object
//                MimeMessage message = new MimeMessage(session);
//
//                // Set From: header field of the header
//                message.setFrom(new InternetAddress(username));
//
//                // Set To: header field of the header
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//                // Set Subject: header field
//                message.setSubject("Testing JavaMail API");
//
//                // Now set the actual message
//                message.setText("Hello, this is a test email from JavaMail API.");
//
//                // Send the message
//                Transport.send(message);
//
//                // Show a toast on the UI thread
//                showToast("Email sent successfully!");
//
//            } catch (MessagingException e) {
//                e.printStackTrace();
//                // Show a toast on the UI thread
//                showToast("Failed to send email.");
//            }
//        }
//    }
//
//    private void showToast(final String message) {
//        // Use Handler to show a toast on the UI thread
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
//
//
