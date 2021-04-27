package com.spisoft.spedittexture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

//public class ItemsAdapter extends ArrayAdapter<TextureItem> implements View.OnClickListener{
public class ItemsAdapter extends ArrayAdapter<TextureItem> implements Filterable {

    private ArrayList<TextureItem> dataSet;
    Context mContext;
    private ValueFilter valueFilter;

    public ItemsAdapter(@NonNull Context context, ArrayList<TextureItem> data) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView icon;
        TextView txtTitle;
        TextView txtInfo;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TextureItem dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtInfo = (TextView) convertView.findViewById(R.id.textInfo);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.icon.setImageResource(dataModel.getIcon());
        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtInfo.setText(dataModel.getId());
        return convertView;
    }


    @Override
    public Filter getFilter() {
        if(valueFilter==null) {
            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<TextureItem> tempList = new ArrayList<TextureItem>();
            if(constraint != null && dataSet != null) {
                int length = dataSet.size();
                int i=0;
                while(i<length){
                    if(dataSet.get(i).getTitle().contains(constraint) || dataSet.get(i).getId().contains(constraint))
                        tempList.add(dataSet.get(i));
                    i++;
                }
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet = (ArrayList<TextureItem>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}

