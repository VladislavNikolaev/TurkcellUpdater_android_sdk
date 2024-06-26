package tr.com.turkcellteknoloji.turkcellupdatersampleapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Serializable;

import tr.com.turkcellteknoloji.turkcellupdater.Message;
import tr.com.turkcellteknoloji.turkcellupdater.TurkcellUpdater;
import tr.com.turkcellteknoloji.turkcellupdater.TurkcellUpdater.TurkcellUpdaterCallback;
import tr.com.turkcellteknoloji.turkcellupdater.UpdaterDialogManager.UpdaterUiListener;

public class SplashActivity extends Activity {

    Message message;

    private EditText serverAddressEditText;

    private CheckBox postPropertiesCheckBox;

    private Button startUpdateCheckButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar);
        setContentView(R.layout.activity_splash);
        serverAddressEditText = (EditText) findViewById(R.id.serverAddressEditText);
        postPropertiesCheckBox = (CheckBox) findViewById(R.id.postPropertiesCheckbox);
        startUpdateCheckButton = (Button) findViewById(R.id.startUpdateCheckButton);
        startUpdateCheckButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TurkcellUpdater updater = new TurkcellUpdater(SplashActivity.this, serverAddressEditText.getText().toString());
                updater.setTurkcellUpdaterCallback(mTurkcellUpdaterCallback);
                updater.setDefaultDialogCallback(mUpdaterUiListener);
                updater.check(false);
            }
        });

    }

    private TurkcellUpdaterCallback mTurkcellUpdaterCallback = new TurkcellUpdaterCallback() {
        @Override
        public void onForceUpdateReceive(String message, String warnings, String whatIsNew, String positiveButton, String negativeButton) {
            Log.e("onForceUpdateReceive", message + " " + warnings + " " + whatIsNew);
        }

        @Override
        public void onForceExitReceive(String message, String warnings, String whatIsNew, String positiveButton, String negativeButton) {
            Log.e("onForceExitReceive", message + " " + warnings + " " + whatIsNew + " " + positiveButton + " " + negativeButton);
        }

        @Override
        public void onNonForceUpdateReceive(String message, String warnings, String whatIsNew, String positiveButton, String negativeButton) {
            Log.e("onNonForceUpdateReceive", message + " " + warnings + " " + whatIsNew + " " + positiveButton + " " + negativeButton);
        }

        @Override
        public void onMessageReceive(String title, String message, String positiveButton, String negativeButton, String imageUrl, Uri redirectionUri) {
            Log.e("onMessageReceive", title + " " + message + " " + imageUrl + " " + redirectionUri.toString() + " " + positiveButton + " " + negativeButton);
        }

        @Override
        public void onUpdateNotFoundReceive() {
            Log.e("onUpdateNotFoundReceive", "asd");
        }

        @Override
        public void onUpdaterErrorReceive(Exception e) {
            Log.e("onUpdaterErrorReceive", e.toString());
        }
    };

    private UpdaterUiListener mUpdaterUiListener = new UpdaterUiListener() {

        @Override
        public void onExitApplication() {
            finish();
        }

        @Override
        public void onUpdateCheckCompleted() {
            final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.putExtra("message", (Serializable) message);
            startActivity(intent);
            finish();
        }

        @Override
        public boolean onDisplayMessage(Message message) {
            // To automatically display message:
            // return false;
            SplashActivity.this.message = message;
            return true;
        }
    };
}
