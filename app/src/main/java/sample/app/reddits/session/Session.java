package sample.app.reddits.session;

import android.content.Context;

import sample.app.reddits.utils.ImageLoader;

/**
 * Created by root on 25/09/16.
 */
public class Session {

    private static Session session;
    private static ImageLoader imageLoader;
    private static AppDb db;

    public static Session getInstance(Context c) {
        if(session==null){
            session = new Session();
            session.setImageLoader(new ImageLoader(c));
            session.setDb(new AppDb(c));
        }
        return session;
    }

    public static AppDb getDb() {
        return db;
    }

    private static void setDb(AppDb db) {
        Session.db = db;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private void setImageLoader(ImageLoader imageLoader) {
        Session.imageLoader = imageLoader;
    }

    private Session() {

    }

}
