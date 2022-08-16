package org.smartregister.chw.hivst.dao;

import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.dao.AbstractDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HivstDao extends AbstractDao {

    public static Date getHivstTestDate(String baseEntityID) {
        String sql = "select hivst_test_date from ec_hivst_confirmation where base_entity_id = '" + baseEntityID + "'";

        DataMap<Date> dataMap = cursor -> getCursorValueAsDate(cursor, "hivst_test_date", getNativeFormsDateFormat());

        List<Date> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }

    public static Date getHivstFollowUpVisitDate(String baseEntityID) {
        String sql = "SELECT eventDate FROM event where eventType ='Hivst Follow-up Visit' AND baseEntityId ='" + baseEntityID + "'";

        DataMap<Date> dataMap = cursor -> getCursorValueAsDate(cursor, "eventDate", getNativeFormsDateFormat());

        List<Date> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }

    public static void closeHivstMemberFromRegister(String baseEntityID) {
        String sql = "update ec_hivst_confirmation set is_closed = 1 where base_entity_id = '" + baseEntityID + "'";
        updateDB(sql);
    }

    public static boolean isRegisteredForHivst(String baseEntityID) {
        String sql = "SELECT count(p.base_entity_id) count FROM ec_hivst_register p " +
                "WHERE p.base_entity_id = '" + baseEntityID + "' AND p.is_closed = 0  ";

        DataMap<Integer> dataMap = cursor -> getCursorIntValue(cursor, "count");

        List<Integer> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return false;

        return res.get(0) > 0;
    }

    public static String getClientTestingHistory(String baseEntityId) {
        String sql = "Select client_testing_history from ec_hivst_register where base_entity_id = '" + baseEntityId + "'";
        DataMap<String> dataMap = cursor -> getCursorValue(cursor, "client_testing_history");
        List<String> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;
        return res.get(0);
    }

    public static String getClientGroup(String baseEntityId) {
        String sql = "Select client_group from ec_hivst_register where base_entity_id = '" + baseEntityId + "'";
        DataMap<String> dataMap = cursor -> getCursorValue(cursor, "client_group");
        List<String> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;
        return res.get(0);
    }

    public static MemberObject getMember(String baseEntityID) {
        String sql = "select m.base_entity_id, " +
                "       m.unique_id, " +
                "       m.relational_id, " +
                "       m.dob, " +
                "       m.first_name, " +
                "       m.middle_name, " +
                "       m.last_name, " +
                "       m.gender, " +
                "       m.phone_number, " +
                "       m.other_phone_number, " +
                "       f.first_name     family_name, " +
                "       f.primary_caregiver, " +
                "       f.family_head, " +
                "       f.village_town, " +
                "       fh.first_name    family_head_first_name, " +
                "       fh.middle_name   family_head_middle_name, " +
                "       fh.last_name     family_head_last_name, " +
                "       fh.phone_number  family_head_phone_number, " +
                "       ancr.is_closed   anc_is_closed, " +
                "       pncr.is_closed   pnc_is_closed, " +
                "       pcg.first_name   pcg_first_name, " +
                "       pcg.last_name    pcg_last_name, " +
                "       pcg.middle_name  pcg_middle_name, " +
                "       pcg.phone_number pcg_phone_number, " +
                "       mr.* " +
                "from ec_family_member m " +
                "         inner join ec_family f on m.relational_id = f.base_entity_id " +
                "         inner join ec_hivst_register mr on mr.base_entity_id = m.base_entity_id " +
                "         left join ec_family_member fh on fh.base_entity_id = f.family_head " +
                "         left join ec_family_member pcg on pcg.base_entity_id = f.primary_caregiver " +
                "         left join ec_anc_register ancr on ancr.base_entity_id = m.base_entity_id " +
                "         left join ec_pregnancy_outcome pncr on pncr.base_entity_id = m.base_entity_id " +
                "where m.base_entity_id = '" + baseEntityID + "' ";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        DataMap<MemberObject> dataMap = cursor -> {
            MemberObject memberObject = new MemberObject();

            memberObject.setFirstName(getCursorValue(cursor, "first_name", ""));
            memberObject.setMiddleName(getCursorValue(cursor, "middle_name", ""));
            memberObject.setLastName(getCursorValue(cursor, "last_name", ""));
            memberObject.setAddress(getCursorValue(cursor, "village_town"));
            memberObject.setGender(getCursorValue(cursor, "gender"));
            memberObject.setUniqueId(getCursorValue(cursor, "unique_id", ""));
            memberObject.setAge(getCursorValue(cursor, "dob"));
            memberObject.setFamilyBaseEntityId(getCursorValue(cursor, "relational_id", ""));
            memberObject.setRelationalId(getCursorValue(cursor, "relational_id", ""));
            memberObject.setPrimaryCareGiver(getCursorValue(cursor, "primary_caregiver"));
            memberObject.setFamilyName(getCursorValue(cursor, "family_name", ""));
            memberObject.setPhoneNumber(getCursorValue(cursor, "phone_number", ""));
            memberObject.setHivstTestDate(getCursorValueAsDate(cursor, "hivst_test_date", df));
            memberObject.setBaseEntityId(getCursorValue(cursor, "base_entity_id", ""));
            memberObject.setFamilyHead(getCursorValue(cursor, "family_head", ""));
            memberObject.setFamilyHeadPhoneNumber(getCursorValue(cursor, "pcg_phone_number", ""));
            memberObject.setFamilyHeadPhoneNumber(getCursorValue(cursor, "family_head_phone_number", ""));
            memberObject.setAncMember(getCursorValue(cursor, "anc_is_closed", ""));
            memberObject.setPncMember(getCursorValue(cursor, "pnc_is_closed", ""));

            String familyHeadName = getCursorValue(cursor, "family_head_first_name", "") + " "
                    + getCursorValue(cursor, "family_head_middle_name", "");

            familyHeadName =
                    (familyHeadName.trim() + " " + getCursorValue(cursor, "family_head_last_name", "")).trim();
            memberObject.setFamilyHeadName(familyHeadName);

            String familyPcgName = getCursorValue(cursor, "pcg_first_name", "") + " "
                    + getCursorValue(cursor, "pcg_middle_name", "");

            familyPcgName =
                    (familyPcgName.trim() + " " + getCursorValue(cursor, "pcg_last_name", "")).trim();
            memberObject.setPrimaryCareGiverName(familyPcgName);

            return memberObject;
        };

        List<MemberObject> res = readData(sql, dataMap);
        if (res == null || res.size() != 1)
            return null;

        return res.get(0);
    }
}
