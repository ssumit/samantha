package samantha.app;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastMaker
{
    private Context _context;
    private Toast _toast;

    public ToastMaker(Context context)
    {
        _context = context;
    }

    public void showToast(final String content ,final Integer duration)
    {
        new Handler(_context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (_toast != null) {
                    _toast.setText(content);
                    _toast.setDuration(duration);
                } else {
                    _toast = Toast.makeText(_context, content, duration);
                }
                _toast.show();
            }
        });
    }

    public void showToast(String content)
    {
        showToast(content, Toast.LENGTH_LONG);
    }

    public void showToast(Integer contentId, Integer duration)
    {
        String content = _context.getString(contentId);
        showToast(content, duration);
    }

    public void showToast(Integer contentId)
    {
        showToast(contentId, Toast.LENGTH_LONG);
    }
}
