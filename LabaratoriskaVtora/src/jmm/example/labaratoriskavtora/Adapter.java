package jmm.example.labaratoriskavtora;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Adapter extends BaseAdapter  implements OnItemClickListener 
{
	
	private List<ModelItem> items;
	private Context ctx;
	private LayoutInflater inflater;
	
	public Adapter(Context ctx)
	{
		items = new ArrayList<ModelItem>();
		this.ctx = ctx;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public Adapter(List<ModelItem> items, Context ctx)
	{
		this.items = items;
		this.ctx = ctx;
		inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() 
	{
		return items.size();
	}
	
	
	@Override
	public Object getItem(int position)
	{
		return items.get(position);
	}
	
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
		
	class TodoHoler
	{
		public RelativeLayout itemLayout;
		public TextView name;
		public TextView price;
		public TextView dueDate;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ModelItem item = items.get(position);
		TodoHoler holder = null;
		if (convertView == null)
		{
			holder = new TodoHoler();
			//holder.itemLayout = (RelativeLayout) inflater.inflate(R.layout.item_todo, null);
	
		//	holder.name = (TextView) holder.itemLayout.findViewById(R.id.todoName);
		//	holder.price = (TextView) holder.itemLayout.findViewById(R.id.todoPrice);
		//	holder.dueDate = (TextView) holder.itemLayout.findViewById(R.id.todoDate);
			convertView = holder.itemLayout;
			convertView.setTag(holder);
		}
	
		holder = (TodoHoler) convertView.getTag();
	
		holder.name.setText(item.getTitle());
		holder.dueDate.setText(item.getDueDate().toString());
	
		return convertView;
	}
	
	
	public void add(ModelItem item) 
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<ModelItem> items)
	{
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	
	public void clear()
	{
		items.clear();
		notifyDataSetInvalidated();
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) 
	{
		notifyDataSetChanged();
	}

}