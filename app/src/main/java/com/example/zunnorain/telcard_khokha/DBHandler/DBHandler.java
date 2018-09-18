package com.example.zunnorain.telcard_khokha.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zunnorain.telcard_khokha.Classes.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zunnorain on 06/06/2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    private DBHandler dbHandler;
    private SQLiteDatabase db;

    private static final int DBVersion = 4;
    private static final String DBName="TelCard.db";


    public DBHandler(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       final String query=" CREATE TABLE " + ItemColumns.ItemEntry.TABLE_NAME + " ( " +
        ItemColumns.ItemEntry.COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        ItemColumns.ItemEntry.COLUMN_ITEM_COMPNAME + " TEXT, " +
        ItemColumns.ItemEntry.COLUMN_ITEM_COMPLOGO + " INTEGER, " +
         ItemColumns.ItemEntry.COLUMN_ITEM_QTY + " INTEGER, " +
         ItemColumns.ItemEntry.COLUMN_ITEM_CAT + " TEXT ," +
          ItemColumns.ItemEntry.COLUMN_ITEM_PRICE + " INTEGER " +
        " ); ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemColumns.ItemEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addItem(Item item)
    {
        db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_COMPNAME,item.getComp_name());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_COMPLOGO,item.getComp_logo());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_QTY,item.getQty());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_CAT,item.getCat());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_PRICE,item.getPrice());
        long row = db.insert(ItemColumns.ItemEntry.TABLE_NAME,null,c);
        db.close();
    }

    public void addRestoreItem(Item item)
    {
        db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_ID,item.getId());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_COMPNAME,item.getComp_name());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_COMPLOGO,item.getComp_logo());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_QTY,item.getQty());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_CAT,item.getCat());
        c.put(ItemColumns.ItemEntry.COLUMN_ITEM_PRICE,item.getPrice());
        long row = db.insert(ItemColumns.ItemEntry.TABLE_NAME,null,c);
        db.close();
    }

    public List<Item> fetchAllItems()
    {
        db = getReadableDatabase();
        String[] columns={
                ItemColumns.ItemEntry.COLUMN_ITEM_ID,
                ItemColumns.ItemEntry.COLUMN_ITEM_COMPNAME,
                ItemColumns.ItemEntry.COLUMN_ITEM_COMPLOGO,
                ItemColumns.ItemEntry.COLUMN_ITEM_QTY,
                ItemColumns.ItemEntry.COLUMN_ITEM_CAT,
                ItemColumns.ItemEntry.COLUMN_ITEM_PRICE
        };
        String queryOrder = ItemColumns.ItemEntry.COLUMN_ITEM_ID + " ASC";
        List<Item> list= new ArrayList<>();
        Cursor cursor = db.query(ItemColumns.ItemEntry.TABLE_NAME,columns,null,null,null,null,queryOrder);
        if (cursor.moveToFirst())
        {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ItemColumns.ItemEntry.COLUMN_ITEM_ID))));
                item.setComp_name(cursor.getString(cursor.getColumnIndex(ItemColumns.ItemEntry.COLUMN_ITEM_COMPNAME)));
                item.setComp_logo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ItemColumns.ItemEntry.COLUMN_ITEM_COMPLOGO))));
                item.setQty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ItemColumns.ItemEntry.COLUMN_ITEM_QTY))));
                item.setCat(cursor.getString(cursor.getColumnIndex(ItemColumns.ItemEntry.COLUMN_ITEM_CAT)));
                item.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(ItemColumns.ItemEntry.COLUMN_ITEM_PRICE))));
                list.add(item);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getItemsCount()
    {
        db = getReadableDatabase();
        String[] columns={
                ItemColumns.ItemEntry.COLUMN_ITEM_ID,
                ItemColumns.ItemEntry.COLUMN_ITEM_COMPNAME,
                ItemColumns.ItemEntry.COLUMN_ITEM_COMPLOGO,
                ItemColumns.ItemEntry.COLUMN_ITEM_QTY,
                ItemColumns.ItemEntry.COLUMN_ITEM_CAT,
                ItemColumns.ItemEntry.COLUMN_ITEM_PRICE
        };
        String queryOrder = ItemColumns.ItemEntry.COLUMN_ITEM_ID + " ASC";
        Cursor cursor = db.query(ItemColumns.ItemEntry.TABLE_NAME,columns,null,null,null,null,queryOrder);
        cursor.moveToFirst();
        db.close();
        return cursor.getCount();
    }

    public void deleteItem(long id )
    {
        db=getWritableDatabase();
        db.delete(ItemColumns.ItemEntry.TABLE_NAME,ItemColumns.ItemEntry.COLUMN_ITEM_ID + "=" + id,null);
        db.close();
    }

    public void clearCart()
    {
        db=getWritableDatabase();
        db.delete(ItemColumns.ItemEntry.TABLE_NAME,null,null);
        db.close();
    }
}
