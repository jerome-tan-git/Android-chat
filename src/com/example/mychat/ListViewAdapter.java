package com.example.mychat;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> listItems;
	private LayoutInflater listContainer;

	public ListViewAdapter(Context _context,
			List<Map<String, Object>> _listItems) {
		this.context = _context;
		listContainer = LayoutInflater.from(context);
		this.listItems = _listItems;
	}

	public final class ListItemView { // 自定义控件集合
		public TextView title;
		public TextView info;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();

			convertView = listContainer.inflate(R.layout.list_item, null);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.titleItem);
			listItemView.info = (TextView) convertView
					.findViewById(R.id.infoItem);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		listItemView.title.setText((String) listItems.get(position)
				.get("title"));
		listItemView.info.setText((String) listItems.get(position).get("info"));
		return convertView;
	}

}
