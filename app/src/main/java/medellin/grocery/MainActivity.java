package medellin.grocery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.io.IOException;

import medellin.grocery.DB.DBHelper;

public class MainActivity extends AppCompatActivity {

    private final String PARSE_APPLICATION_ID= "oWReLz33rUQNlHJz4pheHgnOdQM3NIqeYJ3ZNd6H";
    private final String PARSE_CLIENT_KEY= "YIpvhkqCexkjErwlh8jyvJ7YtH1yLW8VDPdMRQ3D";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        //check if the user has logged in before
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = prefs.getBoolean("IS_LOGIN", false);

        //Check if logged in, if they are send them to their homepage if not send them to registration page
        // both pages TODO: 1/21/2016
        if(isLogin){
            //will containt the login activity

        }else{
            // Once the login activity has finished, set the IS_LOGIN prefernce to true so that the application knows not to relauch
            // will use SharedPreferencesEditor
            //Intent i = new Intent(this, loginActivity.class);
            //startActivityForResult(i, 1);
        }
        setContentView(R.layout.activity_main);

    }
}
