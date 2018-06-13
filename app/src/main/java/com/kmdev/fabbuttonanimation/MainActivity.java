package com.kmdev.fabbuttonanimation;
import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private boolean flag = true;
    private ImageButton mImageButton;
    private LinearLayout mLayoutButtons;
    private LinearLayout mRevealView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageButton = (ImageButton) findViewById(R.id.imageMessage);
        mRevealView = (LinearLayout) findViewById(R.id.linearView);
        mLayoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void launchAnimation(View view) {
/*
      get the center for the clipping circle
      int centerX = (imageView.getLeft() + imageView.getRight()) / 2;
      int centerY = (imageView.getTop() + imageView.getBottom()) / 2;

        int centerX = imageView.getLeft();
        int centerY = imageView.getTop();

        int centerX = imageView.getRight();
        int centerY = imageView.getTop();


*/
        int centerX = mImageView.getRight();
        int centerY = mImageView.getTop();
        int startRadius = 0;
        // get the final radius for the clipping circle
        int endRadius = (int) Math.hypot(mImageView.getWidth(), mImageView.getHeight());

        if (flag) {
            mImageButton.setImageResource(R.drawable.ic_close_white_24dp);
            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                    mRevealView.getLayoutParams();
            parameters.height = mImageView.getHeight();
            mRevealView.setLayoutParams(parameters);
            Animator anim = null;
            // create the animator for this view (the start radius is zero)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                anim = ViewAnimationUtils.createCircularReveal(mRevealView, centerX, centerY, 0, endRadius);
            }
            anim.setDuration(500);
            // make the view visible and start the animation
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mLayoutButtons.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mRevealView.setVisibility(View.VISIBLE);
            anim.start();
            flag = false;
        } else {
            mImageButton.setImageResource(R.drawable.ic_message_white_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(mRevealView, centerX, centerY, endRadius, 0);
            anim.setDuration(400);
            // make the view visible and start the animation
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mRevealView.setVisibility(View.GONE);
                    mLayoutButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            anim.start();
            flag = true;
        }
    }
}
