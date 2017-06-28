package com.bjain.pegasus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bjain.pegasus.pojo.cart.CartDataPOJO;
import com.bjain.pegasus.pojo.wishlist.WishListResultPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 04-05-2017.
 */

public class DatabaseHelper {

    DataBaseDef helper;

    public DatabaseHelper(Context context) {
        helper = new DataBaseDef(context);
    }

    public long insertWishListData(WishListResultPOJO wishListResultPOJO){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DataBaseDef.wish_product_id, wishListResultPOJO.getProduct_id()+"");
        contentValues.put(DataBaseDef.wish_product_name, wishListResultPOJO.getProduct_name()+"");
        contentValues.put(DataBaseDef.wish_wishlist_item_id, wishListResultPOJO.getWishlist_item_id()+"");
        contentValues.put(DataBaseDef.wish_customer_id, wishListResultPOJO.getCustomer_id()+"");
        contentValues.put(DataBaseDef.wish_product_sku, wishListResultPOJO.getProduct_sku()+"");
        contentValues.put(DataBaseDef.wish_description, wishListResultPOJO.getDescription()+"");
        contentValues.put(DataBaseDef.wish_quantity, wishListResultPOJO.getQuantity()+"");
        contentValues.put(DataBaseDef.wish_image, wishListResultPOJO.getImage()+"");
        contentValues.put(DataBaseDef.wish_price, wishListResultPOJO.getPrice()+"");

        long id=db.insert(DataBaseDef.TABLE_WISHLIST, null, contentValues);
        db.close();
        return id;
    }


    public List<WishListResultPOJO> getAllWishListPOJOs(){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<WishListResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseDef.ID,
                DataBaseDef.wish_product_id,
                DataBaseDef.wish_product_name,
                DataBaseDef.wish_wishlist_item_id,
                DataBaseDef.wish_customer_id,
                DataBaseDef.wish_product_sku,
                DataBaseDef.wish_description,
                DataBaseDef.wish_quantity,
                DataBaseDef.wish_image,
                DataBaseDef.wish_price
        };
        Cursor cursor=db.query(DataBaseDef.TABLE_WISHLIST, columns, null, null, null, null, null);

        while(cursor.moveToNext()){
            WishListResultPOJO wishListResultPOJO=
                    new WishListResultPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9));



            lst.add(wishListResultPOJO);
        }
        cursor.close();
        db.close();
        return lst;

    }
    public WishListResultPOJO getWishListData(String product_id){
        SQLiteDatabase db=helper.getWritableDatabase();
//        List<WishListResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseDef.ID,
                DataBaseDef.wish_product_id,
                DataBaseDef.wish_product_name,
                DataBaseDef.wish_wishlist_item_id,
                DataBaseDef.wish_customer_id,
                DataBaseDef.wish_product_sku,
                DataBaseDef.wish_description,
                DataBaseDef.wish_quantity,
                DataBaseDef.wish_image,
                DataBaseDef.wish_price
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor=db.query(DataBaseDef.TABLE_WISHLIST, columns, DataBaseDef.wish_product_id+" = "+product_id, null, null, null, null);
        if(cursor.moveToNext()){

            WishListResultPOJO wishListResultPOJO=
                    new WishListResultPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9));
