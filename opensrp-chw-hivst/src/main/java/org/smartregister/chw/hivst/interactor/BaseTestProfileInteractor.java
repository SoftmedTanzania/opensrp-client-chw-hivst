package org.smartregister.chw.hivst.interactor;

import android.support.annotation.VisibleForTesting;

import org.smartregister.chw.hivst.contract.TestProfileContract;
import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.chw.hivst.util.AppExecutors;
import org.smartregister.chw.hivst.util.TestUtil;
import org.smartregister.domain.AlertStatus;

import java.util.Date;

public class BaseTestProfileInteractor implements TestProfileContract.Interactor {
    protected AppExecutors appExecutors;

    @VisibleForTesting
    BaseTestProfileInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public BaseTestProfileInteractor() {
        this(new AppExecutors());
    }

    @Override
    public void refreshProfileInfo(MemberObject memberObject, TestProfileContract.InteractorCallBack callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> {
            callback.refreshFamilyStatus(AlertStatus.normal);
            callback.refreshMedicalHistory(true);
            callback.refreshUpComingServicesStatus("Hivst Visit", AlertStatus.normal, new Date());
        });
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveRegistration(final String jsonString, final TestProfileContract.InteractorCallBack callback) {

        Runnable runnable = () -> {
            try {
                TestUtil.saveFormEvent(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        appExecutors.diskIO().execute(runnable);
    }
}
