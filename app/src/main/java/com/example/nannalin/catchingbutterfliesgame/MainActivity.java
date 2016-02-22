package com.example.nannalin.catchingbutterfliesgame;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import static android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    SoundPool soundPool;

    // no sound at the current time
    int mySound1 = -1;
    int mySound2 = -1;

    FrameLayout game;
    LinearLayout gameWidgets;
    Button startBtn;
    GameView gv;

    // paint background
    Paint drawPaint = new Paint();

    Bitmap backgroundGraphic;

    int labelColor = Color.argb(255, 26, 26, 255);

    // Device icon
    Bitmap graphic1;

    // User icon
    Bitmap graphicUser;
    float x, y;

    // set the appear position of icon
    float graphicUserX = 150, graphicUserY = 80;


    boolean iconTouched = false;

    // Butterflies
    Bitmap graphic2, graphic3, graphic4, graphic5, graphic6, graphic7, graphic8, graphic9, graphic10, graphic11, graphic12, graphic13, graphic14, graphic15, graphic16;

    // Create rectangle around icons to check if they are intersected to each other
    Rect graphic1Rect, graphic2Rect, graphic3Rect, graphic4Rect, graphic5Rect, graphic6Rect, graphic7Rect, graphic8Rect, graphic9Rect, graphic10Rect, graphic11Rect, graphic12Rect, graphic13Rect, graphic14Rect, graphic15Rect, graphic16Rect, graphicUserRect;

    // set the appear position and speed
    Icon deviceIcon = new DeviceIcon(0, 80, 10, 10, graphic1, graphic1Rect);

    Icon icon2 = new Icon(400, 400, 10, 10, graphic2, graphic2Rect);
    Icon icon3 = new Icon(400, 400, 10, 10, graphic3, graphic3Rect);
    Icon icon4 = new Icon(400, 400, 10, 10, graphic4, graphic4Rect);
    Icon icon5 = new Icon(600, 400, 10, 10, graphic5, graphic5Rect);
    Icon icon6 = new Icon(600, 400, 10, 10, graphic6, graphic6Rect);
    Icon icon7 = new Icon(400, 400, 10, 10, graphic7, graphic7Rect);
    Icon icon8 = new Icon(400, 400, 10, 10, graphic8, graphic8Rect);
    Icon icon9 = new Icon(400, 400, 10, 10, graphic9, graphic9Rect);
    Icon icon10 = new Icon(400, 400, 15, 15, graphic10, graphic10Rect);
    Icon icon11 = new Icon(400, 400, 15, 15, graphic11, graphic11Rect);
    Icon icon12 = new Icon(400, 400, 15, 15, graphic12, graphic12Rect);
    Icon icon13 = new Icon(400, 400, 15, 15, graphic13, graphic13Rect);
    Icon icon14 = new Icon(400, 400, 15, 15, graphic14, graphic14Rect);
    Icon icon15 = new Icon(400, 400, 15, 15, graphic15, graphic15Rect);
    Icon icon16 = new Icon(400, 400, 15, 15, graphic16, graphic16Rect);

    // Initiate Score
    int yourScore = 0;
    int deviceScore = 0;

    boolean isGameOver = false;
    boolean clickStart = false;

    class Icon {
        int x;
        int y;
        int speedX;
        int speedY;
        Bitmap graphic;
        Rect rect;

        Icon() {
            // Default constructor.
        }

        Icon(int xMultiplier, int yMultiplier, int speedXMultiplier, int speedYMultiplier, Bitmap graphic, Rect rect) {
            this.x = 400 + (int)(Math.random() * xMultiplier);
            this.y = 500 + (int)(Math.random() * yMultiplier);
            this.speedX = 10 + (int)(Math.random() * speedXMultiplier);
            this.speedY = 10 + (int)(Math.random() * speedYMultiplier);
            this.graphic = graphic;
            this.rect = rect;
        }
    }

    class DeviceIcon extends Icon {

        DeviceIcon(int x, int y, int speedXMultiplier, int speedYMultiplier, Bitmap graphic, Rect rect) {
            this.x = x;
            this.y = y;
            this.speedX = 5 + (int)(Math.random() * speedXMultiplier);
            this.speedY = 5 + (int)(Math.random() * speedYMultiplier);
            this.graphic = graphic;
            this.rect = rect;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // 20 different sounds, usingstream music, source quality
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        // asset manager handle sound
        AssetManager assetManager = getAssets();

        try {
            AssetFileDescriptor descriptor = assetManager.openFd("woosh.wav");
            AssetFileDescriptor endSound = assetManager.openFd("jack_in_the_box.wav");
            mySound1 = soundPool.load(descriptor, 1);
            mySound2 = soundPool.load(endSound, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        game = new FrameLayout(this);

        // tell the program that we use runnable surfaceview instead of xml file
        // put (this) because GameView is part of the main method
        gv = new GameView(this);

        gameWidgets = new LinearLayout(this);

        // set position of the widget
        gameWidgets.setPadding(400, 1300, 0, 0);

        // create start button
        gameWidgets.addView(createStartButton());

        // add gameView and widget to the frame layout
        game.addView(gv);
        game.addView(gameWidgets);

        this.setContentView(game);

        // create background
        backgroundGraphic = BitmapFactory.decodeResource(getResources(), R.mipmap.sunflower_landscape);

        // Create icons
        graphicUser = BitmapFactory.decodeResource(getResources(), R.mipmap.cartoon_net);
        deviceIcon.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.frog_chasing_fly);
        icon2.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon3.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon4.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon5.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon6.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon7.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon8.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon9.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon10.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon11.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon12.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon13.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon14.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
        icon15.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_2);
        icon16.graphic = BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly_1);
    }

    private Button createStartButton() {
        startBtn = new Button(this);
        startBtn.setTextColor(Color.WHITE);
        startBtn.setBackgroundColor(Color.argb(255, 0, 90, 230));
        startBtn.setTextSize(15);
        startBtn.setTypeface(Typeface.DEFAULT_BOLD);
        startBtn.setText("Start Game");

        startBtn.setVisibility(View.VISIBLE);

        startBtn.setOnClickListener(this);

        return startBtn;
    }

    public void playSound (int sound) {
        soundPool.play(sound, 1, 1, 0, 0, 1);
    }

    // onPause() will be automatically callled when we pause our device or app go to background
    @Override
    protected void onPause() {
        super.onPause();
        gv.pause();
    }

    // when come back from the background, system call onResume
    @Override
    protected void onResume() {
        super.onResume();
        gv.resume();
    }

    @Override
    public void onClick(View v) {
        // hide start button after the user clicked it
        startBtn.setVisibility(View.INVISIBLE);

        clickStart = true;

        // start button only work after the game is over and user click button to play game again
        if (isGameOver) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            // re-starts this activity from game-view. add this.finish(); to remove from stack
            this.finish();
        }
    }

    public class GameView extends SurfaceView implements Runnable {

        Thread ViewThread = null;

        // id for the actual screen
        SurfaceHolder holder;
        boolean threadOK = true;

        public GameView(Context context) {
            super(context);
            // initialize holder
            holder = this.getHolder();
        }

        @Override
        public void run() {
            while (threadOK == true)  {
                if (!holder.getSurface().isValid()) {
                    continue;
                }

                // lock canvas to paint then unlock to show it
                Canvas gameCanvas = holder.lockCanvas();

                // create rectangle
                // draw square around graphic
                graphicUserRect = new Rect((int)graphicUserX, (int)graphicUserY, (int)graphicUserX + graphicUser.getWidth(), (int)graphicUserY + graphicUser.getHeight());
                deviceIcon.rect = new Rect(deviceIcon.x, deviceIcon.y, deviceIcon.x + deviceIcon.graphic.getWidth(), deviceIcon.y + deviceIcon.graphic.getHeight());
                icon2.rect = new Rect(icon2.x, icon2.y, icon2.x + icon2.graphic.getWidth(), icon2.y + icon2.graphic.getHeight());
                icon3.rect = new Rect(icon3.x, icon3.y, icon3.x + icon3.graphic.getWidth(), icon3.y + icon3.graphic.getHeight());
                icon4.rect = new Rect(icon4.x, icon4.y, icon4.x + icon4.graphic.getWidth(), icon4.y + icon4.graphic.getHeight());
                icon5.rect = new Rect(icon5.x, icon5.y, icon5.x + icon5.graphic.getWidth(), icon5.y + icon5.graphic.getHeight());
                icon6.rect = new Rect(icon6.x, icon6.y, icon6.x + icon6.graphic.getWidth(), icon6.y + icon6.graphic.getHeight());
                icon7.rect = new Rect(icon7.x, icon7.y, icon7.x + icon7.graphic.getWidth(), icon7.y + icon7.graphic.getHeight());
                icon8.rect = new Rect(icon8.x, icon8.y, icon8.x + icon8.graphic.getWidth(), icon8.y + icon8.graphic.getHeight());
                icon9.rect = new Rect(icon9.x, icon9.y, icon9.x + icon9.graphic.getWidth(), icon9.y + icon9.graphic.getHeight());
                icon10.rect = new Rect(icon10.x, icon10.y, icon10.x + icon10.graphic.getWidth(), icon10.y + icon10.graphic.getHeight());
                icon11.rect = new Rect(icon11.x, icon11.y, icon11.x + icon11.graphic.getWidth(), icon11.y + icon11.graphic.getHeight());
                icon12.rect = new Rect(icon12.x, icon12.y, icon12.x + icon12.graphic.getWidth(), icon12.y + icon12.graphic.getHeight());
                icon13.rect = new Rect(icon13.x, icon13.y, icon13.x + icon13.graphic.getWidth(), icon13.y + icon13.graphic.getHeight());
                icon14.rect = new Rect(icon14.x, icon14.y, icon14.x + icon14.graphic.getWidth(), icon14.y + icon14.graphic.getHeight());
                icon15.rect = new Rect(icon15.x, icon15.y, icon15.x + icon15.graphic.getWidth(), icon15.y + icon15.graphic.getHeight());
                icon16.rect = new Rect(icon16.x, icon16.y, icon16.x + icon16.graphic.getWidth(), icon16.y + icon16.graphic.getHeight());

                onDraw(gameCanvas, gv.getContext());

                // post to canvas
                holder.unlockCanvasAndPost(gameCanvas);

                if(isGameOver && (icon2.graphic.isRecycled()) && (icon3.graphic.isRecycled()) && (icon4.graphic.isRecycled()) && (icon5.graphic.isRecycled()) && (icon6.graphic.isRecycled()) && (icon7.graphic.isRecycled()) && (icon8.graphic.isRecycled()) && (icon9.graphic.isRecycled()) && (icon10.graphic.isRecycled()) && (icon11.graphic.isRecycled()) && (icon12.graphic.isRecycled()) && (icon13.graphic.isRecycled()) && (icon14.graphic.isRecycled()) && (icon15.graphic.isRecycled()) && (icon16.graphic.isRecycled())) {
                    playSound(mySound2);
                    threadOK = false;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startBtn.setVisibility(View.VISIBLE);
                            startBtn.setText("Play Again");
                            startBtn.setBackgroundColor(Color.argb(255, 255, 99, 71));
                            startBtn.setTextColor(Color.WHITE);
                        }
                    });
                }
            }

            // wait until all butterflies are caught.
            while(!icon2.graphic.isRecycled() || !icon3.graphic.isRecycled() || !icon4.graphic.isRecycled() || !icon5.graphic.isRecycled() || !icon6.graphic.isRecycled() || !icon7.graphic.isRecycled() || !icon8.graphic.isRecycled() || !icon9.graphic.isRecycled() || !icon10.graphic.isRecycled() || !icon11.graphic.isRecycled() || !icon12.graphic.isRecycled() || !icon13.graphic.isRecycled() || !icon14.graphic.isRecycled() || !icon15.graphic.isRecycled() || !icon16.graphic.isRecycled()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Do nothing.
                }
            }
        }

        protected void onDraw(Canvas canvas, Context ct) {

            // paint background on the screen
            // set alpha to the screen, 255 = fully color
            drawPaint.setAlpha(255);

            // draw background every move so we will see only one icon moving around.
            // if we don't redraw background, when run screen will show so many icons.
            // set bitmap to be background
            backgroundGraphic = Bitmap.createScaledBitmap(backgroundGraphic, canvas.getWidth(), canvas.getHeight(), true);
            canvas.drawBitmap(backgroundGraphic, 0,0,null);

            LinearLayout layout1 = new LinearLayout(ct);

            // create text view for user score
            TextView textView1 = new TextView(ct);
            setTextView(textView1, VISIBLE, labelColor, 20, 50, 5, 0, 5);
            textView1.setTypeface(Typeface.DEFAULT_BOLD);

            textView1.setText("Your Score : " + yourScore);

            layoutAndAddView(canvas, layout1, textView1);
            layout1.draw(canvas);

            // create text view for device score
            TextView textView2 = new TextView(ct);
            setTextView(textView2, VISIBLE, labelColor, 20, 160, 5, 0, 5);
            textView2.setTypeface(Typeface.DEFAULT_BOLD);
            textView2.setText("Device Score : " + deviceScore);

            layoutAndAddView(canvas, layout1, textView2);
            layout1.draw(canvas);

            // update score of each player and stop game when al 15 butterflies are caught
            // which mean the total score of user and device is equal to 15
            if ((icon2.graphic.isRecycled()) && (icon3.graphic.isRecycled()) && (icon4.graphic.isRecycled()) && (icon5.graphic.isRecycled()) && (icon6.graphic.isRecycled()) && (icon7.graphic.isRecycled()) && (icon8.graphic.isRecycled()) && (icon9.graphic.isRecycled()) && (icon10.graphic.isRecycled()) && (icon11.graphic.isRecycled()) && (icon12.graphic.isRecycled()) && (icon13.graphic.isRecycled()) && (icon14.graphic.isRecycled()) && (icon15.graphic.isRecycled()) && (icon16.graphic.isRecycled())) {

                // create the layout to show who wins after the game is over
                LinearLayout layout2 = new LinearLayout(ct);
                TextView textView3 = new TextView(ct);
                setTextView(textView3, VISIBLE, Color.RED, 30, 0, 0, 0, 0);

                if (yourScore > deviceScore) {
                    textView3.setText("You are the winner!");

                } else  {   //(yourScore < deviceScore)
                    textView3.setText("You lost!");
                }

                layoutAndAddView(canvas, layout2, textView3);

                // Set textview to the center horizontal of the screen
                canvas.translate((canvas.getWidth() - textView3.getWidth()) / 2, 200);
                layout2.draw(canvas);

                // move user icon and device icon to the bottom of the screen when the game is over.
                deviceIcon.x = 20;
                deviceIcon.y =700;

                graphicUserX = 400;
                graphicUserY = 700;

                isGameOver = true;
            }

            // to keep rectangle align with icon
            // uncomment the lines below to visible the background of icon to test
            //recPaint.setAlpha(255);
            //recPaint.setColor(Color.WHITE);
            //canvas.drawRect(graphic1Rect, recPaint);

            canvas.drawBitmap(deviceIcon.graphic, deviceIcon.x, deviceIcon.y, drawPaint);
            canvas.drawBitmap(graphicUser, graphicUserX, graphicUserY, drawPaint);

            if (clickStart) {
                if ((icon2.graphic != null) && (!icon2.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon2, drawPaint);
                }
                if ((icon3.graphic != null) && (!icon3.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon3, drawPaint);
                }
                if ((icon4.graphic != null) && (!icon4.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon4, drawPaint);
                }
                if ((icon5.graphic != null) && (!icon5.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon5, drawPaint);
                }
                if ((icon6.graphic != null) && (!icon6.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon6, drawPaint);
                }
                if ((icon7.graphic != null) && (!icon7.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon7, drawPaint);
                }
                if ((icon8.graphic != null) && (!icon8.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon8, drawPaint);
                }
                if ((icon9.graphic != null) && (!icon9.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon9, drawPaint);
                }
                if ((icon10.graphic != null) && (!icon10.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon10, drawPaint);
                }
                if ((icon11.graphic != null) && (!icon11.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon11, drawPaint);
                }
                if ((icon12.graphic != null) && (!icon12.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon12, drawPaint);
                }
                if ((icon13.graphic != null) && (!icon13.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon13, drawPaint);
                }
                if ((icon14.graphic != null) && (!icon14.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon14, drawPaint);
                }
                if ((icon15.graphic != null) && (!icon15.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon15, drawPaint);
                }
                if ((icon16.graphic != null) && (!icon16.graphic.isRecycled())) {
                    handleMovedIcon(canvas, icon16, drawPaint);
                }

                deviceIcon.x += deviceIcon.speedX;
                deviceIcon.y += deviceIcon.speedY;

                // keep graphic on the screen
                deviceIcon.speedX = keepIconsInCanvasHorizontal(canvas, deviceIcon.graphic, deviceIcon.x, deviceIcon.speedX);
                deviceIcon.speedY = keepIconsInCanvasVertical(canvas, deviceIcon.graphic, deviceIcon.y, deviceIcon.speedY);
            }
        }

        private void handleMovedIcon(Canvas canvas, Icon icon, Paint drawPaint) {

            canvas.drawBitmap(icon.graphic, icon.x, icon.y, drawPaint);

            // update position of the icon
            icon.x += icon.speedX;
            icon.y += icon.speedY;

            // keep graphic on the screen
            icon.speedX = keepIconsInCanvasHorizontal(canvas, icon.graphic, icon.x, icon.speedX);
            icon.speedY = keepIconsInCanvasVertical(canvas, icon.graphic, icon.y, icon.speedY);

            // check if 2 rectangles intersect to each other
            handleIntersection(deviceIcon.rect, icon.rect);
            handleIntersection(graphicUserRect, icon.rect);
        }

        private void layoutAndAddView(Canvas canvas, LinearLayout layout, TextView textView) {
            layout.addView(textView);
            layout.measure(canvas.getWidth(), canvas.getHeight());
            layout.layout(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        private void setTextView(TextView textView, int appear, int color, int size, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            textView.setVisibility(appear);
            textView.setTextColor(color);
            textView.setTextSize(size);
            textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }

        protected void handleIntersection(Rect gp1, Rect gp2) {
            if (Rect.intersects(gp1, gp2)) {
                // add device score
                if (gp1 == deviceIcon.rect) {
                    deviceScore += 1;
                } else if (gp1 == graphicUserRect) { // add user score
                    playSound(mySound1);
                    yourScore += 1;
                }

                // remove the butterfly that was caught
                if (gp2 == icon2.rect) {
                    icon2.graphic.recycle();
                } else if (gp2 == icon3.rect) {
                    icon3.graphic.recycle();
                } else if (gp2 == icon4.rect) {
                    icon4.graphic.recycle();
                } else if (gp2 == icon5.rect) {
                    icon5.graphic.recycle();
                } else if (gp2 == icon6.rect) {
                    icon6.graphic.recycle();
                } else if (gp2 == icon7.rect) {
                    icon7.graphic.recycle();
                } else if (gp2 == icon8.rect) {
                    icon8.graphic.recycle();
                } else if (gp2 == icon9.rect) {
                    icon9.graphic.recycle();
                } else if (gp2 == icon10.rect) {
                    icon10.graphic.recycle();
                } else if (gp2 == icon11.rect) {
                    icon11.graphic.recycle();
                } else if (gp2 == icon12.rect) {
                    icon12.graphic.recycle();
                } else if (gp2 == icon13.rect) {
                    icon13.graphic.recycle();
                } else if (gp2 == icon14.rect) {
                    icon14.graphic.recycle();
                } else if (gp2 == icon15.rect) {
                    icon15.graphic.recycle();
                } else if (gp2 == icon16.rect) {
                    icon16.graphic.recycle();
                } else {
                    throw new IllegalStateException("graphic rectangle is not valid");
                }
            }
        }

        protected int keepIconsInCanvasHorizontal(Canvas canvas, Bitmap graphic, int graphicX, int graphicXSpeed) {
            if (graphicX < 0 || graphicX > (canvas.getWidth() - graphic.getWidth())) {
                graphicXSpeed = graphicXSpeed * -1;
            }

            return graphicXSpeed;
        }

        protected int keepIconsInCanvasVertical(Canvas canvas, Bitmap graphic, int graphicY, int graphicYSpeed) {

            if (graphicY < 0 || graphicY > (canvas.getHeight() - graphic.getHeight())) {
                graphicYSpeed = graphicYSpeed * -1;
            }

            return graphicYSpeed;
        }

        public void pause() {
            // tell system that it's not okay to run when it's pause
            threadOK = false;

            // while(true) = always run
            while (true) {
                try {
                    ViewThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }

            ViewThread = null;
        }

        public void resume() {
            threadOK = true;

            // create a new thread
            ViewThread = new Thread(this);

            // start
            ViewThread.start();
        }

        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                {
                    x = event.getRawX();
                    y = event.getRawY() - graphicUser.getHeight();

                    // icon can only move when icon is clicked
                    iconTouched = GraphicTools.graphicSelected(graphicUser, graphicUserX, graphicUserY, x, y);
                }
                break;

                case MotionEvent.ACTION_MOVE: {
                    x = event.getRawX() - (graphicUser.getWidth() / 2);
                    y = (event.getRawY() - graphicUser.getHeight()) - (graphicUser.getHeight() / 2);

                    if (iconTouched) {
                        // set location of image
                        graphicUserX = x;
                        graphicUserY = y;
                    }
                }
                break;

                case MotionEvent.ACTION_UP:
                {
                   iconTouched = false;
                }
                break;
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
