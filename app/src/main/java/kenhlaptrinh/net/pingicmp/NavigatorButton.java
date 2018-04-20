package kenhlaptrinh.net.pingicmp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ninhvanluyen on 9/22/17.
 */

public class NavigatorButton extends LinearLayout {
    private Context context;
    private ArrayList<LinearLayout> views;
    private ViewPager viewPager;
    private int[] resource;
    private int color_not_seleted;
    private int color_selected;

    public NavigatorButton(Context context) {
        super(context);
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(10.0F);
        }
    }

    public NavigatorButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(10.0F);
        }
    }

    /**
     * @param titles                       is title of button
     * @param resources                    is icon drawable of button
     * @param resource_color_seleted       : is color when button is selected
     * @param resource_color_not_selected: is color when button is not selected
     * @param background                   :is background of navigation button
     * @param current                      : is position default you want selected start is 0
     */

    public void createView(String[] titles, int[] resources, int resource_color_seleted, int resource_color_not_selected, int background, int current) {
        this.resource = resources;
        this.color_not_seleted = resource_color_not_selected;
        this.color_selected = resource_color_seleted;
        setBackgroundResource(background);
        views = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {

            int position = i;
            LinearLayout linearLayout = new LinearLayout(context);
            TextView title = new TextView(context);
            title.setText(titles[i]);
            title.setTextColor(ContextCompat.getColor(context, color_not_seleted));
            LayoutParams titlelayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1F);
            title.setGravity(Gravity.CENTER);
            title.setLayoutParams(titlelayoutParams);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            ImageView icon = new ImageView(context);
            icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            icon.setImageResource(resources[i]);
            LayoutParams imageviewlp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1F);
            imageviewlp.setMargins(0, (int) context.getResources().getDimension(R.dimen.layout_small_margin), 0, 0);
            icon.setLayoutParams(imageviewlp);
            title.setTextColor(ContextCompat.getColor(context, color_not_seleted));
            icon.setColorFilter(ContextCompat.getColor(context, color_not_seleted), PorterDuff.Mode.MULTIPLY);
            if (current >= 0 && current == i) {
                title.setTextColor(ContextCompat.getColor(context, color_selected));
                icon.setColorFilter(ContextCompat.getColor(context, color_selected), PorterDuff.Mode.MULTIPLY);
            }
            linearLayout.addView(icon);
            linearLayout.addView(title);
            linearLayout.setTag(R.string.tag, i);
            linearLayout.setOnClickListener(v -> {
                changeState(position);
                changeViewpager(position);
            });
            LayoutParams layoutParamLL = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1F);
            linearLayout.setLayoutParams(layoutParamLL);

            views.add(linearLayout);
        }
        for (int i = 0; i < views.size(); i++) {
            addView(views.get(i));
        }

    }

    private void changeState(int position) {
        for (int i = 0; i < views.size(); i++) {
            LinearLayout view = views.get(i);
            ImageView icon = (ImageView) view.getChildAt(0);
            TextView title = (TextView) view.getChildAt(1);
            if ((int) view.getTag(R.string.tag) == position) {
                view.setSelected(true);
                title.setTextColor(ContextCompat.getColor(context, color_selected));
                Drawable drawable = getResources().getDrawable(resource[position]);
                if (Build.VERSION.SDK_INT > 21) {
                    drawable.setTint(ContextCompat.getColor(context, color_selected));
                }
                icon.setColorFilter(ContextCompat.getColor(context, color_selected), PorterDuff.Mode.MULTIPLY);

            } else {
                title.setTextColor(ContextCompat.getColor(context, color_not_seleted));
                icon.setColorFilter(ContextCompat.getColor(context, color_not_seleted), PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    private void changeViewpager(int position) {
        for (LinearLayout view : views) {
            if ((int) view.getTag(R.string.tag) == position) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(position);
                }
            }
        }
    }

    public void setViewPagerListener(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("position:" + position, "offset:" + positionOffset);
                if (positionOffset == 0) {
                    changeState(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                changeState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
