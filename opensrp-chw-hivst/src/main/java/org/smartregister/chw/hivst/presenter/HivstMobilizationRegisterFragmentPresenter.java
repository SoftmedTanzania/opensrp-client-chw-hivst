package org.smartregister.chw.hivst.presenter;

import org.smartregister.chw.hivst.contract.HivstRegisterFragmentContract;
import org.smartregister.chw.hivst.util.Constants;

public class HivstMobilizationRegisterFragmentPresenter extends BaseHivstRegisterFragmentPresenter {

    public HivstMobilizationRegisterFragmentPresenter(HivstRegisterFragmentContract.View view, HivstRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        super(view, model, viewConfigurationIdentifier);
    }

    @Override
    public String getMainTable() {
        return Constants.TABLES.HIVST_MOBILIZATION;
    }

    @Override
    public String getMainCondition() {
        return Constants.TABLES.HIVST_MOBILIZATION + "." + "is_closed is 0";
    }

}
