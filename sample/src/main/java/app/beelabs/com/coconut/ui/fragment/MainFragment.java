package app.beelabs.com.coconut.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.beelabs.com.coconut.R;
import app.beelabs.com.coconut.model.api.response.SummaryResponse;
import app.beelabs.com.coconut.presenter.ResourcePresenter;
import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseFragment;
import app.beelabs.com.codebase.base.BasePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by arysuryawan on 8/21/17.
 */

public class MainFragment extends BaseFragment implements IMainFragmentView {

    @BindView(R.id.fragment1Button)
    TextView fragment1Button;

    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_main, container, false);
        doSecondWay();

        ButterKnife.bind(this, layout);

        return layout;
    }

    @OnClick(R.id.fragment1Button)
    public void onSwithFragment(View view) {
        fragment1Button.setText("Replace fragment text");
        ((BaseActivity) getActivity()).showFragment(new SecondFragment(), R.id.container, true);
    }

    private void doSecondWay() {
        ((ResourcePresenter) BasePresenter.getInstance(initActivityInFragment(),
                ResourcePresenter.class)).postPhoneNumber("081212341212");
    }

    @Override
    public View getRootContentView() {
        return layout;
    }


    @Override
    public void handleDataSummary(SummaryResponse model) {
        Toast.makeText(getActivity(), model.getAcquisitionData().size() + "", Toast.LENGTH_SHORT).show();
        Log.d("TEST", "testing handle data summary");
    }

    @Override
    public void handleError(String message) {
    }

}
