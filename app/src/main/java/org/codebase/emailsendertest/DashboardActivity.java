package org.codebase.emailsendertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import papaya.in.sendmail.SendMail;

public class DashboardActivity extends AppCompatActivity {

    GoogleSignInOptions signInOptions;
    GoogleSignInClient signInClient;

    MaterialButton emailSend, signOut;
    TextView userName, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        emailSend = findViewById(R.id.sendEmail);
        signOut = findViewById(R.id.signout);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

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
            SendMail mail = new SendMail("abdulrehmancs17@gmail.com", "jul2398arj@",
                    "malikarj98@gmail.com",
                    "Testing Email Sending",
                    "Yes, it's working well\nI will use it always.");
            mail.execute();
//            new EmailSender(this).sendEmail();
        });

        // sign out the user
        signOut.setOnClickListener(view -> {
            signOutUser();
        });
    }

    private void signOutUser() {
        signInClient.signOut().addOnCompleteListener(task -> {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        });
    }
}