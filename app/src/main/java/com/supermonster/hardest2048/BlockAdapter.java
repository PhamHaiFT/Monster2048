package com.supermonster.hardest2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monster2048.R;

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

    public void clearData(){
        mList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BlockViewHolder blockViewHolder;
        int value = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_block, null);
            blockViewHolder = new BlockViewHolder(convertView);
            convertView.setTag(blockViewHolder);
        } else {
            blockViewHolder = (BlockViewHolder) convertView.getTag();
        }
        blockViewHolder.block.setTextBlock(value);

        return convertView;
    }

    class BlockViewHolder {
        Block block;
        public BlockViewHolder(View view) {
            block = view.findViewById(R.id.item);
        }
    }

    public ArrayList<Integer> getData(){
        return mList;
    }
}
