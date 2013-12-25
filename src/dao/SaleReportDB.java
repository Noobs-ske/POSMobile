package dao;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
	
public class SaleReportDB extends SQLiteOpenHelper 
{
	
	// Database Version
	private static final int DATABASE_VERSION = 13;

	// Database Name
	private static final String DATABASE_NAME = "mydatabase2";
	
	// Table Name
	private static final String TABLE_REPORT = "Reports";
	
	public SaleReportDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// Create Table Name
		db.execSQL("CREATE TABLE " + TABLE_REPORT
			//	+ "(ItemID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " (Date TEXT(100),"
				+ " Name TEXT(100),"
				+ " Quantity TEXT(100), " 
				+ " Price TEXT(100));");
		
		Log.d("CREATE TABLE", "Create Table Successfully.");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
		// Re Create on method onCreate
		onCreate(db);
	}
	
	public long InsertData(String strDate, String strName, String strQuantity, String strPrice) {
		// TODO Auto-generated method stub

		try {
			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			/**
			 * for API 11 and above SQLiteStatement insertCmd; String strSQL =
			 * "INSERT INTO " + TABLE_MEMBER +
			 * "(ItemID,Name,Quantity,Price) VALUES (?,?,?)";
			 * 
			 * insertCmd = db.compileStatement(strSQL); insertCmd.bindString(1,
			 * strItemID); insertCmd.bindString(2, strName);
			 * insertCmd.bindString(3, strTel); return
			 * insertCmd.executeInsert();
			 */

			ContentValues Val = new ContentValues();
			//Val.put("ItemID", strItemID);
			Val.put("Date" , strDate);
			Val.put("Name", strName);
			Val.put("Quantity", strQuantity);
			Val.put("Price", strPrice);
			long rows = db.insert(TABLE_REPORT, null, Val);

			db.close();
			return rows; // return rows inserted.

		} catch (Exception e) {
			return -1;
		}

	}
	
	// Show All Data
		public ArrayList<HashMap<String, String>> SelectAllData() {
			// TODO Auto-generated method stub

			try {

				ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> map;

				SQLiteDatabase db;
				db = this.getReadableDatabase(); // Read Data

				String strSQL = "SELECT  * FROM " + TABLE_REPORT;
				Cursor cursor = db.rawQuery(strSQL, null);

				if (cursor != null) {
					if (cursor.moveToFirst()) {
						do {
							map = new HashMap<String, String>();
				//			map.put("ItemID", cursor.getString(0));
							map.put("Date", cursor.getString(0));
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
}