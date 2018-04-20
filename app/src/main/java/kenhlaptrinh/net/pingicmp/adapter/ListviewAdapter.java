package kenhlaptrinh.net.pingicmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import kenhlaptrinh.net.pingicmp.model.ItemPing;
import kenhlaptrinh.net.pingicmp.R;

/**
 * Created by ninhvanluyen on 9/19/17.
 */

public class ListviewAdapter extends BaseAdapter {

    private int resource;
    private LinkedList<ItemPing> m_Songs;


    public ListviewAdapter(int resource) {
        this.resource = resource;
        this.m_Songs = new LinkedList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv_result;
        TextView tv_seq;
        TextView tv_timeduration;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        }
        tv_result = (TextView) convertView.findViewById(R.id.tv_result);
        tv_seq = (TextView) convertView.findViewById(R.id.tv_seq);
        tv_timeduration = (TextView) convertView.findViewById(R.id.time);
        tv_result.setText(m_Songs.get(position).getIp());
        tv_seq.setText(m_Songs.get(position).getSeq());
        tv_timeduration.setText(m_Songs.get(position).getTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lam j do o day
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return m_Songs.size();
    }

    public void addPings(ArrayList<ItemPing> itemPings) {
        m_Songs.addAll(itemPings);
        notifyDataSetChanged();
    }

    public void addPing(ItemPing itemPing) {
        m_Songs.add(itemPing);
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (m_Songs.size() > 0) {
            m_Songs.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}