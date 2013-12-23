package com.example.pointofsale;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;




import dao.InventoryDB;
import dao.SaleReportDB;
import domain.SaleListItem;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SaleActivity extends Activity {

	private ArrayList<HashMap<String, String>> ItemList;
	ArrayList<SaleListItem> purchaseList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		Intent intent = this.getIntent();
	  	purchaseList = intent.getParcelableArrayListExtra("PurchaseList");
	  	
	  	//Show data
	  	ShowAllData();
	  	
		//back to catalog button
				final Button btn_Catalog = (Button) findViewById(R.id.button2);
				// Perform action on click
				btn_Catalog.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						Intent newActivity = new Intent(SaleActivity.this,
								ProductCatalogActivity.class);
						
						newActivity.putParcelableArrayListExtra("PurchaseList",
								purchaseList);
						
						startActivity(newActivity);
						finish();

					}
				});
				
				//checkout button
				final Button btn_Checkout = (Button) findViewById(R.id.button1);
				// Perform action on click
				btn_Checkout.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						//saveTime();
						Checkout();
						
						Intent newActivity = new Intent(SaleActivity.this,
								InventoryActivity.class);
						
						newActivity.putParcelableArrayListExtra("PurchaseList",
								purchaseList);
						
						startActivity(newActivity);
						finish();

					}
				});
				
				
	}

	@SuppressLint("SimpleDateFormat")
	public void saveTime(String name, String id, String quan, String price){
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat asf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String now = asf.format(c.getTime());
		SaleReportDB reportDB = new SaleReportDB(this);
		for(int i=0;i<purchaseList.size();i++){
		reportDB.InsertData(id, now, name, quan, price);
		}
		
	}
	public void Checkout() {
		InventoryDB myDb = new InventoryDB(this);
		for (int i = 0; i < purchaseList.size(); i++) {
			SaleListItem item = purchaseList.get(i);
			String ID = item.getProductID();
			String name = item.getProductName();
			int quan = item.getProductQuan();
			int price = item.getProductPrice();
			String arrDataDB[] = myDb.SelectData(ID);
			//saveTime(arrDataDB[1], arrDataDB[2], arrDataSale[2], "99");
			myDb.reduceQuantity(arrDataDB[0] ,arrDataDB[1] , Integer.parseInt(arrDataDB[2]) 
				 , quan, arrDataDB[3]);
		}
		purchaseList = new ArrayList<SaleListItem>();

	}
	

	public void ShowAllData() {
		double total = 0;
		ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		for (int i = 0; i < purchaseList.size(); i++) {
			SaleListItem a = purchaseList.get(i);
			map = new HashMap<String, String>();
			map.put("ItemID", a.getProductID());
			map.put("Name", a.getProductName());
			map.put("Quantity", Integer.toString(a.getProductQuan()));
			map.put("Price", Integer.toString(a.getProductPrice()));
			total += Double.parseDouble(Integer.toString(a.getProductQuan()))*Double.parseDouble(Integer.toString(a.getProductPrice())) ;
			MyArrList.add(map);
		}
		ItemList = MyArrList;
		
		
		
		// listView1
		ListView lisView1 = (ListView) findViewById(R.id.listView1);

		SimpleAdapter sAdap;
		sAdap = new SimpleAdapter(SaleActivity.this, ItemList,
				R.layout.activity_cartcolumn, new String[] { "ItemID",
						"Name","Quantity"}, new int[] { R.id.ColItemID, R.id.ColName,
						R.id.ColQuantity});
		lisView1.setAdapter(sAdap);
		registerForContextMenu(lisView1);

		// Show total
		TextView text_TotalPrice = (TextView) findViewById(R.id.TotalPrice);
		text_TotalPrice.setText("" + total);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// if (v.getId()==R.id.list) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Command for : "
				+ ItemList.get(info.position).get("Name").toString());
		String[] menuItems = getResources().getStringArray(R.array.CmdMenu);
		menu.add(Menu.NONE, 2, 2, menuItems[2]);
		// }
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.CmdMenu);
		String CmdName = menuItems[menuItemIndex];
		final String MemID = ItemList.get(info.position).get("ItemID").toString();
//		final String MemName = ItemList.get(info.position).get("Name").toString();
//		final String MemQuantity = ItemList.get(info.position).get("Quantity").toString();
//		final String MemPrice = ItemList.get(info.position).get("Price").toString();
		

		if ("Delete".equals(CmdName)) {

			ItemList.remove(ItemList.get(info.position));
			for (int i = 0; i < purchaseList.size(); i++) {
				if (MemID.equals(purchaseList.get(i))) {
					purchaseList.remove(i);
				}
			}
			// Call Show Data again
			ShowAllData();
		}

		return true;
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart_screen, menu);
		return true;
	}

}
