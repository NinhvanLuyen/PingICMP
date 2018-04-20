package kenhlaptrinh.net.pingicmp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kenhlaptrinh.net.pingicmp.NavigatorButton;
import kenhlaptrinh.net.pingicmp.R;
import kenhlaptrinh.net.pingicmp.adapter.ViewPagerAdapter;
import kenhlaptrinh.net.pingicmp.fragment.FragmentPing;
import kenhlaptrinh.net.pingicmp.fragment.FragmentTerminal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> listfrm = new ArrayList<>();
        listfrm.add(FragmentPing.getInstant());
        listfrm.add(FragmentTerminal.getInstant());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        String[] list = getResources().getStringArray(R.array.title);
        int[] resources = {
                R.drawable.ic_ping,
                R.drawable.ic_terminal,
        };
        NavigatorButton navigatorButton
                = (NavigatorButton) findViewById(R.id.navigator);
        navigatorButton.createView(list, resources, R.color.White, R.color.DarkSeaGreen, R.color.colorPrimary, 0);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), listfrm);
        viewPager.setAdapter(viewPagerAdapter);
        navigatorButton.setViewPagerListener(viewPager);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
