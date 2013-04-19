package jmm.example.labaratoriskavtora;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


public class TaskDownload extends AsyncTask<String, Void, List<ModelItem>> 
{
		
	public static final String ITEMS_DOWNLOADED_ACTION = "mk.ukim.finki.jmm.example.labaratoriskavtora.ITEMS_DOWNLOADED_ACTION";
	private Exception exception = null;
	protected Context context;
	
	public TaskDownload(Context context) 
	{
		this.context = context;
	}
	
	@Override
	protected List<ModelItem> doInBackground(String... params) 
	{
		if (params.length < 1) 
		{
			exception = new IllegalArgumentException("At least one argument for the download url expected. ");
			return null;
		}
		else 
		{
			String url = params[0];
			OnContentDownloaded<List<ModelItem>> handler = new OnItemsDownloaded();
			try
			{
				Downloader.getFromUrl(url, handler);
				publishProgress(null);
				return handler.getResult();
			}
			catch (Exception ex)
			{
				exception = ex;
				return null;
			}
		}
	}
	
	@Override
	protected void onPostExecute(List<ModelItem> result)
	{
		super.onPostExecute(result);
		if (exception != null)
		{
			Toast.makeText(context, "Error: " + exception.getMessage(),
			Toast.LENGTH_LONG).show();
		} 
		else 
		{
			DatabaseTV dbtv = new DatabaseTV(context, MainActivity.DEFAULT_LANG);
			dbtv.open();
			
			for (ModelItem item : result) 
			{
				dbtv.insert(item);
			}
			
			dbtv.close();
			Intent intent=new Intent(ITEMS_DOWNLOADED_ACTION);
			context.sendBroadcast(intent);
			
		}
	}

}