package org.smartregister.chw.hivst.interactor;

import org.smartregister.chw.hivst.contract.BaseHivstResultViewContract;
import org.smartregister.chw.hivst.util.AppExecutors;
import org.smartregister.chw.hivst.util.HivstUtil;

import androidx.annotation.VisibleForTesting;

public class BaseHivstResultViewInteractor implements BaseHivstResultViewContract.Interactor {

    private AppExecutors appExecutors;

    @VisibleForTesting
    BaseHivstResultViewInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public BaseHivstResultViewInteractor() {
        this(new AppExecutors());
    }

    @Override
    public void saveResults(String jsonString, String baseEntityId, String entityId) {
        Runnable runnable = () -> {
            try {
                HivstUtil.saveFormEvent(jsonString, baseEntityId, entityId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        appExecutors.diskIO().execute(runnable);
    }
}
