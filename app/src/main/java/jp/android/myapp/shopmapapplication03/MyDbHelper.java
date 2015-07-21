package jp.android.myapp.shopmapapplication03;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "shopbase.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "myData";

    static final String ID = "_id";

    //お店の名前
    static final String SHOP_NAME = "shop_Name";

    //お店の住所
    static final String SHOP_ADD = "shop_Add";

    //お店のカテゴリ
    static final String SHOP_KIND = "shop_Kind";

    //お店のおすすめ
    static final String SHOP_MENU = "shop_Menu";

    //お店の情報
    static final String SHOP_CONTENTS = "shop_Contents";

    public MyDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "create table " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SHOP_NAME + " TEXT,"
                + SHOP_ADD + " TEXT,"
                + SHOP_KIND + " TEXT,"
                + SHOP_MENU + " TEXT,"
                + SHOP_CONTENTS + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + DATABASE_NAME);
        onCreate(db);

    }

    //データベースから指定されたIDの行を削除
    public void deleteToDB(SQLiteDatabase db,String id){

        db.execSQL("delete from " + TABLE_NAME + " where " + ID + " = '" + id + "'");

    }
}