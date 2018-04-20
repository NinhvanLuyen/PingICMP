package kenhlaptrinh.net.pingicmp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class VerticalTextView extends View {

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String str2rotate ="Claim";
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawColor(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        canvas.save();
        paint.setAntiAlias(true);
        paint.setTextSize(32);
        Rect rect = new Rect();
        paint.getTextBounds(str2rotate, 0, str2rotate.length(), rect);
        canvas.rotate(-45, 10 + rect.exactCenterX(),
                25 + rect.exactCenterY());
        canvas.drawText(str2rotate, 10, 25, paint);
    }
}