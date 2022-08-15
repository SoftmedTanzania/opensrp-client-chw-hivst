package org.smartregister.chw.hivst.custom_views;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;

import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.chw.hivst.fragment.BaseTestCallDialogFragment;
import org.smartregister.hivst.R;

public class BaseTestFloatingMenu extends LinearLayout implements View.OnClickListener {
    private MemberObject MEMBER_OBJECT;

    public BaseTestFloatingMenu(Context context, MemberObject MEMBER_OBJECT) {
        super(context);
        initUi();
        this.MEMBER_OBJECT = MEMBER_OBJECT;
    }

    protected void initUi() {
        inflate(getContext(), R.layout.view_hivst_floating_menu, this);
        FloatingActionButton fab = findViewById(R.id.hivst_fab);
        if (fab != null)
            fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.hivst_fab) {
            Activity activity = (Activity) getContext();
            BaseTestCallDialogFragment.launchDialog(activity, MEMBER_OBJECT);
        }  else if (view.getId() == R.id.refer_to_facility_layout) {
            Activity activity = (Activity) getContext();
            BaseTestCallDialogFragment.launchDialog(activity, MEMBER_OBJECT);
        }
    }
}