package com.dmitrybrant.android.multitouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class MultiTouchActivity extends Activity implements MultiTouchCanvas.MultiTouchStatusListener {
    private TextView txtInfo;
    private View btnAbout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txtInfo = findViewById(R.id.txtInfo);
        ((MultiTouchCanvas) findViewById(R.id.multiTouchView)).setStatusListener(this);

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.getBackground().setAlpha(128);
        btnAbout.setOnClickListener(v -> showAboutDialog());

        // Added in API level 20
        // setOnApplyWindowInsetsListener
        // https://developer.android.com/reference/android/view/View.OnApplyWindowInsetsListener
        // if (Build.VERSION.SDK_INT >= 21) {}
        if (Build.VERSION.SDK_INT >= 20) {
            // release APK 17KB
            findViewById(R.id.containerView).setOnApplyWindowInsetsListener((v, insets) -> {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) btnAbout.getLayoutParams();
            params.topMargin = insets.getSystemWindowInsetTop();
            params.bottomMargin = insets.getSystemWindowInsetBottom();
            params.leftMargin = insets.getSystemWindowInsetLeft();
            params.rightMargin = insets.getSystemWindowInsetRight();
            return insets.consumeSystemWindowInsets();
        });
            // release APK 1141KB
            // import androidx.core.view.ViewCompat;
            // ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.containerView), (v, insets) -> {
            // }
        }
    }

    @Override
    public void onStatus(List<Point> pointerLocations, int numPoints) {
        String str = String.format(getResources().getString(R.string.num_touches), Integer.toString(numPoints));
        for (int i = 0; i < numPoints; i++) {
            str += "\n";
            str += pointerLocations.get(i).x + ", " + pointerLocations.get(i).y;
        }
        txtInfo.setText(str);
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, null)
                .setTitle(R.string.about)
                .setMessage(R.string.str_about)
                .create()
                .show();
    }
}