//            lst.add(newUrgentChatResultPOJO);
            return wishListResultPOJO;
        }
        cursor.close();
        db.close();
        return null;
    }

    public int UpdateWishListData(WishListResultPOJO wishListResultPOJO){
        //UPDATE TABLE SET Name='vav' where Name=?
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DataBaseDef.wish_product_id, wishListResultPOJO.getProduct_id()+"");
        contentValues.put(DataBaseDef.wish_product_name, wishListResultPOJO.getProduct_name()+"");
        contentValues.put(DataBaseDef.wish_wishlist_item_id, wishListResultPOJO.getWishlist_item_id()+"");
        contentValues.put(DataBaseDef.wish_customer_id, wishListResultPOJO.getCustomer_id()+"");
        contentValues.put(DataBaseDef.wish_product_sku, wishListResultPOJO.getProduct_sku()+"");
        contentValues.put(DataBaseDef.wish_description, wishListResultPOJO.getDescription()+"");
        contentValues.put(DataBaseDef.wish_quantity, wishListResultPOJO.getQuantity()+"");
        contentValues.put(DataBaseDef.wish_image, wishListResultPOJO.getImage()+"");
        contentValues.put(DataBaseDef.wish_price, wishListResultPOJO.getPrice()+"");


        String[] whereArgs={wishListResultPOJO.getProduct_id()};
        int count=db.update(DataBaseDef.TABLE_WISHLIST,contentValues, DataBaseDef.wish_product_id+" =? ",whereArgs);
        db.close();
        return count;
    }

    public int deleteWishListItem(String product_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={product_id};
        int count=db.delete(DataBaseDef.TABLE_WISHLIST, DataBaseDef.wish_product_id+"=?", whereArgs);
        db.close();
        return count;
    }
    public int deleteWishListItemByItemId(String wishlist_item_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={wishlist_item_id};
        int count=db.delete(DataBaseDef.TABLE_WISHLIST, DataBaseDef.wish_wishlist_item_id+"=?", whereArgs);
        db.close();
        return count;
    }
    public void deleteALLWishListData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from "+ DataBaseDef.TABLE_WISHLIST);
        db.close();
    }

    /*------------------------------------------------------------------------------------
    * ------------------------------------------------------------------------------------
    * ----------------------------------------------------------------------------------*/
    private final String TAG=getClass().getSimpleName();

    public long insertCartData(CartDataPOJO catCartResultPOJO){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DataBaseDef.cart_id, catCartResultPOJO.getCart_id()+"");
        contentValues.put(DataBaseDef.product_id, catCartResultPOJO.getProduct_id()+"");
        contentValues.put(DataBaseDef.product_price, catCartResultPOJO.getProduct_price()+"");
        contentValues.put(DataBaseDef.quantity, catCartResultPOJO.getQuantity()+"");
        contentValues.put(DataBaseDef.product_name, catCartResultPOJO.getProduct_name()+"");
        contentValues.put(DataBaseDef.product_sku, catCartResultPOJO.getProduct_sku()+"");
        contentValues.put(DataBaseDef.product_image, catCartResultPOJO.getProduct_image()+"");

        long id=db.insert(DataBaseDef.TABLE_CART, null, contentValues);
        db.close();
        return id;
    }

    public List<CartDataPOJO> getAllCartData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<CartDataPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseDef.ID,
                DataBaseDef.cart_id,
                DataBaseDef.product_id,
                DataBaseDef.product_price,
                DataBaseDef.quantity,
                DataBaseDef.product_name,
                DataBaseDef.product_sku,
                DataBaseDef.product_image
        };
        Cursor cursor=db.query(DataBaseDef.TABLE_CART, columns, null, null, null, null, null);

        while(cursor.moveToNext()){
            CartDataPOJO cartResultPOJO=
                    new CartDataPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7));



            lst.add(cartResultPOJO);
        }
        cursor.close();
        db.close();
        return lst;

    }
    public CartDataPOJO getCartData(String product_id){
        SQLiteDatabase db=helper.getWritableDatabase();
//        List<WishListResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseDef.ID,
                DataBaseDef.cart_id,
                DataBaseDef.product_id,
                DataBaseDef.product_price,
                DataBaseDef.quantity,
                DataBaseDef.product_name,
                DataBaseDef.product_sku,
                DataBaseDef.product_image
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor=db.query(DataBaseDef.TABLE_CART, columns, DataBaseDef.product_id+" = "+product_id, null, null, null, null);
        if(cursor.moveToNext()){

            CartDataPOJO cartResultPOJO=
                    new CartDataPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7));
