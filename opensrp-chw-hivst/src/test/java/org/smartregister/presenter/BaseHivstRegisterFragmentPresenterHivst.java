package org.smartregister.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.hivst.contract.HivstRegisterFragmentContract;
import org.smartregister.chw.hivst.presenter.BaseHivstRegisterFragmentPresenter;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.chw.hivst.util.DBConstants;
import org.smartregister.configurableviews.model.View;

import java.util.Set;
import java.util.TreeSet;

public class BaseHivstRegisterFragmentPresenterHivst {
    @Mock
    protected HivstRegisterFragmentContract.View view;

    @Mock
    protected HivstRegisterFragmentContract.Model model;

    private BaseHivstRegisterFragmentPresenter baseHivstRegisterFragmentPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        baseHivstRegisterFragmentPresenter = new BaseHivstRegisterFragmentPresenter(view, model, "");
    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseHivstRegisterFragmentPresenter);
    }

    @Test
    public void getMainCondition() {
        Assert.assertEquals("", baseHivstRegisterFragmentPresenter.getMainCondition());
    }

    @Test
    public void getDueFilterCondition() {
        Assert.assertEquals(" (cast( julianday(STRFTIME('%Y-%m-%d', datetime('now'))) -  julianday(IFNULL(SUBSTR(hivst_test_date,7,4)|| '-' || SUBSTR(hivst_test_date,4,2) || '-' || SUBSTR(hivst_test_date,1,2),'')) as integer) between 7 and 14) ", baseHivstRegisterFragmentPresenter.getDueFilterCondition());
    }

    @Test
    public void getDefaultSortQuery() {
        Assert.assertEquals(Constants.TABLES.HIVST_REGISTER + "." + DBConstants.KEY.LAST_INTERACTED_WITH + " DESC ", baseHivstRegisterFragmentPresenter.getDefaultSortQuery());
    }

    @Test
    public void getMainTable() {
        Assert.assertEquals(Constants.TABLES.HIVST_REGISTER, baseHivstRegisterFragmentPresenter.getMainTable());
    }

    @Test
    public void initializeQueries() {
        Set<View> visibleColumns = new TreeSet<>();
        baseHivstRegisterFragmentPresenter.initializeQueries(null);
        Mockito.doNothing().when(view).initializeQueryParams("ec_hivst_confirmation", null, null);
        Mockito.verify(view).initializeQueryParams("ec_hivst_confirmation", null, null);
        Mockito.verify(view).initializeAdapter(visibleColumns);
        Mockito.verify(view).countExecute();
        Mockito.verify(view).filterandSortInInitializeQueries();
    }

}