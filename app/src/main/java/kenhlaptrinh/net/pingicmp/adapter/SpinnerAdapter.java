package kenhlaptrinh.net.pingicmp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kenhlaptrinh.net.pingicmp.R;

public class SpinnerAdapter extends BaseAdapter {

    private int resource;
    private List<String> mData = new ArrayList<>();

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource) {
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.tv_host);
        textView.setText(mData.get(position));
        return convertView;
    }


    public void addItem(ArrayList<String> ls) {
        mData.addAll(ls);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