//            lst.add(newUrgentChatResultPOJO);
            return cartResultPOJO;
        }
        cursor.close();
        db.close();
        return null;
    }

    public int UpdateCartData(CartDataPOJO catCartResultPOJO){
        //UPDATE TABLE SET Name='vav' where Name=?
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DataBaseDef.cart_id, catCartResultPOJO.getCart_id()+"");
        contentValues.put(DataBaseDef.product_id, catCartResultPOJO.getProduct_id()+"");
        contentValues.put(DataBaseDef.product_price, catCartResultPOJO.getProduct_price()+"");
        contentValues.put(DataBaseDef.quantity, catCartResultPOJO.getQuantity()+"");
        contentValues.put(DataBaseDef.product_name, catCartResultPOJO.getProduct_name()+"");
        contentValues.put(DataBaseDef.product_sku, catCartResultPOJO.getProduct_sku()+"");
        contentValues.put(DataBaseDef.product_image, catCartResultPOJO.getProduct_image()+"");


        String[] whereArgs={catCartResultPOJO.getCart_id()};
        int count=db.update(DataBaseDef.TABLE_CART,contentValues, DataBaseDef.cart_id+" =? ",whereArgs);
        db.close();
        return count;
    }

    public int deleteCartData(String cart_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={cart_id};
        int count=db.delete(DataBaseDef.TABLE_CART, DataBaseDef.cart_id+"=?", whereArgs);
        db.close();
        return count;
    }
    public void deleteAllCartItems(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from "+ DataBaseDef.TABLE_CART);
        db.close();
    }



    static class DataBaseDef extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "pegasus";

        //table names
        private static final String TABLE_WISHLIST = "wishlisttable";
        private static final String TABLE_CART = "carttable";


        private static final int DATABASE_VERSION = 2;

//        //columns for the ItemData
        private static final String ID = "_id";


        private static final String cart_id = "cart_id";
        private static final String product_id = "product_id";
        private static final String product_price= "product_price";
        private static final String quantity = "quantity";
        private static final String product_name = "product_name";
        private static final String product_sku = "product_sku";
        private static final String product_image = "product_image";


        private static final String wish_product_id = "wish_product_id";
        private static final String wish_product_name = "wish_product_name";
        private static final String wish_wishlist_item_id= "wish_wishlist_item_id";
        private static final String wish_customer_id = "wish_customer_id";
        private static final String wish_product_sku = "wish_product_sku";
        private static final String wish_description = "wish_description";
        private static final String wish_quantity = "wish_quantity";
        private static final String wish_image = "wish_image";
        private static final String wish_price = "wish_price";






        private static final String CREATE_WISHLIST_TABLE = "CREATE TABLE " + TABLE_WISHLIST + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + wish_product_id + " VARCHAR(255), "
                + wish_product_name + " VARCHAR(255), "
                + wish_wishlist_item_id + " varchar(255), "
                + wish_customer_id + " TEXT, "
                + wish_product_sku + " VARCHAR(255), "
                + wish_description + " VARCHAR(255), "
                + wish_quantity + " VARCHAR(255), "
                + wish_image + " VARCHAR(255),  "
                + wish_price + " VARCHAR(255) "
                + ");";

        private static final String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + cart_id + " VARCHAR(255), "
                + product_id + " VARCHAR(255), "
                + product_price + " varchar(255), "
                + quantity + " TEXT, "
                + product_name + " VARCHAR(255), "
                + product_sku + " VARCHAR(255), "
                + product_image + " VARCHAR(255) "
                + ");";

        private static final String DROP_WISHLIST_TABLE = "DROP TABLE IF EXISTS " + TABLE_WISHLIST;
        private static final String DROP_CART_TABLE = "DROP TABLE IF EXISTS " + TABLE_CART;


        private Context context;


        public DataBaseDef(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try {

                db.execSQL(CREATE_WISHLIST_TABLE);
                db.execSQL(CREATE_CART_TABLE);

//                Toast.makeText(context, "database called", Toast.LENGTH_SHORT).show();
                Log.d("sunil", "database called");
            } catch (Exception e) {
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil", e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            try {
                db.execSQL(DROP_WISHLIST_TABLE);
                db.execSQL(DROP_CART_TABLE);

//                Toast.makeText(context, "database droped", Toast.LENGTH_SHORT).show();
                Log.d("sunil", "database droped");
                onCreate(db);
            } catch (Exception e) {
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil", e.toString());
            }
        }
    }
}
