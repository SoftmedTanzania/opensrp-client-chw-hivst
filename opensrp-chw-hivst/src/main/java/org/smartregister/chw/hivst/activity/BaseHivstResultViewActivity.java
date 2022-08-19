package org.smartregister.chw.hivst.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;

import org.smartregister.chw.hivst.fragment.BaseHivstResultViewFragment;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.hivst.R;
import org.smartregister.view.activity.SecuredActivity;

public class BaseHivstResultViewActivity extends SecuredActivity {
    private String baseEntityId;

    public static void startResultViewActivity(Context context, String baseEntityId){
        //TODO implement in app
        Intent intent = new Intent(context, BaseHivstResultViewActivity.class);
        intent.putExtra(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID, baseEntityId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreation() {
        setContentView(R.layout.base_hivst_result_view_activity);
        String baseEntityId = getIntent().getStringExtra(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID);
        this.baseEntityId = baseEntityId;
        loadFragment();
    }

    @Override
    protected void onResumption() {
        // Implement
    }

    public BaseHivstResultViewFragment getBaseFragment() {
        return BaseHivstResultViewFragment.newInstance(baseEntityId);
    }

    private void loadFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, getBaseFragment());
        ft.commit();
    }
}
