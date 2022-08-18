package org.smartregister.chw.hivst.activity;

import androidx.fragment.app.FragmentTransaction;

import org.smartregister.chw.hivst.fragment.BaseHivstResultRegisterFragment;
import org.smartregister.hivst.R;
import org.smartregister.view.activity.SecuredActivity;

public class BaseHivstResultViewActivity extends SecuredActivity {

    @Override
    protected void onCreation() {
        setContentView(R.layout.base_hivst_result_view_activity);
        loadFragment();
    }

    @Override
    protected void onResumption() {

    }

    public BaseHivstResultRegisterFragment getBaseFragment() {
        return new BaseHivstResultRegisterFragment();
    }

    private void loadFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, getBaseFragment());
        ft.commit();
    }
}
