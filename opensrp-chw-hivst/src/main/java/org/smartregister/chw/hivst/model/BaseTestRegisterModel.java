package org.smartregister.chw.hivst.model;

import org.json.JSONObject;
import org.smartregister.chw.hivst.contract.TestRegisterContract;
import org.smartregister.chw.hivst.util.TestJsonFormUtils;

public class BaseTestRegisterModel implements TestRegisterContract.Model {

    @Override
    public JSONObject getFormAsJson(String formName, String entityId, String currentLocationId) throws Exception {
        JSONObject jsonObject = TestJsonFormUtils.getFormAsJson(formName);
        TestJsonFormUtils.getRegistrationForm(jsonObject, entityId, currentLocationId);

        return jsonObject;
    }

}
