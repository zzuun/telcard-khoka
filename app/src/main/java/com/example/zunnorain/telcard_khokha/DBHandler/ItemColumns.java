package com.example.zunnorain.telcard_khokha.DBHandler;

import android.provider.BaseColumns;

/**
 * Created by Zunnorain on 08/06/2018.
 */

public class ItemColumns {
    public static final class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME="items";
        public static final String COLUMN_ITEM_ID = "id";
        public static final String COLUMN_ITEM_COMPLOGO = "comp_logo";
        public static final String COLUMN_ITEM_COMPNAME = "comp_name";
        public static final String COLUMN_ITEM_QTY = "qty";
        public static final String COLUMN_ITEM_CAT = "cat";
        public static final String COLUMN_ITEM_PRICE= "price";
    }
}
