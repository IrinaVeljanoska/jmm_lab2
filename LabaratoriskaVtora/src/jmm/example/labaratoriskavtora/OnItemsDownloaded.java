package jmm.example.labaratoriskavtora;

import java.util.ArrayList;
import java.util.List;

import jmm.example.labaratoriskavtora.ModelItem;

import org.json.JSONArray;
import org.json.JSONObject;

public class OnItemsDownloaded implements OnContentDownloaded<List<ModelItem>>
{

	private List<ModelItem> items = new ArrayList<ModelItem>();

	@Override
	public void onContentDownloaded(String content, int httpStatus)	throws Exception 
	{
		JSONArray jsonItems = new JSONArray(content);

		for (int i = 0; i < jsonItems.length(); i++) 
		{
			JSONObject jObj = (JSONObject) jsonItems.get(i);
			ModelItem item = new ModelItem();
			item.setTitle(jObj.getString("title"));
			item.setId(jObj.getLong("offer_id")); 
			item.setPrice(jObj.getString("price"));
			items.add(item);
		}

	}

	@Override
	public List<ModelItem> getResult()
	{
		return items;
	}

}
