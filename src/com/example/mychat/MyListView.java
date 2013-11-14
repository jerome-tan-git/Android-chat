package com.example.mychat;

import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class MyListView  extends AdapterView<Adapter>{
	private Adapter adapter;
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Adapter getAdapter() {
		// TODO Auto-generated method stub
		return this.adapter;
	}

	@Override
	public View getSelectedView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAdapter(Adapter _adapter) {
		// TODO Auto-generated method stub
		this.adapter = _adapter;
		
	}

	@Override
	public void setSelection(int position) {
		// TODO Auto-generated method stub
		
	}

}
