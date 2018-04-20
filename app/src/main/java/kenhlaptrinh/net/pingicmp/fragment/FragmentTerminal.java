package kenhlaptrinh.net.pingicmp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kenhlaptrinh.net.pingicmp.R;
import kenhlaptrinh.net.pingicmp.adapter.HostAdapter;
import kenhlaptrinh.net.pingicmp.adapter.ListviewAdapter;
import kenhlaptrinh.net.pingicmp.model.ItemPing;

/**
 * Created by ninhvanluyen on 9/25/17.
 */

public class FragmentTerminal extends Fragment {
    //    private ListviewAdapter listviewAdapter;
    private ArrayList<ItemPing> mData;
    private TextView console;
    private ScrollView mScrollView;
    private Button btn_stop;
    private Observable<String> getData;
    private boolean isStop;

    public static Fragment getInstant() {
        return new FragmentTerminal();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frm_terminal, container, false);
        console = (TextView) view.findViewById(R.id.console);
        btn_stop = (Button) view.findViewById(R.id.btn_stop);
        mScrollView = view.findViewById(R.id.scrollView);
        console.setMovementMethod(new ScrollingMovementMethod());
        mData = new ArrayList<>();
        final AutoCompleteTextView host = (AutoCompleteTextView) view.findViewById(R.id.host);
        HostAdapter hostAdapter = new HostAdapter(R.layout.item_host);
        ArrayList<String> data = new ArrayList<>();
        data.add("");
        data.add("ping google.com");
        data.add("ping vnexpress.net");
        data.add("ping facebook.com");
        data.add("ping vnpet.net");
        data.add("ls");
        data.add("pwd");
        data.add("top");
        data.add("whois vnexpress.net");
        data.add("whois google.com");
        hostAdapter.addItem(data);
        host.setAdapter(hostAdapter);
        host.setThreshold(1);
        hostAdapter.setOnSelectListenner(position -> {
            host.setText(position);
            host.dismissDropDown();
        });
        Button fab = (Button) view.findViewById(R.id.ping);
        Button clear = (Button) view.findViewById(R.id.clear);
        fab.setOnClickListener(view_ -> {
            if (host.getText().toString().trim().equals("clear")) {
                console.setText("");
            } else {
                Fetch(host.getText().toString());
            }
        });
        btn_stop.setOnClickListener(view1 -> {
            isStop = true;
        });
        clear.setOnClickListener(view12 -> console.setText(""));
        return view;
    }

    private void Fetch(String host) {
        isStop = false;
        getData = Observable.create(sub -> {
            Process p;
            p = new ProcessBuilder("sh").redirectErrorStream(true).start();
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(host + '\n');
            os.flush();
// Close the terminal
            os.writeBytes("exit\n");
            os.flush();
// read ping replys
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            while ((line = reader.readLine()) != null) {
                if (isStop) {
                    sub.onComplete();
                    break;
                } else {

                    String text= getString(R.string.text,simpleDateFormat.format(GregorianCalendar.getInstance().getTime()) ,line);
                    CharSequence styledText = Html.fromHtml(text);
                    sub.onNext(styledText.toString());
                }
            }
        });
        getData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(this::showData)
                .subscribe();
    }

    private void showData(String text) {
        console.setText(console.getText().toString() + "\n\n" + text);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mScrollView.post(() -> {
//                mScrollView.smoothScrollTo(0, console.getBottom());
            mScrollView.fullScroll(View.FOCUS_DOWN);
        });
    }
}
