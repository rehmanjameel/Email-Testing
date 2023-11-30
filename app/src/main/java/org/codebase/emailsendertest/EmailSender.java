package org.codebase.emailsendertest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

    private final String username = "abdulrehmancs17@gmail.com";
    private final String password = "uwts nnup cjob mhap";
    private final String to = "malikarj98@gmail.com";
    private static final String SUBJECT = "Testing Email Sending";
    private static final String MESSAGE = "Yes, it's working well. I will use it always.";

    private Bitmap imageAttachment;

    public EmailSender(Bitmap imageAttachment) {
        this.imageAttachment = imageAttachment;
    }

    public void sendEmailWithAttachment() {
        Executor executor = Executors.newSingleThreadExecutor();
        // Your existing email sending logic
        executor.execute(this::sendEmail);
    }

    private void sendEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(SUBJECT);

            // Create the message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(MESSAGE);

            // Create the attachment
            MimeBodyPart imageBodyPart = new MimeBodyPart();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageAttachment.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            imageBodyPart.setContent(byteArray, "image/png");
            imageBodyPart.setFileName("qrcode.png");

            // Combine message and attachment
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(imageBodyPart);

            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            Log.e("sending", "email sent");
        } catch (MessagingException e) {
            Log.e("sending", "email not sent" + e.toString());

        }
    }

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
}


