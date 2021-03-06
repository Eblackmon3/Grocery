package medellin.grocery.DB;

/**
 * Created by Eric on 1/25/2016.
 */


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;



// used to make the HTTP Reequest through java
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;




//Create a singleton class so that we don't have a lot  of DBHelper classes running around
// only want one DBHelper out connecting to the Parse Database and and making API calls. Threads my niggas
public class DBHelper extends AsyncTask<String, Void, JSONArray> {
//example of string that we use to ping edmame below
//"https://api.edamam.com/search?q=beef&app_id=${YOUR_APP_ID}&app_key=${YOUR_APP_KEY}"


    private final String url= "https://api.edamam.com/search?q=";
    private final String APP_ID= "dfc3e2a0";
    private final String APP_KEY= "ed05fbb6a513d54972979a4e88231040";
    private String querySpecs;
    private StringBuilder result= new StringBuilder();
    private static DBHelper instance = null;
    private JSONObject formattedResult;
    private JSONArray  hitsResults;
    // use this to return result
    public AsyncResponse delegate = null;
    private DBHelper() {
        // Exists only to defeat instantiation.
    }

    //Used to return information from the ASYNC class
    public interface AsyncResponse {
        void processFinish(JSONArray output);
    }


    //Remeber that this must be turned into JSON in order to easily use the information
    @Override
    protected JSONArray doInBackground(String... query) {

        String finalQuery= url+query[0]+"&app_id="+APP_ID+"&app_key="+APP_KEY+"&to="+query[1];
        HttpURLConnection urlConnection=null;
        try {
            URL obj = new URL(finalQuery);
            urlConnection = (HttpURLConnection) obj.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch (Exception e){
            Log.e("Error with request ",e.toString());


        }
        finally {
            urlConnection.disconnect();
        }

        try {
            formattedResult = new JSONObject(result.toString());
            hitsResults= formattedResult.getJSONArray("hits");
        }catch(JSONException e){
            Log.e("Exception from JSON", e.toString());
        }

        return hitsResults;

        /* from this point I need to correctly parse the json
            then place appropriately place it in the parse tables
         */
    }

    // a DBHelper is not created until you call this method
    // Once it is called no one else has access to a DBhelper
    public static DBHelper getInstance() throws IOException{
        if(instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }


    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }

    public JSONArray getSearchRsult() throws JSONException {
        return formattedResult.getJSONArray("hits");
    }

}
