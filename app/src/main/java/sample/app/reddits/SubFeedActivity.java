package sample.app.reddits;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sample.app.reddits.adapters.SubFeedAdapter;
import sample.app.reddits.utils.JSONParser;
import sample.app.reddits.model.SubFeed;
import sample.app.reddits.session.Session;

/**
 * Created by root on 12/12/16.
 */
public class SubFeedActivity extends AppCompatActivity {

    //private LinearLayout layout;
    private ListView list;
    private ImageView header;
    private SubFeedAdapter adapter;
    private String url, color, header_img, parent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_main);
        url = getIntent().getStringExtra("url");
        //color = getIntent().getStringExtra("color");
        header_img = getIntent().getStringExtra("header_img");
        parent_id = getIntent().getStringExtra("id");
        initViews();
        AsyncListLoader a = new AsyncListLoader(url, parent_id);
        a.execute();
    }

    private void initViews(){
        /*
        background -- Pensaba implementar esto pero los colores que venian eran muy feos
        layout = (RelativeLayout)findViewById(R.id.subfeed_layout);
        layout.setBackgroundColor(Color.parseColor(color));
        */
        //Header
        header = (ImageView) findViewById(R.id.feed_header);
        Session.getInstance(this).getImageLoader().DisplayImage(header_img, header);
        //List
        list = (ListView)findViewById(R.id.feed_list);
        adapter = new SubFeedAdapter(this, new ArrayList<SubFeed>());
        list.setAdapter(adapter);
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


    private class AsyncListLoader extends AsyncTask<String, Integer, JSONObject> {

        private JSONParser jsonParser = new JSONParser();
        private String url, parent;
        private HashMap<String, String> param = new HashMap<>();

        public AsyncListLoader(String url, String parent){
            this.parent = parent;
            this.url = url;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject json = jsonParser.makeHttpRequest(url, "GET", param);
                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                } else {
                    Log.d("JSON result", "is null");
                }
            }
            catch (Exception e){
                //assuming the only problem is going to be connection related
                return new JSONObject();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            try {
                if(s==null || !s.has("kind")){
                    Toast.makeText(SubFeedActivity.this, "You have Connection Problems, Getting Data Saved", Toast.LENGTH_LONG).show();
                    ArrayList<SubFeed> feeds = Session.getInstance(SubFeedActivity.this).getDb().findAllSubFeed(parent);
                    if(feeds.isEmpty())
                        SubFeedActivity.this.setListEmptyMessage(true);
                    else {
                        SubFeedActivity.this.setListEmptyMessage(false);
                        for (SubFeed f : feeds)
                            SubFeedActivity.this.adapter.add(f);
                    }
                }
                else {
                    JSONObject list = s.getJSONObject("data");
                    JSONArray childrens = list.getJSONArray("children");
                    for(int i = 0; i < childrens.length(); i++){
                        JSONObject o = childrens.getJSONObject(i).getJSONObject("data");
                        SubFeed f;
                        f = new SubFeed(o.getString("id"),
                                o.getString("subreddit_id"),
                                o.getString("title"),
                                o.getString("url"),
                                o.getString("author"),
                                o.getInt("score"),
                                o.getInt("num_comments"),
                                o.getBoolean("stickied")
                        );
                        Session.getInstance(SubFeedActivity.this).getDb().updateSubFeed(f);
                        SubFeedActivity.this.setListEmptyMessage(false);
                        SubFeedActivity.this.adapter.add(f);
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
