package org.smartregister.chw.hivst.util;

public interface Constants {

    int REQUEST_CODE_GET_JSON = 2244;
    String ENCOUNTER_TYPE = "encounter_type";
    String STEP_ONE = "step1";
    String STEP_TWO = "step2";

    interface JSON_FORM_EXTRA {
        String JSON = "json";
        String ENCOUNTER_TYPE = "encounter_type";
    }

    interface EVENT_TYPE {
        String HIVST_REGISTRATION = "Self Testing Registration";
        String HIVST_ISSUE_KITS = "Self Testing Kits Issue";
        String HIVST_RESULTS = "Self Testing Results";
    }

    interface FORMS {
        String HIVST_REGISTRATION = "hivst_registration";
        String HIVST_ISSUE_KITS = "hivs_issue_kits";
        String HIVST_RECORD_RESULTS = "hivs_results";
        String HIVST_FOLLOW_UP_VISIT = "hivst_followup_visit";
    }

    interface TABLES {
        String HIVST_REGISTER = "ec_hivst_register";
        String HIVST_FOLLOWUP = "ec_hivst_followup";
        String HIVST_RESULTS = "ec_hivst_results";
    }

    interface ACTIVITY_PAYLOAD {
        String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        String FAMILY_BASE_ENTITY_ID = "FAMILY_BASE_ENTITY_ID";
        String ACTION = "ACTION";
        String HIVST_FORM_NAME = "HIVST_FORM_NAME";

    }

    interface ACTIVITY_PAYLOAD_TYPE {
        String REGISTRATION = "REGISTRATION";
        String FOLLOW_UP_VISIT = "FOLLOW_UP_VISIT";
    }

    interface CONFIGURATION {
        String HIVST_CONFIRMATION = "hivst_confirmation";
    }

    interface HIVST_MEMBER_OBJECT {
        String MEMBER_OBJECT = "memberObject";
    }

}