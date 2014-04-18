package samantha.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseActivity extends FragmentActivity
{
    private static final String LOGTAG = BaseActivity.class.getSimpleName();
    protected SamLogger _logger;
    protected Context _context;
    private static boolean _applicationInForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _logger = new SamLogger();
        onReady(savedInstanceState);
    }

    public void onReady(Bundle savedState)
    {
        hideKeyBoard();
        _context = this;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        _applicationInForeground = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        _applicationInForeground = false;
    }

    public static boolean isAnyActivityInForeground()
    {
        return _applicationInForeground;
    }

    protected void hideKeyBoard()
    {
        final View currentFocusedView = this.getCurrentFocus();
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (currentFocusedView != null)
                {
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
                }
            }
        });
    }
}
