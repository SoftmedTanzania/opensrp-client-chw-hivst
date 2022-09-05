package org.smartregister.chw.hivst.presenter;

import static org.apache.commons.lang3.StringUtils.trim;

import org.smartregister.chw.hivst.contract.HivstResultsFragmentContract;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.chw.hivst.util.DBConstants;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.TreeSet;

public class BaseHivstResultsFragmentPresenter implements HivstResultsFragmentContract.Presenter {
    protected WeakReference<HivstResultsFragmentContract.View> viewReference;
    protected HivstResultsFragmentContract.Model model;

    protected RegisterConfiguration config;
    protected Set<View> visibleColumns = new TreeSet<>();
    protected String viewConfigurationIdentifier;
    private String baseEntityId;

    public BaseHivstResultsFragmentPresenter(String baseEntityId, HivstResultsFragmentContract.View view, HivstResultsFragmentContract.Model model, String viewConfigurationIdentifier) {
        this.viewReference = new WeakReference<>(view);
        this.model = model;
        this.baseEntityId = baseEntityId;
        this.viewConfigurationIdentifier = viewConfigurationIdentifier;
        this.config = model.defaultRegisterConfiguration();
    }

    @Override
    public String getMainCondition() {
        return " " + Constants.TABLES.HIVST_RESULTS + "." + DBConstants.KEY.ENTITY_ID + " = " + "'" + baseEntityId + "'";
    }

    @Override
    public String getDefaultSortQuery() {
        return Constants.TABLES.HIVST_RESULTS + "." + DBConstants.KEY.COLLECTION_DATE + " DESC ";
    }

    @Override
    public String getMainTable() {
        return Constants.TABLES.HIVST_RESULTS;
    }

    @Override
    public String getDueFilterCondition() {
        return null;
    }


    @Override
    public void processViewConfigurations() {
        //implement
    }

    @Override
    public void initializeQueries(String mainCondition) {
        String tableName = getMainTable();
        mainCondition = trim(getMainCondition()).equals("") ? mainCondition : getMainCondition();
        String countSelect = model.countSelect(tableName, mainCondition);
        String mainSelect = model.mainSelect(tableName, mainCondition);

        if (getView() != null) {

            getView().initializeQueryParams(tableName, countSelect, mainSelect);
            getView().initializeAdapter(visibleColumns);

            getView().countExecute();
            getView().filterandSortInInitializeQueries();
        }
    }


    protected HivstResultsFragmentContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }

    @Override
    public void startSync() {
        //implement
    }

    @Override
    public void searchGlobally(String s) {
        //implement
    }
}
