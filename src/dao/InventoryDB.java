package dao;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InventoryDB extends SQLiteOpenHelper {

	public static final String TABLE_INVENTORY = "inventory";
	private static final int DATABASE_VERSION = 13;
	public static final String COLUMN_PRODUCTID = "productid";
	public static final String COLUMN_PRODUCTNAME = "productname";
	public static final String COLUMN_PRODUCTQUAN = "productquan";
	public static final String COLUMN_PRODUCTPRICE = "productprice";
	private static final String DATABASE_NAME = "inventorydb";
	
	//Creation statement
	private static final String DATABASE_CREATE = 
			"create table " + TABLE_INVENTORY + "(" 
					+ COLUMN_PRODUCTID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ COLUMN_PRODUCTNAME + "TEXT,"
					+ COLUMN_PRODUCTQUAN + "INTEGER,"
					+ COLUMN_PRODUCTPRICE + "DOUBLE);" ;
	
	public InventoryDB(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		Log.d("CREATE TABLE", "Create Table Successfully.");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
		// Re Create on method onCreate
		onCreate(db);
	}
	
	public long InsertData(String strID, String strName, String strQuantity, String strPrice)
	{
		try {
			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			ContentValues Val = new ContentValues();
			Val.put("ItemID", strID);
			Val.put("Name", strName);
			Val.put("Quantity", strQuantity);
			Val.put("Price", strPrice);
			long rows = db.insert(TABLE_INVENTORY, null, Val);

			db.close();
			return rows; // return rows inserted.

		} catch (Exception e) {
			return -1;
		}
	}

	public String[] SelectData(String strItemID) {
		// TODO Auto-generated method stub

		try {
			String arrData[] = null;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			Cursor cursor = db.query(false, TABLE_INVENTORY, new String[] { "*" },
					"ItemID=?", new String[] { String.valueOf(strItemID) },
					null, null, null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					/***
					 * 0 = ItemID , 1 = Name
					 * 2 = Quantity , 3 = Price 
					 */
					arrData[0] = cursor.getString(0);
					arrData[1] = cursor.getString(1);
					arrData[2] = cursor.getString(2);
					arrData[3] = cursor.getString(3);
					arrData[4] = cursor.getString(4);
				}
			}
			cursor.close();
			db.close();
			return arrData;

		} catch (Exception e) {
			return null;
		}

	}
	
	public ArrayList<HashMap<String, String>> SelectAllData() {
		// TODO Auto-generated method stub

		try {

			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_INVENTORY;
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();
						map.put("ItemID", cursor.getString(0));
						map.put("Name", cursor.getString(1));
						map.put("Quantity", cursor.getString(2));
						map.put("Price", cursor.getString(3));
						MyArrList.add(map);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return MyArrList;

		} catch (Exception e) {
			return null;
		}

	}

	public long UpdateData(String strItemID, String strName, String strQuantity, String strPrice) {
		// TODO Auto-generated method stub

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			ContentValues Val = new ContentValues();
			Val.put("Name", strName);
			Val.put("Quantity", strQuantity);
			Val.put("Price", strPrice);

			long rows = db.update(TABLE_INVENTORY, Val, " ItemID = ?",
					new String[] { String.valueOf(strItemID) });

			db.close();
			return rows; // return rows updated.

		} catch (Exception e) {
			return -1;
		}

	}

	//Reduce items quantity upon purchase on checkout
	public void reduceQuantity(String ID ,String name , int current , int purchase, String price)
	{
		int currentquan = current;
		int purchasequan = purchase;
		int left = currentquan - purchasequan;
		String left2 = left+"";
		UpdateData(ID, name, left2, price);
	}
	
}
