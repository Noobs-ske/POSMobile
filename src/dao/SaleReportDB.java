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
	private static final String DATABASE_NAME2 = "mydatabase2";

	// Table Name
	private static final String TABLE_REPORT = "Reports";
	
	public SaleReportDB(Context context) {
		super(context, DATABASE_NAME2, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE " + TABLE_REPORT
				+ "(ItemID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " Date TEXT(100),"
				+ " Name TEXT(100),"
				+ " Quantity TEXT(100), " 
				+ " Price TEXT(100));");
		
		Log.d("CREATE TABLE", "Create Table Successfully.");
	}

	public long InsertDataReport(String strID, String strSaleDate,  String strName, String strQuantity, String strPrice) {
	
		try 
		{
			SQLiteDatabase db;
			db = this.getWritableDatabase(); 
			ContentValues Val = new ContentValues();
			
			Val.put("Date", strSaleDate);
			Val.put("ItemID",strID);
			Val.put("Name", strName);
			Val.put("Quantity",strQuantity);
			Val.put("Price", strPrice);
			
			long rows = db.insert(DATABASE_NAME2, null, Val);

			db.close();
			return rows;
		}
		catch (Exception e) {
			return -1;
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME2);
		
		onCreate(db);
		
	}
	
	@SuppressLint("NewApi")
	public String[] SelectReportData(String strItemID) {
		
		try 
		{
			String arrData[] = null;

			SQLiteDatabase db;
			db = this.getReadableDatabase();
			
			Cursor cursor = db.query(false, DATABASE_NAME2, new String[] { "*" },
					"ProductID=?", new String[] { String.valueOf(strItemID) },
					null, null, null, null,null);
			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					/***
					 * 0 = Date , 1 = ID
					 * 2 = Name , 3 = Quantity 
					 * 4 = Price
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
					
		} catch (Exception e) 
			{
			return null;
			}
		
	}
	
	public ArrayList<HashMap<String, String>> SelectAllData()
	{
		try {

			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;
			SQLiteDatabase db;
			db = this.getReadableDatabase();
			
			String strSQL = "SELECT  * FROM " + TABLE_REPORT;
			Cursor cursor = db.rawQuery(strSQL, null);
			
			if (cursor != null) 
			{
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();
						map.put("Date", cursor.getString(0));
						map.put("ItemID", cursor.getString(1));
						map.put("Name", cursor.getString(2));
						map.put("Quantity", cursor.getString(3));
						map.put("Price", cursor.getString(4));
						MyArrList.add(map);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return MyArrList;
			
		}catch (Exception e)
			{
				return null;
			}

	}
	
//	public long UpdateData(String strID, String strSaleDate, String strName, String strPrice, String strQuantity) 
//	{
//		try 
//		{
//
//			SQLiteDatabase db;
//			db = this.getWritableDatabase();
//			ContentValues Val = new ContentValues();
//			
//		}
//	}
}