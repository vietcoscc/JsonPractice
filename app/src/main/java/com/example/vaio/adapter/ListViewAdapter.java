package com.example.vaio.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vaio.jsonpractice.R;
import com.example.vaio.object.ItemObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vaio on 11/21/2016.
 */

public class ListViewAdapter extends ArrayAdapter<ItemObject> {
    private ArrayList<ItemObject> arrItemObject;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, ArrayList<ItemObject> arrItemObject) {
        super(context, android.R.layout.simple_list_item_1, arrItemObject);
        this.arrItemObject = arrItemObject;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrItemObject.size();
    }

    @NonNull
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ItemObject itemObject = arrItemObject.get(position);
        ViewHolder viewHolder;

        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.item_list_view, parent, false);
            viewHolder.ivCountry = (ImageView) v.findViewById(R.id.ivCountry);
            viewHolder.tvMuaTM = (TextView) v.findViewById(R.id.tvMuaTM);
            viewHolder.tvBanTM = (TextView) v.findViewById(R.id.tvBanTM);
            viewHolder.tvMuaCK = (TextView) v.findViewById(R.id.tvMuaCK);
            viewHolder.tvBanCK = (TextView) v.findViewById(R.id.tvBanCK);
            viewHolder.tvType = (TextView) v.findViewById(R.id.tvType);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        Picasso.with(getContext()).load(itemObject.getImageurl()).error(R.drawable.warning).placeholder(R.drawable.loading).into(viewHolder.ivCountry);
        viewHolder.tvMuaTM.setText(itemObject.getMuatienmat());
        viewHolder.tvBanTM.setText(itemObject.getBantienmat());
        viewHolder.tvMuaCK.setText(itemObject.getMuack());
        viewHolder.tvBanCK.setText(itemObject.getBanck());
        viewHolder.tvType.setText(itemObject.getType());

        return v;
    }

    class ViewHolder {
        ImageView ivCountry;
        TextView tvMuaTM;
        TextView tvBanTM;
        TextView tvMuaCK;
        TextView tvBanCK;
        TextView tvType;
    }
}
