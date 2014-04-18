package samantha.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartUp extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
        if (SamanthaApplication.getInstance().isReady())
        {
            startRelevantActivity();
        }
        else
        {
            attachOnReadyCallback();
        }
    }

    private void attachOnReadyCallback()
    {
        SamanthaApplication.getInstance().attachCallback(new SamanthaApplication.Callback() {
            @Override
            public void onReady() {
                startRelevantActivity();
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private void startRelevantActivity()
    {
        Class className = new NavigationHelper().getDefaultIntentIfAppIsReady();
        final Intent nextActivityIntent = new Intent(StartUp.this, className);
        nextActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(nextActivityIntent);
                finish();
            }
        });
    }
}
