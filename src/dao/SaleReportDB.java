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

	private static final int DATABASE_VERSION = 13;
	static final String dbName = "SaleReportDB";
	static final String reportTable = "SaleReport";
	static final String colProductSaleDate = "ProductSaleDate";
	static final String colProductID = "ProductID";
	static final String colProductName = "ProductName";
	static final String colProductPrice = "ProductPrice";
	static final String colProductSoldQuantity = "ProductSoldQuantity";
	static final String colProductTotalPrice = "ProductTotalPrice";
	
	public SaleReportDB(Context context) {
		super(context, dbName, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE" + reportTable +
				"(" + colProductSaleDate + " TEXT, "
				+ colProductID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colProductName + " TEXT, " + colProductPrice + " DOUBLE, "
				+ colProductSoldQuantity + " INTEGER" + colProductTotalPrice + " DOUBLE)");
		
		Log.d("CREATE TABLE", "Create Table Successfully.");
	}

	public long InsertData( String strID, String strSaleDate, String strName, String strPrice, String strQuantity) {
	
		try 
		{
			SQLiteDatabase db;
			db = this.getWritableDatabase(); 
			double price =  Double.parseDouble(strPrice);
			int quan = Integer.parseInt(strQuantity);
			String line = Double.toString(quan*price);
			ContentValues Val = new ContentValues();
			Val.put("ProductID", strID);
			Val.put("ProductSaleDate",strSaleDate);
			Val.put("ProductName", strName);
			Val.put("Price", strPrice);
			Val.put("ProductSoldQuantity",strQuantity);
			Val.put("ProductTotalPrice", line);
			
			long rows = db.insert(dbName, null, Val);

			db.close();
			return rows;
		}
		catch (Exception e) {
			return -1;
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + dbName);
		
		onCreate(db);
		
	}
	
	@SuppressLint("NewApi")
	public String[] SelectData(String strItemID) {
		
		try 
		{
			String arrData[] = null;

			SQLiteDatabase db;
			db = this.getReadableDatabase();
			
			Cursor cursor = db.query(false, dbName, new String[] { "*" },
					"ProductID=?", new String[] { String.valueOf(strItemID) },
					null, null, null, null,null);
			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					/***
					 * 0 = ID , 1 = Date
					 * 2 = Name , 3 = Price 
					 * 4 = Quantity, 5 = LinePrice
					 */
					arrData[0] = cursor.getString(0);
					arrData[1] = cursor.getString(1);
					arrData[2] = cursor.getString(2);
					arrData[3] = cursor.getString(3);
					arrData[4] = cursor.getString(4);
					arrData[5] = cursor.getString(5);
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
			
			String strSQL = "SELECT  * FROM " + reportTable;
			Cursor cursor = db.rawQuery(strSQL, null);
			
			if (cursor != null) 
			{
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();
						map.put("ProductID", cursor.getString(0));
						map.put("ProductSaleDate", cursor.getString(1));
						map.put("ProductName", cursor.getString(2));
						map.put("ProductSoldQuantity", cursor.getString(3));
						map.put("ProductPrice", cursor.getString(4));
						map.put("ProductLinePrice", cursor.getString(5));
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