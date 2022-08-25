package org.smartregister.chw.hivst.util;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.hivst.HivstLibrary;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.clientandeventmodel.Obs;
import org.smartregister.domain.tag.FormTag;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.util.FormUtils;
import org.smartregister.util.JsonFormUtils;

import java.util.ArrayList;
import java.util.Date;

import timber.log.Timber;

import static org.smartregister.chw.hivst.util.Constants.ENCOUNTER_TYPE;
import static org.smartregister.chw.hivst.util.Constants.STEP_ONE;
import static org.smartregister.chw.hivst.util.Constants.STEP_TWO;

public class HivstJsonFormUtils extends org.smartregister.util.JsonFormUtils {
    public static final String METADATA = "metadata";

    public static Triple<Boolean, JSONObject, JSONArray> validateParameters(String jsonString) {

        JSONObject jsonForm = toJSONObject(jsonString);
        JSONArray fields = hivstFormFields(jsonForm);

        Triple<Boolean, JSONObject, JSONArray> registrationFormParams = Triple.of(jsonForm != null && fields != null, jsonForm, fields);
        return registrationFormParams;
    }

    public static JSONArray hivstFormFields(JSONObject jsonForm) {
        try {
            JSONArray fieldsOne = fields(jsonForm, STEP_ONE);
            JSONArray fieldsTwo = fields(jsonForm, STEP_TWO);
            if (fieldsTwo != null) {
                for (int i = 0; i < fieldsTwo.length(); i++) {
                    fieldsOne.put(fieldsTwo.get(i));
                }
            }
            return fieldsOne;

        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
        return null;
    }

    public static JSONArray fields(JSONObject jsonForm, String step) {
        try {

            JSONObject step1 = jsonForm.has(step) ? jsonForm.getJSONObject(step) : null;
            if (step1 == null) {
                return null;
            }

            return step1.has(FIELDS) ? step1.getJSONArray(FIELDS) : null;

        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
        return null;
    }

    public static Event processJsonForm(AllSharedPreferences allSharedPreferences, String
            jsonString) {

        Triple<Boolean, JSONObject, JSONArray> registrationFormParams = validateParameters(jsonString);

        if (!registrationFormParams.getLeft()) {
            return null;
        }

        JSONObject jsonForm = registrationFormParams.getMiddle();
        JSONArray fields = registrationFormParams.getRight();
        String entityId = getString(jsonForm, ENTITY_ID);
        String encounter_type = jsonForm.optString(Constants.JSON_FORM_EXTRA.ENCOUNTER_TYPE);

        if (Constants.EVENT_TYPE.HIVST_REGISTRATION.equals(encounter_type)) {
            encounter_type = Constants.TABLES.HIVST_REGISTER;
        } else if (Constants.EVENT_TYPE.HIVST_ISSUE_KITS.equals(encounter_type)) {
            encounter_type = Constants.TABLES.HIVST_FOLLOWUP;
            try {
                boolean selfTestKitGiven = false;
                boolean extraKits = false;
                JSONObject selfTestKitGivenObj = getFieldJSONObject(fields(jsonForm, STEP_ONE), "self_test_kit_given");
                JSONObject extraKitsGivenObj = getFieldJSONObject(fields(jsonForm, STEP_ONE), "extra_kits_required");
                if (selfTestKitGivenObj != null) {
                    boolean isVisible = selfTestKitGivenObj.getBoolean("is_visible");
                    if (isVisible)
                        selfTestKitGiven = selfTestKitGivenObj.getString("value").equalsIgnoreCase("yes");
                }
                if (extraKitsGivenObj != null) {
                    boolean isVisible = extraKitsGivenObj.getBoolean("is_visible");
                    if (isVisible)
                        extraKits = extraKitsGivenObj.getString("value").equalsIgnoreCase("yes");
                }
                if (selfTestKitGiven) {
                    createHivstResultRegistrationEvent(jsonForm, true, extraKits, entityId, allSharedPreferences);
                }
                if (extraKits) {
                    createHivstResultRegistrationEvent(jsonForm, selfTestKitGiven, true, entityId, allSharedPreferences);
                }
            } catch (Exception e) {
                Timber.e(e);
            }
        } else if (Constants.EVENT_TYPE.HIVST_RESULTS.equals(encounter_type)) {
            encounter_type = Constants.TABLES.HIVST_RESULTS;
        }
        return org.smartregister.util.JsonFormUtils.createEvent(fields, getJSONObject(jsonForm, METADATA), formTag(allSharedPreferences), entityId, getString(jsonForm, ENCOUNTER_TYPE), encounter_type);
    }

    private static void createHivstResultRegistrationEvent(JSONObject jsonForm, boolean clientKitGiven, boolean extraKits, String entityId, AllSharedPreferences allSharedPreferences) throws Exception {
        if (clientKitGiven) {
            String kitCode = getFieldJSONObject(fields(jsonForm, STEP_ONE), "kit_code").optString("value", "");
            processRegistrationResultForClient(entityId, allSharedPreferences, kitCode, "client");
        }
        if (extraKits) {
            JSONArray vals = getFieldJSONObject(fields(jsonForm, STEP_ONE), "extra_kits_issued_for").getJSONArray("value");
            String kitCodeForPartner = getFieldJSONObject(fields(jsonForm, STEP_ONE), "sexual_partner_kit_code").optString("value", "");
            String kitCodeForPeer = getFieldJSONObject(fields(jsonForm, STEP_ONE), "peer_friend_kit_code").optString("value", "");

            for (int i = 0; i < vals.length(); i++) {
                String kitFor = vals.get(i).toString();
                if (kitFor.equalsIgnoreCase("sexual_partner")) {
                    processRegistrationResultForClient(entityId, allSharedPreferences, kitCodeForPartner, "sexual_partner");
                } else if (kitFor.equalsIgnoreCase("peer_friend")) {
                    processRegistrationResultForClient(entityId, allSharedPreferences, kitCodeForPeer, "peer_friend");
                }
            }
        }
    }

    private static void processRegistrationResultForClient(String entityId, AllSharedPreferences allSharedPreferences, String kitCode, String kitFor) {
        Event baseEvent = getBaseEvent(entityId, allSharedPreferences);
        baseEvent.addObs(new Obs().withFormSubmissionField("kit_for").withValue(kitFor)
                .withFieldCode("kit_for").withFieldType("formsubmissionField").withFieldDataType("text").withParentCode("").withHumanReadableValues(new ArrayList<>()));
        baseEvent.addObs(new Obs().withFormSubmissionField("kit_code").withValue(kitCode)
                .withFieldCode("kit_code").withFieldType("formsubmissionField").withFieldDataType("text").withParentCode("").withHumanReadableValues(new ArrayList<>()));
        try {
            HivstUtil.processEvent(allSharedPreferences, baseEvent);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private static Event getBaseEvent(String entityId, AllSharedPreferences allSharedPreferences) {
        return (Event) new Event()
                .withBaseEntityId(entityId)
                .withEventDate(new Date())
                .withFormSubmissionId(JsonFormUtils.generateRandomUUIDString())
                .withEventType(Constants.EVENT_TYPE.HIVST_RESULTS_REGISTRATION)
                .withEntityType(Constants.TABLES.HIVST_RESULTS)
                .withProviderId(allSharedPreferences.fetchRegisteredANM())
                .withLocationId(allSharedPreferences.fetchDefaultLocalityId(allSharedPreferences.fetchRegisteredANM()))
                .withTeamId(allSharedPreferences.fetchDefaultTeamId(allSharedPreferences.fetchRegisteredANM()))
                .withTeam(allSharedPreferences.fetchDefaultTeam(allSharedPreferences.fetchRegisteredANM()))
                .withClientDatabaseVersion(HivstLibrary.getInstance().getDatabaseVersion())
                .withClientApplicationVersion(HivstLibrary.getInstance().getApplicationVersion())
                .withDateCreated(new Date());
    }


    protected static FormTag formTag(AllSharedPreferences allSharedPreferences) {
        FormTag formTag = new FormTag();
        formTag.providerId = allSharedPreferences.fetchRegisteredANM();
        formTag.appVersion = HivstLibrary.getInstance().getApplicationVersion();
        formTag.databaseVersion = HivstLibrary.getInstance().getDatabaseVersion();
        return formTag;
    }

    public static void tagEvent(AllSharedPreferences allSharedPreferences, Event event) {
        String providerId = allSharedPreferences.fetchRegisteredANM();
        event.setProviderId(providerId);
        event.setLocationId(locationId(allSharedPreferences));
        event.setChildLocationId(allSharedPreferences.fetchCurrentLocality());
        event.setTeam(allSharedPreferences.fetchDefaultTeam(providerId));
        event.setTeamId(allSharedPreferences.fetchDefaultTeamId(providerId));

        event.setClientApplicationVersion(HivstLibrary.getInstance().getApplicationVersion());
        event.setClientDatabaseVersion(HivstLibrary.getInstance().getDatabaseVersion());
    }

    private static String locationId(AllSharedPreferences allSharedPreferences) {
        String providerId = allSharedPreferences.fetchRegisteredANM();
        String userLocationId = allSharedPreferences.fetchUserLocalityId(providerId);
        if (StringUtils.isBlank(userLocationId)) {
            userLocationId = allSharedPreferences.fetchDefaultLocalityId(providerId);
        }

        return userLocationId;
    }

    public static void getRegistrationForm(JSONObject jsonObject, String entityId, String
            currentLocationId) throws JSONException {
        jsonObject.getJSONObject(METADATA).put(ENCOUNTER_LOCATION, currentLocationId);
        jsonObject.put(org.smartregister.util.JsonFormUtils.ENTITY_ID, entityId);
        jsonObject.put(DBConstants.KEY.RELATIONAL_ID, entityId);
    }

    public static JSONObject getFormAsJson(String formName) throws Exception {
        return FormUtils.getInstance(HivstLibrary.getInstance().context().applicationContext()).getFormJson(formName);
    }

}
