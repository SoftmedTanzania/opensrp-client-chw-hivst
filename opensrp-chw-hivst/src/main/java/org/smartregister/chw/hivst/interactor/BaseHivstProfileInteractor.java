package org.smartregister.chw.hivst.interactor;

import androidx.annotation.VisibleForTesting;

import org.smartregister.chw.hivst.contract.HivstProfileContract;
import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.chw.hivst.util.AppExecutors;
import org.smartregister.chw.hivst.util.HivstUtil;
import org.smartregister.domain.AlertStatus;

import java.util.Date;

public class BaseHivstProfileInteractor implements HivstProfileContract.Interactor {
    protected AppExecutors appExecutors;

    @VisibleForTesting
    BaseHivstProfileInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public BaseHivstProfileInteractor() {
        this(new AppExecutors());
    }

    @Override
    public void refreshProfileInfo(MemberObject memberObject, HivstProfileContract.InteractorCallBack callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> {
            callback.refreshFamilyStatus(AlertStatus.normal);
            callback.refreshMedicalHistory(true);
            callback.refreshUpComingServicesStatus("Hivst Visit", AlertStatus.normal, new Date());
        });
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveRegistration(final String jsonString, final HivstProfileContract.InteractorCallBack callback) {

        Runnable runnable = () -> {
            try {
                HivstUtil.saveFormEvent(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        appExecutors.diskIO().execute(runnable);
    }
}
