package com.example.nannalin.catchingbutterfliesgame;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.View;
import android.graphics.Canvas;
import android.content.Context;

/**
 * Created by Nannalin on 11/18/15.
 */
public class TouchView extends View {

    public TouchView(Context context) {

        super(context);
        // TODO Auto-generated constructor stub

    }

    protected void moveIcon(Canvas canvas, boolean iconTouched, Bitmap bm, float x, float y, Paint paint) {
        if(iconTouched) {
            canvas.drawRect(x,y, x+(bm.getWidth()), y+(bm.getHeight()), paint);
        }
    }

}
