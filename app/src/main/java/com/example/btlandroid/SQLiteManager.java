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
    private static final String KEY_IMAGE = "image_blob";

    // INVOICE Table - column names
    private static final String KEY_DATE = "date";
    private static final String KEY_INVOICE_PRICE = "invoice_price";
    private static final String KEY_CREATED = "creator";

    // Table Create Statements
    // Product table create statement
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
            + TABLE_PRODUCT + "(" + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT," + KEY_AMOUNT + " INTEGER," + KEY_PRICE + " REAL,"
            + KEY_IMAGE + " BLOB" + ")";

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE_PRODUCT);

        // create new tables
        onCreate(db);
    }
    public void addProduct(String name, int amount, double price, byte[] image) {
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
    public void updateProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, product.getId());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_AMOUNT, product.getAmount());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_IMAGE, product.getImage());

        long result = db.update(TABLE_PRODUCT, values, KEY_PRODUCT_ID + "=?", new String[]{String.valueOf(product.getId())});
        if(result==-1){
            Toast.makeText(context, "Failed update",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success update",Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    public void deleteProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            // Xóa tất cả các bản ghi liên quan đến sản phẩm này trong bảng invoice_product
            db.delete(TABLE_INVOICE_PRODUCT, KEY_PRODUCT_ID + "=?", new String[]{String.valueOf(product.getId())});
        }
        catch (Exception e){
            Toast.makeText(context, "Looix khi xoa ",Toast.LENGTH_SHORT);
        }
        // Xóa sản phẩm từ bảng product
        db.delete(TABLE_PRODUCT, KEY_PRODUCT_ID + "=?", new String[]{String.valueOf(product.getId())});

        db.close();
    }
    public Product getProductById(int productId) {
        Product product = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_PRODUCT, null, KEY_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int amount = Integer.parseInt(cursor.getString(2));
                double price = Double.parseDouble(cursor.getString(3));
                byte[] image = cursor.getBlob(4); // Chuyển dữ liệu ảnh từ BLOB thành mảng byte

                product = new Product(id, name, amount, price, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return product;
    }
}
