package org.codebase.emailsendertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DashboardActivity extends AppCompatActivity {

    GoogleSignInOptions signInOptions;
    GoogleSignInClient signInClient;

    MaterialButton emailSend, signOut, generateQR;
    TextView userName, userEmail;
    ImageView qrCodeImage;
    String qrCodeImageBase64 = "";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        emailSend = findViewById(R.id.sendEmail);
        signOut = findViewById(R.id.signout);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        qrCodeImage = findViewById(R.id.qrImage);
        generateQR = findViewById(R.id.generateQrCode);

        generateQR.setOnClickListener(view -> {
            generateQRCode();
        });

        // set the sign in option via email
        signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // get signing in client
        signInClient = GoogleSignIn.getClient(this, signInOptions);

        // get the signed in client details form sign in account
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();

            userName.setText(personName);
            userEmail.setText(personEmail);
        }

        emailSend.setOnClickListener(view -> {
//            SendMail mail = new SendMail("abdulrehmancs17@gmail.com", "uwts nnup cjob mhap",
//                    "malikarj98@gmail.com",
//                    "Testing Email Sending",
//                    "Yes, it's working well\nI will use it always."
//            );
//            mail.execute();

            // Send email with both text and QR code image
            EmailSender emailSender = new EmailSender(bitmap);
            emailSender.sendEmailWithAttachment();
        });

        // sign out the user
        signOut.setOnClickListener(view -> {
            signOutUser();
        });
    }

    private void generateQRCode() {

        // Create a QRGEncoder object
        QRGEncoder qrgEncoder = new QRGEncoder("https://www.google.com", null, QRGContents.Type.TEXT, 500);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);
        // Get the QR Code as Bitmap
        bitmap = qrgEncoder.getBitmap();

        // Set the Bitmap on the ImageView
        qrCodeImage.setImageBitmap(bitmap);


    }

    private static class BitmapUtils {
        static String bitmapToBase64(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    private void signOutUser() {
        signInClient.signOut().addOnCompleteListener(task -> {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        });
    }

    public static void sendImageEmail(Session session, String toEmail, String subject, String body){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "image.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);

            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>" +
                    "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            //Set the multipart message to the email message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with image!!");
        }catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}