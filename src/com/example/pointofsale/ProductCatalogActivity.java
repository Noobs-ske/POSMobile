package com.example.pointofsale;


	import java.util.ArrayList;
import java.util.HashMap;

import dao.InventoryDB;

	
	

	import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

	public class ProductCatalogActivity extends Activity {

		private ArrayList<HashMap<String, String>> ItemList;
		ArrayList<String> PurchaseList = new ArrayList<String>();
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_catalog);
		  	ShowListData();
		  	Intent intent = getIntent();
		  	PurchaseList = intent.getStringArrayListExtra("PurchaseList");
			//back to inventory button
			final Button btn_Invntory = (Button) findViewById(R.id.button2);
			// Perform action on click
			btn_Invntory.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					Intent newActivity = new Intent(ProductCatalogActivity.this,
							InventoryActivity.class);
					
					startActivity(newActivity);
					finish();

				}
			});
			
			//go to cart button
			final Button btn_Cart = (Button) findViewById(R.id.button6);
			// Perform action on click
			btn_Cart.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					Intent newActivity = new Intent(ProductCatalogActivity.this,SaleActivity
							.class);
					newActivity.putStringArrayListExtra("PurchaseList",
							PurchaseList);
					
					startActivity(newActivity);
					finish();

				}
			});
		}

		

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.catalog_screen, menu);
			return true;
		}
		
//		@Override
//		public void onCreateContextMenu(ContextMenu menu, View v,
//				ContextMenuInfo menuInfo) {
//
//			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//			menu.setHeaderTitle("Command for : "
//					+ ItemList.get(info.position).get("Name").toString());
//			String[] menuItems = getResources().getStringArray(R.array.CmdMenu);
//			int i = 0;
//				menu.add(Menu.NONE, i, i, menuItems[i]);
//			
//
//		}
//
//		@Override
//		public boolean onContextItemSelected(MenuItem item) {
//			final InventoryDB myDb = new InventoryDB(this);
//			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
//					.getMenuInfo();
//			int menuItemIndex = item.getItemId();
//			String[] menuItems = getResources().getStringArray(R.array.CmdMenu);
//			String CmdName = menuItems[menuItemIndex];
//			final String MemID = ItemList.get(info.position).get("ItemID").toString();
//			final String MemName = ItemList.get(info.position).get("Name").toString();
//			final String MemQuantity = ItemList.get(info.position).get("Quantity").toString();
//			final String MemPrice = ItemList.get(info.position).get("Price").toString();
//			// Check Event Command
////			if ("Purchase".equals(CmdName)) {
////				boolean check = true;
////				for (int i = 0; i < PurchaseList.size(); i++) {
////					if (PurchaseList.get(i).equals(MemID)) {
////						check = false;
////						break;
////					}
////
////				}
////				if (check){
////					
////
////			        AlertDialog.Builder alert = new AlertDialog.Builder(this);  
////
////			        alert.setTitle("Quantity");  
////			        alert.setMessage("Please input the number");  
////
////			        // Set an EditText view to get user input   
////			        final EditText inputQuantity = new EditText(this);  
////			        alert.setView(inputQuantity);  
////
////			        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {  
////			        public void onClick(DialogInterface dialog, int whichButton) {  
////			        	
////			        	
////			        	try{
////			            PurchaseQuantity = Integer.parseInt(inputQuantity.getText().toString());
////			           
////			            int n = Integer.parseInt(MemQuantity)- PurchaseQuantity;
////			        
////			            String PurQuantity = PurchaseQuantity+""; 
////			            if(PurchaseQuantity > Integer.parseInt(MemQuantity))
////			            {
////			            	Toast.makeText(getBaseContext(),
////									"Not enough item in stock",
////									Toast.LENGTH_LONG).show();
////			            }
////			            else {       
////			            	PurchaseList.add(MemID);
////
////			            		myDb.InsertData2(MemID, MemName, PurQuantity, MemPrice);
////			            		ShowListData();
////			
////			            	}
////			            		
////			            	
////			        	}
////			        	catch(Exception e){
////			        		Toast.makeText(getBaseContext(),
////									"INPUT THE NUMBER MORON !!",
////									Toast.LENGTH_LONG).show();
////			        	}
////			          }  
////			        }); 
////			        
////			        alert.setNegativeButton("Cancel", null );
////
////			        alert.show();
////					
////			        
////				}
////				else {
////					Toast.makeText(ProductCatalogActivity.this,
////							"You're already Purchase.", Toast.LENGTH_LONG).show();
////				}
////			}
//
//
//				// for Delete Command
////			 else if ("Delete".equals(CmdName)) {
////
//////				DBClass myDb = new DBClass(this);
////
////				long flg = myDb.DeleteData(MemID);
////				if (flg > 0) {
////					Toast.makeText(ProductCatalogActivity.this,
////							"Delete Data Successfully", Toast.LENGTH_LONG).show();
////				} else {
////					Toast.makeText(ProductCatalogActivity.this, "Delete Data Failed.",
////							Toast.LENGTH_LONG).show();
////				}
////
////				// Call Show Data again
////				ShowListData();
////			}
//
//			return true;
//		
//		}
		
		public void ShowListData() {
			final InventoryDB myDb = new InventoryDB(this);
			ItemList = myDb.SelectAllData();

			// listView1
			ListView lisView1 = (ListView) findViewById(R.id.listView1);

			SimpleAdapter sAdap;
			sAdap = new SimpleAdapter(ProductCatalogActivity.this, ItemList,
					R.layout.activity_catalogcolumn, new String[] { "ItemID", "Name",
							 "Price" }, new int[] { R.id.ColItemID,
							R.id.ColName, R.id.TotalPrice });
			lisView1.setAdapter(sAdap);
			registerForContextMenu(lisView1);
		}

	}



	// myDb.InsertData(tItemID.getText().toString(), tName
	//.getText().toString(), tQuantity.getText().toString(), tPrice
	//.getText().toString());

