package org.smartregister.chw.hivst.contract;

public interface BaseHivstResultViewContract {


    interface Presenter {
        void saveForm(String jsonString, String baseEntityId, String entityId);
    }

    interface Interactor {
        void saveResults(String jsonString, String baseEntityId, String entityId);
    }
}
