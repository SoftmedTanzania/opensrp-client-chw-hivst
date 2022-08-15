package org.smartregister.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.hivst.model.BaseHivstRegisterFragmentModel;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;

import java.util.HashSet;
import java.util.Set;

public class BaseHivstRegisterFragmentModelHivst {

    @Mock
    private BaseHivstRegisterFragmentModel baseHivstRegisterFragmentModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDefaultRegisterConfiguration() {
        RegisterConfiguration configuration = new RegisterConfiguration();
        Mockito.when(baseHivstRegisterFragmentModel.defaultRegisterConfiguration())
                .thenReturn(configuration);
        Assert.assertEquals(configuration, baseHivstRegisterFragmentModel.defaultRegisterConfiguration());
    }

    @Test
    public void testGetViewConfiguration() {
        ViewConfiguration viewConfiguration = new ViewConfiguration();
        Mockito.when(baseHivstRegisterFragmentModel.getViewConfiguration(Mockito.anyString()))
                .thenReturn(viewConfiguration);
        Assert.assertEquals(viewConfiguration, baseHivstRegisterFragmentModel.getViewConfiguration(Mockito.anyString()));
    }

    @Test
    public void testGetRegisterActiveColumns() {
        Set<View> views = new HashSet<View>();
        Mockito.when(baseHivstRegisterFragmentModel.getRegisterActiveColumns(Mockito.anyString()))
                .thenReturn(views);
        Assert.assertEquals(views, baseHivstRegisterFragmentModel.getRegisterActiveColumns(Mockito.anyString()));
    }

    @Test
    public void testCountSelect() {
        String countString = "0";
        Mockito.when(baseHivstRegisterFragmentModel.countSelect(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(countString);
        Assert.assertEquals(countString, baseHivstRegisterFragmentModel.countSelect(Mockito.anyString(), Mockito.anyString()));
    }

    @Test
    public void testMainSelect() {
        String countString = "mainSelect";
        Mockito.when(baseHivstRegisterFragmentModel.mainSelect(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(countString);
        Assert.assertEquals(countString, baseHivstRegisterFragmentModel.mainSelect(Mockito.anyString(), Mockito.anyString()));
    }

}
