package org.smartregister.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.hivst.activity.BaseHivstProfileActivity;
import org.smartregister.chw.hivst.contract.HivstProfileContract;
import org.smartregister.domain.AlertStatus;
import org.smartregister.hivst.R;

import static org.mockito.Mockito.validateMockitoUsage;

public class BaseTestProfileActivityHivst {
    @Mock
    public BaseHivstProfileActivity baseHivstProfileActivity;

    @Mock
    public HivstProfileContract.Presenter profilePresenter;

    @Mock
    public View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseHivstProfileActivity);
    }

    @Test
    public void setOverDueColor() {
        baseHivstProfileActivity.setOverDueColor();
        Mockito.verify(view, Mockito.never()).setBackgroundColor(Color.RED);
    }

    @Test
    public void formatTime() {
        BaseHivstProfileActivity activity = new BaseHivstProfileActivity();
        try {
            Assert.assertEquals("25 Oct 2019", Whitebox.invokeMethod(activity, "formatTime", "25-10-2019"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkHideView() {
        baseHivstProfileActivity.hideView();
        Mockito.verify(view, Mockito.never()).setVisibility(View.GONE);
    }

    @Test
    public void checkProgressBar() {
        baseHivstProfileActivity.showProgressBar(true);
        Mockito.verify(view, Mockito.never()).setVisibility(View.VISIBLE);
    }

    @Test
    public void medicalHistoryRefresh() {
        baseHivstProfileActivity.refreshMedicalHistory(true);
        Mockito.verify(view, Mockito.never()).setVisibility(View.VISIBLE);
    }

    @Test
    public void onClickBackPressed() {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.title_layout);
        Mockito.doNothing().when(baseHivstProfileActivity).onBackPressed();
        baseHivstProfileActivity.onClick(view);
        Mockito.verify(baseHivstProfileActivity).onBackPressed();
    }

    @Test
    public void onClickOpenMedicalHistory() {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.rlLastVisit);
        Mockito.doNothing().when(baseHivstProfileActivity).openMedicalHistory();
        baseHivstProfileActivity.onClick(view);
        Mockito.verify(baseHivstProfileActivity).openMedicalHistory();
    }

    @Test
    public void onClickOpenUpcomingServices() {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.rlUpcomingServices);
        Mockito.doNothing().when(baseHivstProfileActivity).openUpcomingService();
        baseHivstProfileActivity.onClick(view);
        Mockito.verify(baseHivstProfileActivity).openUpcomingService();
    }

    @Test
    public void onClickOpenFamlilyServicesDue() {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.rlFamilyServicesDue);
        Mockito.doNothing().when(baseHivstProfileActivity).openFamilyDueServices();
        baseHivstProfileActivity.onClick(view);
        Mockito.verify(baseHivstProfileActivity).openFamilyDueServices();
    }

    @Test(expected = Exception.class)
    public void refreshFamilyStatusComplete() throws Exception {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        TextView textView = view.findViewById(R.id.textview_family_has);
        Whitebox.setInternalState(baseHivstProfileActivity, "tvFamilyStatus", textView);
        Mockito.doNothing().when(baseHivstProfileActivity).showProgressBar(false);
        baseHivstProfileActivity.refreshFamilyStatus(AlertStatus.complete);
        Mockito.verify(baseHivstProfileActivity).showProgressBar(false);
        PowerMockito.verifyPrivate(baseHivstProfileActivity).invoke("setFamilyStatus", "Family has nothing due");
    }

    @Test(expected = Exception.class)
    public void refreshFamilyStatusNormal() throws Exception {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        TextView textView = view.findViewById(R.id.textview_family_has);
        Whitebox.setInternalState(baseHivstProfileActivity, "tvFamilyStatus", textView);
        Mockito.doNothing().when(baseHivstProfileActivity).showProgressBar(false);
        baseHivstProfileActivity.refreshFamilyStatus(AlertStatus.complete);
        Mockito.verify(baseHivstProfileActivity).showProgressBar(false);
        PowerMockito.verifyPrivate(baseHivstProfileActivity).invoke("setFamilyStatus", "Family has services due");
    }

    @Test(expected = Exception.class)
    public void onActivityResult() throws Exception {
        baseHivstProfileActivity = Mockito.spy(new BaseHivstProfileActivity());
        Whitebox.invokeMethod(baseHivstProfileActivity, "onActivityResult", 2244, -1, null);
        Mockito.verify(profilePresenter).saveForm(null);
    }

}
