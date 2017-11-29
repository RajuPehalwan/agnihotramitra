package app.gahomatherapy.agnihotramitra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by tomer on 8/4/2017.
 */

class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AgnihotraDB";
    private  String DB_TABLE1 = "Location";
    private static final int DB_VER = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_SUNRISE = "sunrise";
    private static final String KEY_SUNSET = "sunset";
    private final Context mycontext;

    public DBhelper(Context context, int loc) {
        super(context, DB_NAME, null, DB_VER);
        mycontext = context;
        DB_TABLE1 = DB_TABLE1+loc;
       }

    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATE_CONTACTS_TABLE1 = "CREATE TABLE IF NOT EXISTS " + "Location1" + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_SUNSET + " TEXT,"
                    + KEY_SUNRISE + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE1);
            String CREATE_CONTACTS_TABLE2 = "CREATE TABLE IF NOT EXISTS " + "Location2" + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_SUNSET + " TEXT,"
                    + KEY_SUNRISE + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE1);
            String CREATE_CONTACTS_TABLE3 = "CREATE TABLE IF NOT EXISTS " + "Location3" + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_SUNSET + " TEXT,"
                    + KEY_SUNRISE + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE1);
        db.execSQL(CREATE_CONTACTS_TABLE2);
        db.execSQL(CREATE_CONTACTS_TABLE3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE1);
       // db.execSQL("DROP TABLE IF EXISTS " + "Location2");
      //  db.execSQL("DROP TABLE IF EXISTS " + "Location3");
        // Create tables again
        onCreate(db);
    }


  public void addDate(Entrydate dateentry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, dateentry.getDate());
        values.put(KEY_SUNRISE, dateentry.getSunrise());
        values.put(KEY_SUNSET, dateentry.getSunset());

        // Inserting Row
        db.insert(DB_TABLE1, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection   -- INEFFECIENT entry for each date ....can be improved.
    }

  public String getlastdate()
     {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlquery="SELECT * FROM " + DB_TABLE1;


        Cursor cursor = db.rawQuery(sqlquery,null);
        Entrydate entrydate=null;
        if (cursor.getCount()>0) {
            cursor.moveToLast();
            entrydate = new Entrydate(cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));

        }
        else
        {
            Toast.makeText(mycontext,"Date not found ! ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
         db.close();

         if(entrydate!=null)
        return entrydate.getDate();
         else
             return null;

    }

    public Entrydate getDate (String datetosearch) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE1, new String[] { KEY_ID,
                        KEY_DATE, KEY_SUNRISE,KEY_SUNSET }, KEY_DATE + "=?",
                new String[] { datetosearch }, null, null, null, null);
        Entrydate entrydate=null;
       if (cursor.getCount()>0) {
            cursor.moveToFirst();
            entrydate = new Entrydate(cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));

        }
        else
        {
            Toast.makeText(mycontext," Oops ! Data not found for date. Try updating database. ", Toast.LENGTH_SHORT).show();
        }
        // return contact
        cursor.close();
        db.close();
        return entrydate;
    }
    public void deleteall()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Location1");
        db.execSQL("delete from Location2");
        db.execSQL("delete from Location3");
        db.close();
    }

    public void deleteallrecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DB_TABLE1);
        db.close();
    }



    }