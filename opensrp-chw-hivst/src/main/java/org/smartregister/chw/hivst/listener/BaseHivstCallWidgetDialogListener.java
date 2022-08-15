package org.smartregister.chw.hivst.listener;


import android.view.View;

import org.smartregister.chw.hivst.fragment.BaseHivstCallDialogFragment;
import org.smartregister.chw.hivst.util.HivstUtil;
import org.smartregister.hivst.R;

import timber.log.Timber;

public class BaseHivstCallWidgetDialogListener implements View.OnClickListener {

    private BaseHivstCallDialogFragment callDialogFragment;

    public BaseHivstCallWidgetDialogListener(BaseHivstCallDialogFragment dialogFragment) {
        callDialogFragment = dialogFragment;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.hivst_call_close) {
            callDialogFragment.dismiss();
        } else if (i == R.id.hivst_call_head_phone) {
            try {
                String phoneNumber = (String) v.getTag();
                HivstUtil.launchDialer(callDialogFragment.getActivity(), callDialogFragment, phoneNumber);
                callDialogFragment.dismiss();
            } catch (Exception e) {
                Timber.e(e);
            }
        } else if (i == R.id.call_hivst_client_phone) {
            try {
                String phoneNumber = (String) v.getTag();
                HivstUtil.launchDialer(callDialogFragment.getActivity(), callDialogFragment, phoneNumber);
                callDialogFragment.dismiss();
            } catch (Exception e) {
                Timber.e(e);
            }
        }
    }
}
