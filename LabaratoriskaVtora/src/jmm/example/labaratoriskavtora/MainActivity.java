package jmm.example.labaratoriskavtora;

import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	public static final String DEFAULT_LANG = "en";
		
	private ListView mTodoItemsList;
	private DatabaseTV dbtv;
	private Adapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadViews();
		initList();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		dbtv = new DatabaseTV(this, DEFAULT_LANG);
		dbtv.open();
		loadData();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		dbtv.close();
	}
	
	private void loadData() 
	{	
		List<ModelItem> res = dbtv.getAllItems();
		mAdapter.clear();
		mAdapter.addAll(res);
	}
	
	/**
	* Inflates the views from the xml layout
	*/
	
	private void loadViews() 
	{
		mTodoItemsList = (ListView) findViewById(R.id.listView1);
	}
	
	private void initList() 
	{
		mAdapter = new Adapter(this);
		mTodoItemsList.setAdapter(mAdapter);
		mTodoItemsList.setOnItemClickListener(mAdapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(1, 1, 1, "Refresh");
		menu.add(1, 2, 2, "Refresh with service");
		return true;
	}
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
*/
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == 1) 
		{
			if (checkConnection()) 
			{
				TaskDownloadWithProgress task = new TaskDownloadWithProgress(this, mAdapter);
				task.execute(getString(R.string.all_items));
				
				return true;
			}
			
		} 
		else if (item.getItemId() == 2) 
		{
			if (checkConnection()) 
			{
				createDialog();
				IntentFilter filter = new IntentFilter(
				TaskDownload.ITEMS_DOWNLOADED_ACTION);
				registerReceiver(new OnDownloadRefreshReceiver(), filter);
				//startService(new Intent(this, DownloadService.class));
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkConnection() 
	{
		ConnectivityManager connectivityMannager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityMannager.getActiveNetworkInfo();
		if (netInfo == null)
		{
			showSettingsAlert();
			return false;
		} 
		else
		{
			return true;
		}
	}
	
	public void showSettingsAlert() 
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		// Setting Dialog Title
		alertDialog.setTitle("Internet settings");
		
		// Setting Dialog Message
		alertDialog.setMessage("No active connection. Do you want to go to settings menu?");
		
		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface promptDialog, int which) 
			{
				Intent intent = new Intent(
				Settings.ACTION_WIFI_SETTINGS);
				MainActivity.this.startActivity(intent);
			}
		});
		
		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",	new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface promptDialog, int which) 
			{
				promptDialog.dismiss();
			}
		});
		
		// Showing Alert Message
		alertDialog.show();
	}
	
	private ProgressDialog loadingDialog;
	
	private void createDialog() 
	{
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle(this.getResources().getString(R.string.download_title));
		loadingDialog.setMessage(this.getResources().getString(R.string.download_description));
		loadingDialog.setIndeterminate(true);
		loadingDialog.setCancelable(false);
	}
	
	/*public void addTodoItem(View view)
	{
		
		ModelItem item = new ModelItem(mItemName.getText().toString(), false,
		DatePickerUtils.getDate(mItemDueDate));
		
		mAdapter.add(item);
		dbtv.insert(item);
	}
	*/
	class OnDownloadRefreshReceiver extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
		
			loadData();
			
			if (loadingDialog != null && loadingDialog.isShowing())
			{
				loadingDialog.dismiss();
			}
			MainActivity.this.unregisterReceiver(this);
		
		}
	}


}
