package sample.app.reddits.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sample.app.reddits.R;
import sample.app.reddits.SubFeedActivity;
import sample.app.reddits.model.Feed;
import sample.app.reddits.session.Session;

/**
 * Created by root on 11/12/16.
 */
public class FeedAdapter extends BaseAdapter {


    private Activity activity;
    private ArrayList<Feed> feeds;
    private static LayoutInflater inflater=null;

    public FeedAdapter(Activity a, ArrayList<Feed> feeds) {
        activity = a;
        this.feeds=feeds;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(Feed f){ this.feeds.add(f); notifyDataSetChanged(); }

    public int getCount() {
        return feeds.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(vi==null) {
            vi = inflater.inflate(R.layout.feed_item, parent, false);
            ViewHolderFeed holder = new ViewHolderFeed();
            holder.icon = (ImageView)vi.findViewById(R.id.icon_list);
            holder.description = (TextView)vi.findViewById(R.id.list_description);
            holder.title = (TextView)vi.findViewById(R.id.list_title);
            holder.subscribers = (TextView)vi.findViewById(R.id.list_subscribers);
            holder.row = (LinearLayout)vi.findViewById(R.id.feed_row);
            vi.setTag(holder);
        }
        ViewHolderFeed holder = (ViewHolderFeed)vi.getTag();
        holder.row.setTag(position);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (Integer) v.getTag();
                Intent intent = new Intent(activity, SubFeedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("url", "https://www.reddit.com" + feeds.get(clickedPosition).getUrl() + ".json");
                intent.putExtra("header_img", feeds.get(clickedPosition).getHeader());
                intent.putExtra("id", "t5_" + feeds.get(clickedPosition).getId());
                intent.putExtra("color", feeds.get(clickedPosition).getKeycolor());
                activity.startActivity(intent);
            }
        });
        holder.title.setText(feeds.get(position).getText());
        holder.description.setText(feeds.get(position).getDescription());
        holder.subscribers.setText("Subscribers - "+feeds.get(position).getSubscribers());
        Session.getInstance(activity).getImageLoader().DisplayImage(feeds.get(position).getIcon(), holder.icon);
        return vi;
    }

    private class ViewHolderFeed{
        TextView title, description, subscribers;
        ImageView icon;
        LinearLayout row;

        private ViewHolderFeed() {
        }
    }

}
