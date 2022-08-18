package org.smartregister.chw.hivst.model;

import org.smartregister.chw.hivst.HivstLibrary;
import org.smartregister.chw.hivst.contract.HivstResultsFragmentContract;
import org.smartregister.chw.hivst.util.ConfigHelper;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;

import java.util.Set;

public class BaseHivstResultsFragmentModel implements HivstResultsFragmentContract.Model {
    @Override
    public RegisterConfiguration defaultRegisterConfiguration() {
        return ConfigHelper.defaultRegisterConfiguration(HivstLibrary.getInstance().context().applicationContext());
    }

    @Override
    public ViewConfiguration getViewConfiguration(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getViewConfiguration(viewConfigurationIdentifier);
    }

    @Override
    public Set<View> getRegisterActiveColumns(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getRegisterActiveColumns(viewConfigurationIdentifier);
    }

    @Override
    public String countSelect(String tableName, String mainCondition) {
        SmartRegisterQueryBuilder countQueryBuilder = new SmartRegisterQueryBuilder();
        countQueryBuilder.selectInitiateMainTableCounts(tableName);
        return countQueryBuilder.mainCondition(mainCondition);
    }

    @Override
    public String mainSelect(String tableName, String mainCondition) {
        SmartRegisterQueryBuilder queryBuilder = new SmartRegisterQueryBuilder();
        queryBuilder.selectInitiateMainTable(tableName, mainColumns(tableName));
        return queryBuilder.mainCondition(mainCondition);
    }


    protected String[] mainColumns(String tableName) {
        String[] columns = new String[]{
                tableName + ".relationalid"
        };
        return columns;
    }
}
