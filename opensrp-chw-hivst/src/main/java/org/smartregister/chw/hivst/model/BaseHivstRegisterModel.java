package org.smartregister.chw.hivst.model;

import org.json.JSONObject;
import org.smartregister.chw.hivst.contract.HivstRegisterContract;
import org.smartregister.chw.hivst.util.HivstJsonFormUtils;

public class BaseHivstRegisterModel implements HivstRegisterContract.Model {

    @Override
    public JSONObject getFormAsJson(String formName, String entityId, String currentLocationId) throws Exception {
        JSONObject jsonObject = HivstJsonFormUtils.getFormAsJson(formName);
        HivstJsonFormUtils.getRegistrationForm(jsonObject, entityId, currentLocationId);

        return jsonObject;
    }

}
