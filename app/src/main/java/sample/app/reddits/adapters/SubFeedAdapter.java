package sample.app.reddits.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sample.app.reddits.R;
import sample.app.reddits.model.Feed;
import sample.app.reddits.model.SubFeed;
import sample.app.reddits.session.Session;

/**
 * Created by root on 12/12/16.
 */
public class SubFeedAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<SubFeed> feeds;
    private static LayoutInflater inflater=null;

    public SubFeedAdapter(Activity a, ArrayList<SubFeed> feeds) {
        activity = a;
        this.feeds=feeds;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(SubFeed f){ this.feeds.add(f); notifyDataSetChanged(); }

    public int getCount() {
        return feeds.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(vi==null) {
            vi = inflater.inflate(R.layout.subfeed_item, parent, false);
            ViewHolderSubFeed holder = new ViewHolderSubFeed();
            holder.title = (TextView)vi.findViewById(R.id.subfeed_title);
            holder.rank = (TextView)vi.findViewById(R.id.subfeed_rank);
            holder.details = (TextView)vi.findViewById(R.id.subfeed_details);
            holder.comments = (TextView)vi.findViewById(R.id.subfeed_comments);
            holder.score = (TextView)vi.findViewById(R.id.subfeed_score);
            holder.row = (LinearLayout)vi.findViewById(R.id.subfeed_layout_item);
            vi.setTag(holder);
        }

        ViewHolderSubFeed holder = (ViewHolderSubFeed)vi.getTag();

        holder.title.setText(feeds.get(position).getText());
        holder.details.setText("Submitted by "+feeds.get(position).getUser());
        holder.comments.setText("Comments - " + feeds.get(position).getComments());
        holder.score.setText(feeds.get(position).getScore()+"");
        if(feeds.get(position).isStickied()) {
            holder.rank.setText("S");
        }
        else{
            holder.rank.setText(realPosition(feeds,position)+"");
        }
        holder.row.setTag(position);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (Integer)v.getTag();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feeds.get(clickedPosition).getUrl()));
                SubFeedAdapter.this.activity.startActivity(browserIntent);
            }
        });
        return vi;
    }

    private int realPosition(ArrayList<SubFeed> array, int posi){
        int i = 0;
        for(SubFeed f : array){
            if(f.isStickied())
                i++;
        }
        return posi+1-i;
    }

    private class ViewHolderSubFeed{
        TextView title, details, comments, score, rank;
        LinearLayout row;

        private ViewHolderSubFeed() {
        }
    }
}
