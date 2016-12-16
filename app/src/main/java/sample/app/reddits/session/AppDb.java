package sample.app.reddits.session;

import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sample.app.reddits.db.DbHelper;
import sample.app.reddits.model.Feed;
import sample.app.reddits.model.SubFeed;

public class AppDb {

    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public AppDb(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<Feed> findAllFeed(){
        ArrayList<Feed> feeds = new ArrayList<Feed>();
        Cursor c = database.rawQuery("Select * from feed",new String[]{});
        if (c != null && c.moveToFirst()) {
            while (c.isAfterLast() == false)
            {
                feeds.add(new Feed(c.getString(c.getColumnIndex("Id")),
                        c.getString(c.getColumnIndex("Description")),
                        c.getString(c.getColumnIndex("Icon")),
                        c.getString(c.getColumnIndex("Header")),
                        c.getString(c.getColumnIndex("Title")),
                        c.getString(c.getColumnIndex("Url")),
                        c.getString(c.getColumnIndex("Keycolor")),
                        c.getInt(c.getColumnIndex("Subscribers"))
                ));
                c.moveToNext();
            }
        }
        return feeds;
    }

    public Feed findFeed(String id){
        Cursor c = database.rawQuery("Select * from feed where Id = ?",new String[]{id});
        if (c != null && c.moveToFirst()) {
            return new Feed(id,
                    c.getString(c.getColumnIndex("Description")),
                    c.getString(c.getColumnIndex("Icon")),
                    c.getString(c.getColumnIndex("Header")),
                    c.getString(c.getColumnIndex("Title")),
                    c.getString(c.getColumnIndex("Url")),
                    c.getString(c.getColumnIndex("Keycolor")),
                    c.getInt(c.getColumnIndex("Subscribers"))
                    );
        }
        return null;
    }

    public long insertFeed(Feed feed){
        ContentValues values = new ContentValues();
        values.put("Id", feed.getId());
        values.put("Description", feed.getDescription());
        values.put("Icon", feed.getIcon());
        values.put("Header", feed.getHeader());
        values.put("Title", feed.getText());
        values.put("Url", feed.getUrl());
        values.put("Keycolor", feed.getKeycolor());
        values.put("Subscribers", feed.getSubscribers());
        return database.insert("feed", null, values);
    }

    public ArrayList<SubFeed> findAllSubFeed(String parent){
        ArrayList<SubFeed> feeds = new ArrayList<SubFeed>();
        Cursor c = database.rawQuery("Select * from subfeed where Parent = ?",new String[]{parent});
        if (c != null && c.moveToFirst()) {
            while (c.isAfterLast() == false)
            {
                feeds.add(new SubFeed(c.getString(c.getColumnIndex("Id")),
                        c.getString(c.getColumnIndex("Parent")),
                        c.getString(c.getColumnIndex("Title")),
                        c.getString(c.getColumnIndex("Url")),
                        c.getString(c.getColumnIndex("User")),
                        c.getInt(c.getColumnIndex("Score")),
                        c.getInt(c.getColumnIndex("Comments")),
                        c.getInt(c.getColumnIndex("Stickied"))==1?true:false)
                );
                c.moveToNext();
            }
        }
        return feeds;
    }

    public SubFeed findSubFeed(String id){
        Cursor c = database.rawQuery("Select * from subfeed where Id = ?",new String[]{id});
        if (c != null && c.moveToFirst()) {
            return new SubFeed(id,
                    c.getString(c.getColumnIndex("Parent")),
                    c.getString(c.getColumnIndex("Title")),
                    c.getString(c.getColumnIndex("Url")),
                    c.getString(c.getColumnIndex("User")),
                    c.getInt(c.getColumnIndex("Score")),
                    c.getInt(c.getColumnIndex("Comments")),
                    c.getInt(c.getColumnIndex("Stickied"))==1?true:false);
        }
        return null;
    }

    public void updateSubFeed(SubFeed feed){
        SubFeed f = findSubFeed(feed.getId());
        if(f==null){
            insertSubFeed(feed);
        }
        else{
            ContentValues data=new ContentValues();
            data.put("Score",feed.getScore());
            data.put("Comments",feed.getComments());
            data.put("Url",feed.getUrl());
            data.put("Title",feed.getText());
            database.update("subfeed", data, "id='" + feed.getId()+"'", null);
        }
    }

    public long insertSubFeed(SubFeed feed){
        ContentValues values = new ContentValues();
        values.put("Id", feed.getId());
        values.put("Parent", feed.getParentId());
        values.put("Title", feed.getText());
        values.put("Url", feed.getUrl());
        values.put("User", feed.getUser());
        values.put("Comments", feed.getScore());
        values.put("Score", feed.getComments());
        values.put("Stickied", feed.isStickied()?1:0);
        return database.insert("subfeed", null, values);
    }

}