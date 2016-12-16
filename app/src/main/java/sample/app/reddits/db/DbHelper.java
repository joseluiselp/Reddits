package sample.app.reddits.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reddits";
    private static final int DATABASE_VERSION = 2;

    //Feed table
    private static final String FEED_TABLE_NAME = "feed";
    private static final String FEED_TABLE_CREATE =
            "CREATE TABLE " + FEED_TABLE_NAME + " (" +
                    "Id" + " TEXT PRIMARY KEY, " +
                    "Icon" + " TEXT, " +
                    "Header" + " TEXT, " +
                    "Title" + " TEXT, " +
                    "Url" + " TEXT, " +
                    "Description" + " TEXT, " +
                    "Subscribers" + " INTEGER, " +
                    "Keycolor" + " TEXT);";

    //Subfeed table
    private static final String SUBFEED_TABLE_NAME = "subfeed";
    private static final String SUBFEED_TABLE_CREATE =
            "CREATE TABLE " + SUBFEED_TABLE_NAME + " (" +
                    "Id" + " TEXT PRIMARY KEY, " +
                    "Parent" + " TEXT, " +
                    "Title" + " TEXT, " +
                    "Url" + " TEXT, " +
                    "User" + " TEXT, " +
                    "Score" + " INTEGER, " +
                    "Comments" + " INTEGER, " +
                    "Stickied" + " INTEGER);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FEED_TABLE_CREATE);
        db.execSQL(SUBFEED_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Not necessary
    }
}
