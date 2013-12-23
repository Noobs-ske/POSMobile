package com.example.pointofsale;


	import java.util.ArrayList;
import java.util.HashMap;
import dao.InventoryDB;
import domain.SaleListItem;

	
	

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
import android.widget.TextView;
import android.widget.Toast;

	public class ProductCatalogActivity extends Activity {

		private ArrayList<HashMap<String, String>> ItemList;
		ArrayList<SaleListItem> purchaseList;
		int PurchaseQuantity = 0;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_catalog);
		  	ShowListData();
		  	Intent intent = this.getIntent();
		  	purchaseList = intent.getParcelableArrayListExtra("PurchaseList");
		  	if (purchaseList == null) purchaseList = new ArrayList<SaleListItem>();
			//back to inventory button
			final Button btn_Invntory = (Button) findViewById(R.id.button2);
			
			// Perform action on click
			btn_Invntory.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					Intent newActivity = new Intent(ProductCatalogActivity.this,
							InventoryActivity.class);
					newActivity.putParcelableArrayListExtra("PurchaseList",
							purchaseList);
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
					newActivity.putParcelableArrayListExtra("PurchaseList",
							purchaseList);
					
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
		
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
				
				final InventoryDB myDb = new InventoryDB(this);
				AdapterView.AdapterContextMenuInfo info =
			            (AdapterView.AdapterContextMenuInfo) menuInfo;
				String ID = ItemList.get(info.position).get("ItemID").toString();
			//	System.out.println(ID);
				final String[] iteminfo = myDb.SelectData(ID);
				
		        AlertDialog.Builder alert = new AlertDialog.Builder(this);  

		        alert.setTitle("Quantity");  
		        alert.setMessage("Please input the number");  

		        // Set an EditText view to get user input   
		        final EditText inputQuantity = new EditText(this);  
		        alert.setView(inputQuantity);  

		        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int whichButton) {  
		        	
		        	
		        	try{
		        	
		        		
		            PurchaseQuantity = Integer.parseInt(inputQuantity.getText().toString());
		            //Create item
	        		SaleListItem product = new SaleListItem(iteminfo[0], iteminfo[1],
	        				PurchaseQuantity, Integer.parseInt(iteminfo[3]));
	        	//	System.out.println(product.getProductName());
	        	//	if(purchaseList == null) System.out.println("Yup");

	        	//First item
	        		if(purchaseList.size() == 0) {
	        			if(PurchaseQuantity<= Integer.parseInt(iteminfo[2]))
	            		{
	        			 purchaseList.add(product); 
		            	}
	        			
	        			else Toast.makeText(getBaseContext(),
								"Not enough item in stock",
								Toast.LENGTH_LONG).show();
	        		}
	        		
	        	//Check if item exists
	        		else for (int i = 0; i < purchaseList.size(); i++)
	            	{
	        			//exists. check value then add if possible.
	            		if(purchaseList.get(i).getProductName().equals(product.getProductName()))
	            			{
	            			if(purchaseList.get(i).getProductQuan() + PurchaseQuantity
	            					<= Integer.parseInt(iteminfo[2]))
	            				{
	            				purchaseList.get(i).setProductQuan(
	            						purchaseList.get(i).getProductQuan() + PurchaseQuantity);
	            				break;
	            				}
	            			 else { Toast.makeText(getBaseContext(),
	     							"Not enough item in stock",
	     							Toast.LENGTH_LONG).show();
	            			 break;}
	            			}
	            	//Does not exist. Add to list.
	            		if(i == purchaseList.size() -1 && PurchaseQuantity<= Integer.parseInt(iteminfo[2]))
	            		{
	        			 purchaseList.add(product); 
	        			 break;
		            	}
	            		
	            		//Not and not enough item
			            else Toast.makeText(getBaseContext(),
								"Not enough item in stock",
								Toast.LENGTH_LONG).show();
	            	}
		            
		        	}
		        	catch(Exception e){
		        		Toast.makeText(getBaseContext(),
								"Please input the number",
								Toast.LENGTH_LONG).show();
		        	}
		          }  
		        }); 
		        
		        alert.setNegativeButton("Cancel", null );

		        alert.show();
		}

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



