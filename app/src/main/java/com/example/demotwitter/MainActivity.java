package com.example.demotwitter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {

    TwitterLoginButton default_login_button;
    LinearLayout Custom_login_button;
    TwitterAuthClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);
        client = new TwitterAuthClient();

        Custom_login_button = findViewById(R.id.Custom_login_button);
        customLoginTwitter();

        default_login_button = findViewById(R.id.default_login_button);
        default_login_button.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(MainActivity.this, exception.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(TwitterSession twitterSession) {

        String username = twitterSession.getUserName();
        Intent intent = new Intent(MainActivity.this,Homepage.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        default_login_button.onActivityResult(requestCode, resultCode, data);
    }


    //Custom button
    public void customLoginTwitter() {

            client.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    TwitterAuthToken authToken = session.getAuthToken();
                    String token = authToken.token;
                    String secret = authToken.secret;

                    customLogin(session);

                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    Toast.makeText(MainActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void customLogin(TwitterSession session) {
        String username = session.getUserName();
        Intent intent = new Intent(MainActivity.this,Homepage.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
