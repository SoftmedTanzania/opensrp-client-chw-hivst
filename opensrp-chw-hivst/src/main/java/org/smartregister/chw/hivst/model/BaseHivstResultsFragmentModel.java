package org.smartregister.chw.hivst.model;

import androidx.annotation.NonNull;

import org.smartregister.chw.hivst.HivstLibrary;
import org.smartregister.chw.hivst.contract.HivstResultsFragmentContract;
import org.smartregister.chw.hivst.util.ConfigHelper;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.chw.hivst.util.DBConstants;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;

import java.util.HashSet;
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


    @NonNull
    @Override
    public String mainSelect(@NonNull String tableName, @NonNull String mainCondition) {
        SmartRegisterQueryBuilder queryBuilder = new SmartRegisterQueryBuilder();
        queryBuilder.selectInitiateMainTable(tableName, mainColumns(tableName), DBConstants.KEY.BASE_ENTITY_ID);
        queryBuilder.customJoin("INNER JOIN " + Constants.TABLES.HIVST_FOLLOWUP + " ON  " + tableName + "." + DBConstants.KEY.ENTITY_ID + " = " + Constants.TABLES.HIVST_FOLLOWUP + "." + DBConstants.KEY.ENTITY_ID + " COLLATE NOCASE ");
        return queryBuilder.mainCondition(mainCondition);
    }

    protected String[] mainColumns(String tableName) {
        Set<String> columnList = new HashSet<>();
        columnList.add(tableName + "." + DBConstants.KEY.ENTITY_ID + " as " + DBConstants.KEY.BASE_ENTITY_ID);
        columnList.add(tableName + "." + DBConstants.KEY.BASE_ENTITY_ID + " as " + DBConstants.KEY.ENTITY_ID);
        columnList.add(tableName + "." + DBConstants.KEY.ENTITY_ID + " as relationalid");
        columnList.add(tableName + "." + DBConstants.KEY.KIT_CODE);
        columnList.add(tableName + "." + DBConstants.KEY.KIT_FOR);
        columnList.add(tableName  + "." + DBConstants.KEY.HIVST_RESULT);
        columnList.add(Constants.TABLES.HIVST_FOLLOWUP + "." + DBConstants.KEY.COLLECTION_DATE);

        return columnList.toArray(new String[columnList.size()]);

    }
}
