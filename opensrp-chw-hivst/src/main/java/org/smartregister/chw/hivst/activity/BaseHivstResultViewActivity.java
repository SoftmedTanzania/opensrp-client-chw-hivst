package org.smartregister.chw.hivst.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.hivst.contract.BaseHivstResultViewContract;
import org.smartregister.chw.hivst.fragment.BaseHivstResultViewFragment;
import org.smartregister.chw.hivst.interactor.BaseHivstResultViewInteractor;
import org.smartregister.chw.hivst.presenter.BaseHivstResultViewPresenter;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.hivst.R;
import org.smartregister.view.activity.SecuredActivity;

import androidx.fragment.app.FragmentTransaction;

public class BaseHivstResultViewActivity extends SecuredActivity implements View.OnClickListener {
    protected BaseHivstResultViewContract.Presenter resultsPresenter;
    private String baseEntityId;
    private String entityId;

    public static void startResultViewActivity(Context context, String baseEntityId) {
        // implement in app
        Intent intent = new Intent(context, BaseHivstResultViewActivity.class);
        intent.putExtra(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID, baseEntityId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreation() {
        setContentView(R.layout.base_hivst_result_view_activity);
        String baseEntityId = getIntent().getStringExtra(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID);
        String jsonString = getIntent().getStringExtra(Constants.ACTIVITY_PAYLOAD.HIVST_FORM_NAME);
        String entityId = getIntent().getStringExtra(Constants.JSON_FORM_EXTRA.ENTITY_ID);
        if (StringUtils.isNotBlank(jsonString)) {
            this.baseEntityId = baseEntityId;
            this.entityId = entityId;
            startFormActivity(jsonString);
        } else {
            this.baseEntityId = baseEntityId;
            loadFragment();
            setupViews();
        }
        initializePresenter();
    }


    public void startFormActivity(String jsonString) {
        //Implement
    }

    public void setupViews() {
        ImageView backImageView = findViewById(R.id.back);
        backImageView.setOnClickListener(this);
    }

    @Override
    protected void onResumption() {
        // Implement
    }

    public BaseHivstResultViewFragment getBaseFragment() {
        return BaseHivstResultViewFragment.newInstance(baseEntityId);
    }

    public void initializePresenter() {
        resultsPresenter = new BaseHivstResultViewPresenter(new BaseHivstResultViewInteractor());
    }

    private void loadFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, getBaseFragment());
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_GET_JSON && resultCode == Activity.RESULT_OK) {
            String jsonString = data.getStringExtra(Constants.JSON_FORM_EXTRA.JSON);
            if (StringUtils.isNotBlank(jsonString) && StringUtils.isNotBlank(baseEntityId) && StringUtils.isNotBlank(entityId)) {
                resultsPresenter.saveForm(jsonString, baseEntityId, entityId);
                finish();
            }
        } else {
            finish();
        }
    }
}
