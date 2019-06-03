package app.beelabs.com.codebase.base;

import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import app.beelabs.com.codebase.R;
import app.beelabs.com.codebase.component.LoadingDialogComponent;
import app.beelabs.com.codebase.di.IProgress;
import app.beelabs.com.codebase.di.component.AppComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by arysuryawan on 8/16/17.
 */

public class BaseActivity extends AppCompatActivity implements IView, ComponentCallbacks2 {
    private BroadcastReceiver broadcastReceiver;
    private View rootView;

    public void setupCoconutContentView(int rootIdLayout){

        this.rootView = findViewById(rootIdLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadCastReceiver();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*
         * Uncomment this line in order to avoid memory leak.
         * You need to unregister the broadcast receiver since the broadcast receiver keeps a reference of the activity.
         * Now when its time for your Activity to die, the Android framework will call onDestroy() on it
         * but the garbage collector will not be able to remove the instance from memory because the broadcastReceiver
         * is still holding a strong reference to it.
         * */

        if(broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showFragment(Fragment fragment, int fragmentResourceID) {

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentResourceID, fragment);
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
        }
    }

    protected BaseFragment onBackFragment(BaseActivity activity) {
        List fragments = activity.getSupportFragmentManager().getFragments();
        BaseFragment currentFragment = (BaseFragment) fragments.get(fragments.size() - 1);

        return currentFragment;
    }


    // handle progress dialog
    protected void showApiProgressDialog(AppComponent appComponent, BasePresenter presenter) {
        showApiProgressDialog(appComponent, presenter, null);
    }

    protected void showApiProgressDialog(AppComponent appComponent, BasePresenter presenter, String message) {
        IProgress progress = appComponent.getProgressDialog();
        progress.showProgressDialog(this, message, false);
        presenter.call();
    }

    protected void showApiProgressDialog(AppComponent appComponent, BasePresenter presenter, String message, boolean isCanceledOnTouch) {
        IProgress progress = appComponent.getProgressDialog();
        progress.showProgressDialog(this, message, isCanceledOnTouch);
        presenter.call();
    }


    protected void showApiCustomProgressDialog(AppComponent appComponent, BasePresenter presenter, String message) {
        IProgress progress = appComponent.getProgressDialog();
        progress.showLoadingDialog(new LoadingDialogComponent(message, this, R.style.CoconutDialogFullScreen));
        presenter.call();

    }

    private void registerBroadCastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //your receiver code goes here!
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("SmsMessage.intent.MAIN"));
    }

    //--- end ----

    @Override
    public void handleError(String message) {

    }

    @Override
    public BaseActivity getCurrentActivity() {
        return this;
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public void handleRetryConnection() {

    }
}
