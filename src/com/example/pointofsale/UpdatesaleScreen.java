package com.example.pointofsale;

import java.util.ArrayList;

import dao.InventoryDB;
import domain.SaleListItem;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdatesaleScreen extends Activity {

	ArrayList<SaleListItem> purchaseList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatesale);
		
		Intent newActivity = null;	
		
		newActivity.putParcelableArrayListExtra("PurchaseList",
				purchaseList);
		// Read var from Intent
		Intent intent = getIntent();
		final String MemID = intent.getStringExtra("MemID");
		
		// Show Data
		ShowData(MemID);

		// btnSave (Save)
		final Button save = (Button) findViewById(R.id.btnSave);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// If Save Complete
				if (MemID == purchaseList.get(0).getProductID()) {
					// Open Form ListUpdate
					Intent newActivity = new Intent(UpdatesaleScreen.this,
							InventoryActivity.class);
					startActivity(newActivity);
				}
			}
		});

		// btnCancel (Cancel)
		final Button cancel = (Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Open Form ListUpdate
				Intent newActivity = new Intent(UpdatesaleScreen.this,
						InventoryActivity.class);
				startActivity(newActivity);
			}
		});

	}

	public void ShowData(String MemID) {
		// txtMemberID, txtName, txtTel, txtDesc
		final TextView tItemID = (TextView) findViewById(R.id.txtItemID);
		final EditText tName = (EditText) findViewById(R.id.txtName);
		final EditText tQuantity = (EditText) findViewById(R.id.txtQuantity);
		final EditText tPrice = (EditText) findViewById(R.id.txtPrice);
		
		// new Class DB
		final InventoryDB myDb = new InventoryDB(this);

		// Show Data
		String arrData[] = myDb.SelectData(MemID);
		if (arrData != null) {
			tItemID.setText(arrData[0]);
			tName.setText(arrData[1]);
			tQuantity.setText(arrData[2]);
			tPrice.setText(arrData[3]);
		}

	}
}
