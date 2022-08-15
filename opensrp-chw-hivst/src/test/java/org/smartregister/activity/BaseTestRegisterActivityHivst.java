package org.smartregister.activity;

import android.content.Intent;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.hivst.activity.BaseHivstRegisterActivity;

public class BaseTestRegisterActivityHivst {
    @Mock
    public Intent data;
    @Mock
    private BaseHivstRegisterActivity baseHivstRegisterActivity = new BaseHivstRegisterActivity();

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseHivstRegisterActivity);
    }

    @Test
    public void testFormConfig() {
        Assert.assertNull(baseHivstRegisterActivity.getFormConfig());
    }

    @Test
    public void checkIdentifier() {
        Assert.assertNotNull(baseHivstRegisterActivity.getViewIdentifiers());
    }

    @Test(expected = Exception.class)
    public void onActivityResult() throws Exception {
        Whitebox.invokeMethod(baseHivstRegisterActivity, "onActivityResult", 2244, -1, data);
        Mockito.verify(baseHivstRegisterActivity.presenter()).saveForm(null);
    }

}
