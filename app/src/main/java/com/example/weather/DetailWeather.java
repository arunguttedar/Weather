package com.example.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;



public class DetailWeather extends Activity {
    TextView cityNamme;
    String code;
    String date;
    String temp;
    String text;
    TextView codeTv,dateTv,tempTv,textTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        cityNamme=(TextView)findViewById(R.id.cityName);
        codeTv =(TextView)findViewById(R.id.code);
        dateTv=(TextView)findViewById(R.id.date);
        tempTv=(TextView)findViewById(R.id.temp);
         textTv=(TextView)findViewById(R.id.text);

        Bundle extras = getIntent().getExtras();
            String data = extras.getString("cityName");
            cityNamme.setText(data);

       new getWeahter().execute(data,"in");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class getWeahter extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
           // String url = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where in (select woeid from geo.places(1) where text="+params[0]+")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
           // String url = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=mumbai, in)&format=json&env=store://datatables.org/alltableswithkeys";
            String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+params[0]+"%2C%20"+params[1]+"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

            JSONObject obj = new JSONObject();
            try {

                JSONObject resObj = RestJsonClient.connect(url);

                Log.d("url get data", "+url");
                System.out.println("resObj-------------------" + resObj);
               JSONObject objquery=resObj.getJSONObject("query");

                JSONObject objresult=objquery.getJSONObject("results");
                JSONObject objchannel=objresult.getJSONObject("channel");
                JSONObject objitem=objchannel.getJSONObject("item");
                JSONObject objcondition=objitem.getJSONObject("condition");
                 code=objcondition.getString("code");
                 date=objcondition.getString("date");
                 temp=objcondition.getString("temp");
                 text=objcondition.getString("text");
                Log.d("DataWeather",code+date+temp+text);

            } catch (JSONException e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            codeTv.setText(code);
            dateTv.setText(date);
            tempTv.setText(temp);
            textTv.setText(text);
        }
    }
}
