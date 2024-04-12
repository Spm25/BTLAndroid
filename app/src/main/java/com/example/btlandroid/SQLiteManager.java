package com.example.btlandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_INVOICE = "invoice";
    private static final String TABLE_INVOICE_PRODUCT = "invoice_product";

    // Common column names
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_INVOICE_ID = "invoice_id";

    // PRODUCT Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMAGE = "image";

    // INVOICE Table - column names
    private static final String KEY_DATE = "date";
    private static final String KEY_INVOICE_PRICE = "invoice_price";
    private static final String KEY_CREATED = "creator";

    // Table Create Statements
    // Product table create statement
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
            + TABLE_PRODUCT + "(" + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT," + KEY_AMOUNT + " INTEGER," + KEY_PRICE + " REAL,"
            + KEY_IMAGE + " TEXT" + ")";

    // Invoice table create statement
    private static final String CREATE_TABLE_INVOICE = "CREATE TABLE "
            + TABLE_INVOICE + "(" + KEY_INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DATE + " TEXT," + KEY_INVOICE_PRICE + " REAL," + KEY_CREATED + " TEXT" + ")";

    // Invoice_Product table create statement
    private static final String CREATE_TABLE_INVOICE_PRODUCT = "CREATE TABLE "
            + TABLE_INVOICE_PRODUCT + "(" + KEY_INVOICE_ID + " INTEGER,"
            + KEY_PRODUCT_ID + " INTEGER,"
            + "PRIMARY KEY (" + KEY_INVOICE_ID + ", " + KEY_PRODUCT_ID + "),"
            + "FOREIGN KEY (" + KEY_INVOICE_ID + ") REFERENCES " + TABLE_INVOICE + "(" + KEY_INVOICE_ID + "),"
            + "FOREIGN KEY (" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT + "(" + KEY_PRODUCT_ID + ")"
            + ")";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_INVOICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);

        // create new tables
        onCreate(db);
    }
    public void addProduct(String name, int amount, double price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_AMOUNT, amount);
        values.put(KEY_PRICE, price);
        values.put(KEY_IMAGE, image);

        // Inserting Row
        long result = db.insert(TABLE_PRODUCT, null, values);
        if(result==-1){
            Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success",Toast.LENGTH_SHORT).show();
        }
        // Closing database connection
        db.close();
    }
    public Cursor readAllProducts(){
        String query = "select * from "+TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
