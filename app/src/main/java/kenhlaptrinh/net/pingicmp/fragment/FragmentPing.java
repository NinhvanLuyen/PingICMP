package kenhlaptrinh.net.pingicmp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kenhlaptrinh.net.pingicmp.R;
import kenhlaptrinh.net.pingicmp.adapter.HostAdapter;
import kenhlaptrinh.net.pingicmp.adapter.ListviewAdapter;
import kenhlaptrinh.net.pingicmp.model.ItemPing;

/**
 * Created by ninhvanluyen on 9/25/17.
 */

public class FragmentPing extends Fragment {
    private ListviewAdapter listviewAdapter;
    private ArrayList<ItemPing> mData;

    public static Fragment getInstant() {
        return new FragmentPing();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frm_ping, container, false);
        mData = new ArrayList<>();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        listviewAdapter = new ListviewAdapter(R.layout.item_ping);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        final AutoCompleteTextView host = (AutoCompleteTextView) view.findViewById(R.id.host);
        listView.setAdapter(listviewAdapter);
        HostAdapter hostAdapter = new HostAdapter(R.layout.item_host);
        ArrayList<String> data = new ArrayList<>();
        data.add("realtime-staging.squargame.com");
        data.add("google.com");
        data.add("vnexpress.net");
        data.add("facebook.com");
        data.add("vnpet.net");
        hostAdapter.addItem(data);
        host.setAdapter(hostAdapter);
        host.setThreshold(1);
        hostAdapter.setOnSelectListenner(position -> {
            host.setText(position);
            host.dismissDropDown();
        });
        Button fab = (Button) view.findViewById(R.id.ping);
        fab.setOnClickListener(view_ -> {
            listviewAdapter.removeAll();
            Fetch(host.getText().toString());
            getData(host.getText().toString());
        });
        return view;
    }

    private void Fetch(String host) {

        Observable<ItemPing> getData = Observable.create(sub -> {
            Process p;
            p = new ProcessBuilder("sh").redirectErrorStream(true).start();
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("ping -c 3 " + host + '\n');
            os.flush();
// Close the terminal
            os.writeBytes("exit\n");
            os.flush();
// read ping replys
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ttl")) {
                    ItemPing itemPing = new ItemPing();
                    itemPing.setResult(line);
                    String[] lss = line.split(" ");
                    for (String x : lss) {
                        if (x.contains("time=")) {
                            itemPing.setTime(x);
                        }
                        if (x.contains("icmp_seq")) {
                            itemPing.setSeq(x);
                        }
                        if (x.contains(":")) {
                            itemPing.setIp(x);
                        }
                    }
                    Log.e("line", line);
                    Log.e("tostrin", itemPing.toString());
                    sub.onNext(itemPing);
                }
            }
        });
        getData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(itemPing -> listviewAdapter.addPing(itemPing))
                .subscribe();
    }

    private void getData(String host) {
        AsyncTask<String, String, ArrayList<ItemPing>> asyncTask = new AsyncTask<String, String, ArrayList<ItemPing>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<ItemPing> doInBackground(String... params) {
                ArrayList<ItemPing> ls = new ArrayList<>();
                Process p;
                try {
                    p = new ProcessBuilder("sh").redirectErrorStream(true).start();
                    DataOutputStream os = new DataOutputStream(p.getOutputStream());
                    os.writeBytes("ping -c 3 " + params[0] + '\n');
                    os.flush();
                    os.writeBytes("exit\n");
                    os.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("ttl")) {
                            ItemPing itemPing = new ItemPing();
                            itemPing.setResult(line);
                            String[] lss = line.split(" ");
                            for (String x : lss) {
                                if (x.contains("time=")) {
                                    itemPing.setTime(x);
                                }
                                if (x.contains("icmp_seq")) {
                                    itemPing.setSeq(x);
                                }
                                if (x.contains(":")) {
                                    itemPing.setIp(x);
                                }
                            }
                            Log.e("itemPing_toString", itemPing.toString());
                            ls.add(itemPing);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ls;
            }

            @Override
            protected void onPostExecute(ArrayList<ItemPing> itemPings) {
                super.onPostExecute(itemPings);
                Log.e("itemPing_toString","Success");
            }
        };
        asyncTask.execute(host);
    }
}
