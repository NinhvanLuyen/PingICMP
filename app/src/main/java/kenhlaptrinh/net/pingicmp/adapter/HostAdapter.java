package kenhlaptrinh.net.pingicmp.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import kenhlaptrinh.net.pingicmp.databinding.ItemHostBinding;
import kenhlaptrinh.net.pingicmp.model.ItemHostViewHolder;

public class HostAdapter extends BaseAdapter implements Filterable {

    private int resource;
    private List<String> mData = new ArrayList<>();
    private ArrayList<String> suggestions = new ArrayList<>();
    private OnSelectListenner onSelectListenner;

    public void setOnSelectListenner(OnSelectListenner onSelectListenner) {
        this.onSelectListenner = onSelectListenner;
    }

    public HostAdapter(int resource) {
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        }
        ItemHostBinding itemSpinner = DataBindingUtil.inflate(LayoutInflater.from(convertView.getContext()), resource, parent, false);
        ;
        ItemHostViewHolder itemHostViewHolder = new ItemHostViewHolder();
        itemHostViewHolder.host_name.set(suggestions.get(position));
        itemSpinner.setViewmodel(itemHostViewHolder);
        itemSpinner.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListenner.select(suggestions.get(position));
            }
        });
        return itemSpinner.getRoot();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void addItem(ArrayList<String> ls) {
        mData.addAll(ls);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new CustomFilter();
    }


    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();

            if (mData != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).toLowerCase().contains(constraint.toString().toLowerCase())) { // Compare item in original list if it contains constraints.
                        suggestions.add(mData.get(i)); // If TRUE add item in Suggestions.
                    }
                }
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    public interface OnSelectListenner {
        public void select(String position);
    }
}