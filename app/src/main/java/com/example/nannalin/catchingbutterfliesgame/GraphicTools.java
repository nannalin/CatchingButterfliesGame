package com.example.nannalin.catchingbutterfliesgame;
import android.graphics.Bitmap;
/**
 * Created by Nannalin on 11/18/15.
 */
public class GraphicTools {
    public static boolean graphicSelected (Bitmap bm, float bmX, float bmY, float xSelected, float ySelected) {
        // find coordinate of bitmap

        if (xSelected > bmX
                && xSelected < (bmX + bm.getWidth())
                && ySelected > bmY
                && ySelected < (bmY + bm.getHeight())
                )
        {
            return true;
        }

        System.out.println("X selected =" + xSelected);
        System.out.println("bitmap X position =" + bmX);
        System.out.println("Image Width = " + bm.getWidth());
        System.out.println("Y selected=" + ySelected);
        System.out.println("bitmap Y position =" + bmY);
        System.out.println("Image Height = " + bm.getHeight());

        return false;
    }
}
