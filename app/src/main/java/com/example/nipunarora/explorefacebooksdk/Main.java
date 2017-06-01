package com.example.nipunarora.explorefacebooksdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class Main extends AppCompatActivity {
    LoginButton fblogin;
    CallbackManager cm;
    static String TAG="MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fblogin=(LoginButton)findViewById(R.id.fblogin);
        fblogin.setReadPermissions(Arrays.asList("user_friends","public_profile"));
        cm=CallbackManager.Factory.create();
        fblogin.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //You get your access tokens over here process succesful login over here
                Log.d(TAG,"Login result:"+loginResult.toString());
                //Obtaining Uri for profile image

              /* String s= Profile.getCurrentProfile().getProfilePictureUri(10,10).toString();*/
                /*Log.d(TAG,"loginsuccess"+s);*/

                ////////////////////////////////
                /************************** Requesting User Friends who have our app installed *******/
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
            /* handle the result */
                                JSONObject x=response.getJSONObject();
                                try {
                                    JSONArray dta = x.getJSONArray("data");
                                    Log.d("GraphRequest",dta.get(0).toString());
                                }
                                catch (Exception e) {
                                    Log.d("JSON", "error" + e.toString());
                                }
                /*********************** ENd of user friend list request ******/

                            }
                        }
                ).executeAsync();
            }

            @Override
            public void onCancel() {
                 //gets invoked when user cancels the authentication
            }

            @Override
            public void onError(FacebookException error) {
                //Some connection error
                Log.d(TAG,"Login Error"+error.toString());
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
