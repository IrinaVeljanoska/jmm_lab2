package jmm.example.labaratoriskavtora;

import java.util.List;
import jmm.example.labaratoriskavtora.Downloader;
import jmm.example.labaratoriskavtora.OnContentDownloaded;
import jmm.example.labaratoriskavtora.OnItemsDownloaded;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class TaskDownloadWithProgress extends AsyncTask<String, Void, List<ModelItem>> {

	private Exception exception = null;
	private ProgressDialog loadingDialog;
	private Adapter adapter;
	protected Context context;
	Handler handler;

	public TaskDownloadWithProgress(Context context, Adapter adapter) 
	{
		this.context = context;
		this.adapter = adapter;
		handler = new Handler();
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
				ex.printStackTrace();
				return null;
			}
		}
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();

		createDialog();
	}
	

	@Override
	protected void onPostExecute(List<ModelItem> result)
	{
		super.onPostExecute(result);
		if (exception != null)
		{
			Toast.makeText(context, "Error: " + exception.getMessage(),	Toast.LENGTH_LONG).show();
		} 
		else 
		{
			adapter.clear();
			adapter.addAll(result);
		}
		dismiss();
	}

	private void createDialog() 
	{
		loadingDialog = new ProgressDialog(context);
		//loadingDialog.setTitle(context.getResources().getString(R.string.download_title));
	//	loadingDialog.setMessage(context.getResources().getString(R.string.download_description));
		loadingDialog.setIndeterminate(true);
		loadingDialog.setCancelable(false);
	}

	public void dismiss()
	{
		if (loadingDialog != null && loadingDialog.isShowing()) 
		{
			loadingDialog.dismiss();
		}
	}

}
