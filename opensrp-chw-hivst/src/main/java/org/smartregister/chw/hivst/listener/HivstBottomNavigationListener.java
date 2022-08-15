package org.smartregister.chw.hivst.listener;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.MenuItem;

import org.smartregister.listener.BottomNavigationListener;
import org.smartregister.hivst.R;
import org.smartregister.view.activity.BaseRegisterActivity;

public class HivstBottomNavigationListener extends BottomNavigationListener {
    private Activity context;

    public HivstBottomNavigationListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        super.onNavigationItemSelected(item);

        BaseRegisterActivity baseRegisterActivity = (BaseRegisterActivity) context;

        if (item.getItemId() == R.id.action_family) {
            baseRegisterActivity.switchToBaseFragment();
        } else if (item.getItemId() == R.id.action_scan_qr) {
            baseRegisterActivity.startQrCodeScanner();
        }

        return true;
    }
}