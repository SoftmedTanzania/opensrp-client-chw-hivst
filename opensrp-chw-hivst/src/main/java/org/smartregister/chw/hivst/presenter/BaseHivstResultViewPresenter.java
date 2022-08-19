package org.smartregister.chw.hivst.presenter;

import org.smartregister.chw.hivst.contract.BaseHivstResultViewContract;


public class BaseHivstResultViewPresenter implements BaseHivstResultViewContract.Presenter {
    private BaseHivstResultViewContract.Interactor interactor;

    public BaseHivstResultViewPresenter(BaseHivstResultViewContract.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void saveForm(String jsonString, String baseEntityId, String entityId) {
        interactor.saveResults(jsonString, baseEntityId, entityId);
    }

}
