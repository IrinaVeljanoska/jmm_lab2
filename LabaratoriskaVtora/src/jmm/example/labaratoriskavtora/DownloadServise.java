package jmm.example.labaratoriskavtora;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadServise extends Service {

	@Override
	public IBinder onBind(Intent intent) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		TaskDownload task = new TaskDownload(this);
		task.execute(getString(R.string.all_items));

		return super.onStartCommand(intent, flags, startId);
	}

}
