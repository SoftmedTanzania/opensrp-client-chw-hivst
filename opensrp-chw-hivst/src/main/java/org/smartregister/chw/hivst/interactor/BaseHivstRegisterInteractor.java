package org.smartregister.chw.hivst.interactor;

import android.support.annotation.VisibleForTesting;

import org.smartregister.chw.hivst.contract.HivstRegisterContract;
import org.smartregister.chw.hivst.util.AppExecutors;
import org.smartregister.chw.hivst.util.HivstUtil;

public class BaseHivstRegisterInteractor implements HivstRegisterContract.Interactor {

    private AppExecutors appExecutors;

    @VisibleForTesting
    BaseHivstRegisterInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public BaseHivstRegisterInteractor() {
        this(new AppExecutors());
    }

    @Override
    public void saveRegistration(final String jsonString, final HivstRegisterContract.InteractorCallBack callBack) {

        Runnable runnable = () -> {
            try {
                HivstUtil.saveFormEvent(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            appExecutors.mainThread().execute(() -> callBack.onRegistrationSaved());
        };
        appExecutors.diskIO().execute(runnable);
    }
}
