package com.example.monster2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BlockAdapter extends ArrayAdapter<Integer> {
    private ArrayList<Integer> mList;
    private Context mContext;


    public BlockAdapter(@NonNull Context context, int resource, ArrayList<Integer> mList) {
        super(context, resource);
        this.mList = mList;
        this.mContext = context;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyViewHolder myViewHolder;
        int value = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_block, null);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.block.setTextBlock(value);

        return convertView;
    }

    class MyViewHolder {
        Block block;
        public MyViewHolder(View view) {
            block = view.findViewById(R.id.item);
        }
    }

}
