package samantha.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BasicFragment extends Fragment {

    private static final String EXCEPTION_MSG = BasicFragment.class.getSimpleName() + " can only be accessed by activities implementing " + IWitActivity.class.getSimpleName();

    @Override
    public void onAttach (Activity activity){
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        applyWitSetting();
        return rootView;
    }

    private void applyWitSetting() {
        Activity activity = getActivity();
        if (activity instanceof IWitActivity) {
            ((IWitActivity) activity).setWitSetting();
        }
        else {
            throw new IllegalAccessError(EXCEPTION_MSG);
        }
    }
}

