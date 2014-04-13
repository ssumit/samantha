package samantha.app;

import ai.wit.sdk.IWitListener;
import ai.wit.sdk.Wit;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements IWitListener, IWitActivity {

    private static final int RESULT_SETTINGS = 1;
    private static Fragment _fragment;
    private SamLogger _logger = new SamLogger();
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String WIT_FRAGMENT = "wit_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            _fragment = new BasicFragment();
              getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, _fragment)
                    .commit();
        }
    }

    @Override
    public void setWitSetting() {
        String access_token = new WitSettings().getAccessToken();
        _logger.logV(LOG_TAG, " access_token: " + access_token);
        if (_fragment != null) {
            Wit wit_fragment = (Wit) getSupportFragmentManager().findFragmentByTag(WIT_FRAGMENT);
            if (wit_fragment != null) {
                wit_fragment.setAccessToken(access_token);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SETTINGS:
                setWitSetting();
                break;
        }
    }

    @Override
    public void witDidGraspIntent(String intent, HashMap<String, JsonElement> entities, String body, double confidence, Error error) {
        ((TextView) findViewById(R.id.txtText)).setText(body);
        ((TextView) findViewById(R.id.jsonView)).setMovementMethod(new ScrollingMovementMethod());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(entities);
        ((TextView) findViewById(R.id.jsonView)).setText(Html.fromHtml("<span><b>Intent: " + intent +
                "<b></span><br/>") + jsonOutput +
                Html.fromHtml("<br/><span><b>Confidence: " + confidence + "<b></span>"));
    }
}
