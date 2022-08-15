package org.smartregister.presenter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.chw.hivst.contract.HivstProfileContract;
import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.chw.hivst.presenter.BaseHivstProfilePresenter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BaseTestProfilePresenterHivst {

    @Mock
    private HivstProfileContract.View view = Mockito.mock(HivstProfileContract.View.class);

    @Mock
    private HivstProfileContract.Interactor interactor = Mockito.mock(HivstProfileContract.Interactor.class);

    @Mock
    private MemberObject memberObject = new MemberObject();

    private BaseHivstProfilePresenter profilePresenter = new BaseHivstProfilePresenter(view, interactor, memberObject);


    @Test
    public void fillProfileDataCallsSetProfileViewWithDataWhenPassedMemberObject() {
        profilePresenter.fillProfileData(memberObject);
        verify(view).setProfileViewWithData();
    }

    @Test
    public void fillProfileDataDoesntCallsSetProfileViewWithDataIfMemberObjectEmpty() {
        profilePresenter.fillProfileData(null);
        verify(view, never()).setProfileViewWithData();
    }

    @Test
    public void hivstTestDatePeriodIsLessThanSeven() {
        profilePresenter.recordHivstButton("");
        verify(view).hideView();
    }

    @Test
    public void hivstTestDatePeriodGreaterThanTen() {
        profilePresenter.recordHivstButton("OVERDUE");
        verify(view).setOverDueColor();
    }

    @Test
    public void hivstTestDatePeriodIsMoreThanFourteen() {
        profilePresenter.recordHivstButton("EXPIRED");
        verify(view).hideView();
    }

    @Test
    public void refreshProfileBottom() {
        profilePresenter.refreshProfileBottom();
        verify(interactor).refreshProfileInfo(memberObject, profilePresenter.getView());
    }

    @Test
    public void saveForm() {
        profilePresenter.saveForm(null);
        verify(interactor).saveRegistration(null, view);
    }
}
