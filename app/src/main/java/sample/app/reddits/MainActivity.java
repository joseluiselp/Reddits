package sample.app.reddits;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sample.app.reddits.adapters.FeedAdapter;
import sample.app.reddits.utils.JSONParser;
import sample.app.reddits.model.Feed;
import sample.app.reddits.session.Session;


public class MainActivity extends AppCompatActivity {

    private ListView list;
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.main_list);
        adapter = new FeedAdapter(this, new ArrayList<Feed>());
        list.setAdapter(adapter);
        AsyncListLoader a = new AsyncListLoader();
        a.execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setListEmptyMessage(boolean b){
        if(b)
            ((TextView) findViewById(android.R.id.empty)).setText("Esta Lista esta vacia");
        else
            ((TextView) findViewById(android.R.id.empty)).setVisibility(View.GONE);
    }

    private class AsyncListLoader extends AsyncTask<String, Integer, JSONObject>{

        private JSONParser jsonParser = new JSONParser();
        private static final String REDEEM_URL = "https://www.reddit.com/reddits.json";
        private HashMap<String, String> param = new HashMap<>();

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject json = jsonParser.makeHttpRequest(REDEEM_URL, "GET", param);
                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                } else {
                    Log.d("JSON result", "is null");
                }
            }catch (Exception e) {
                //assuming the only problem is going to be connection related
                return new JSONObject();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            try {
                if(s==null || !s.has("kind")){
                    Toast.makeText(MainActivity.this, "You have Connection Problems, Getting Data Saved", Toast.LENGTH_LONG).show();
                    ArrayList<Feed> feeds = Session.getInstance(MainActivity.this).getDb().findAllFeed();
                    if(feeds.isEmpty())
                        MainActivity.this.setListEmptyMessage(true);
                    else {
                        MainActivity.this.setListEmptyMessage(false);
                        for (Feed f : feeds)
                            MainActivity.this.adapter.add(f);
                    }
                }
                else {
                    JSONObject list = s.getJSONObject("data");
                    JSONArray childrens = list.getJSONArray("children");
                    for(int i = 0; i < childrens.length(); i++){
                        JSONObject o = childrens.getJSONObject(i).getJSONObject("data");
                        Feed f;
                        f = Session.getInstance(MainActivity.this).getDb().findFeed(o.getString("id"));
                        if(f==null) {
                            f = new Feed(o.getString("id"),
                                    o.getString("public_description"),
                                    o.getString("icon_img"),
                                    o.getString("header_img"),
                                    o.getString("title"),
                                    o.getString("url"),
                                    o.getString("key_color"),
                                    o.getInt("subscribers")
                            );
                            Session.getDb().insertFeed(f);
                        }
                        MainActivity.this.setListEmptyMessage(false);
                        MainActivity.this.adapter.add(f);
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
