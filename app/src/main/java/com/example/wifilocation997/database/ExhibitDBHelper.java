package com.example.wifilocation997.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wifilocation997.entity.Exhibit;

import java.util.ArrayList;
import java.util.List;

public class ExhibitDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "exhibit.db";
    // 商品信息表
    private static final String TABLE_EXHIBITS_INFO = "exhibit";

    private static final int DB_VERSION = 1;
    private static ExhibitDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private ExhibitDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static ExhibitDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ExhibitDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建展品信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_EXHIBITS_INFO +
                "(_exhibit_number INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " exhibit_name VARCHAR NOT NULL," +
                " position VARCHAR NOT NULL," +
                " year VARCHAR NOT NULL," +
                " introduction VARCHAR NOT NULL," +
                "pic_path VARCHAR NOT NULL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 添加多条展品信息
    public void insertGoodsInfos(List<Exhibit> list) {
        // 插入多条记录，要么全部成功，要么全部失败
        try {
            mWDB.beginTransaction();
            for (Exhibit info : list) {
                ContentValues values = new ContentValues();
                values.put("exhibit_name", info.exhibit_name);
                values.put("position", info.position);
                values.put("year", info.year);
                values.put("introduction", info.introduction);
                values.put("pic_path", info.pic_path);
                mWDB.insert(TABLE_EXHIBITS_INFO, null, values);
            }
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    // 查询所有的展品信息
    public List<Exhibit> queryAllGoodsInfo() {
        String sql = "select * from " + TABLE_EXHIBITS_INFO;
        List<Exhibit> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Exhibit info = new Exhibit();
            info.exhibit_number = cursor.getInt(0);
            info.exhibit_name = cursor.getString(1);
            info.position = cursor.getString(2);
            info.year = cursor.getString(3);
            info.introduction = cursor.getString(4);
            info.pic_path = cursor.getString(5);
            list.add(info);
        }
        cursor.close();
        return list;
    }


    // 根据展品ID查询展品信息
    public Exhibit queryGoodsInfoById(int goodsId) {
        Exhibit info = null;
        Cursor cursor = mRDB.query(TABLE_EXHIBITS_INFO, null, "_exhibit_number=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        if (cursor.moveToNext()) {
            info = new Exhibit();
            info.exhibit_number = cursor.getInt(0);
            info.exhibit_name = cursor.getString(1);
            info.position = cursor.getString(2);
            info.year = cursor.getString(3);
            info.introduction = cursor.getString(4);
            info.pic_path = cursor.getString(5);
        }
        return info;
    }

    // 根据展品名称查询展品信息
    public Exhibit queryGoodsInfoByName(String exhibit_name) {
        Exhibit info = null;
        Cursor cursor = mRDB.query(TABLE_EXHIBITS_INFO, null, "_exhibit_name=?", new String[]{String.valueOf(exhibit_name)}, null, null, null);
        if (cursor.moveToNext()) {
            info = new Exhibit();
            info.exhibit_number = cursor.getInt(0);
            info.exhibit_name = cursor.getString(1);
            info.position = cursor.getString(2);
            info.year = cursor.getString(3);
            info.introduction = cursor.getString(4);
            info.pic_path = cursor.getString(5);
        }
        return info;
    }

}