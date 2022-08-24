package org.smartregister.chw.hivst.dao;

import org.smartregister.chw.hivst.model.HivstMobilizationModel;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.chw.hivst.util.DBConstants;
import org.smartregister.dao.AbstractDao;

import java.util.List;

public class HivstMobilizationDao extends AbstractDao {

    //TODO: Compute condoms issued
    public static List<HivstMobilizationModel> getMobilizationSessions() {
        String sql = "SELECT * FROM " + Constants.TABLES.HIVST_MOBILIZATION;

        AbstractDao.DataMap<HivstMobilizationModel> dataMap = cursor -> {
            HivstMobilizationModel hivstMobilizationModel = new HivstMobilizationModel();
            hivstMobilizationModel.setSessionId(cursor.getString(cursor.getColumnIndex(DBConstants.KEY.BASE_ENTITY_ID)));
            hivstMobilizationModel.setSessionDate(cursor.getString(cursor.getColumnIndex(Constants.FORM_SUBMISSION_FIELD.MOBILIZATION_DATE)));
            hivstMobilizationModel.setSessionParticipants(cursor.getString(cursor.getColumnIndex(Constants.FORM_SUBMISSION_FIELD.MOBILIZATION_PARTICIPANTS_NUMBER)));
            hivstMobilizationModel.setCondomsIssued(cursor.getString(cursor.getColumnIndex(Constants.FORM_SUBMISSION_FIELD.MOBILIZATION_MALE_CONDOMS_ISSUED)));

            return hivstMobilizationModel;
        };

        List<HivstMobilizationModel> res = readData(sql, dataMap);
        if (res == null || res.size() == 0)
            return null;
        return res;
    }

    //TODO Refactor query
    public static void updateData(String baseEntityID, String sbccDate, String locationType, String participantsNumber) {
        String sql = String.format("INSERT INTO ec_sbcc (id, sbcc_date, location_type, participants_number) VALUES ('%s', '%s', '%s', '%s') ON CONFLICT (id) DO UPDATE SET sbcc_date = '%s', location_type = '%s', participants_number = '%s'", baseEntityID, sbccDate, locationType, participantsNumber, sbccDate, locationType, participantsNumber);
        updateDB(sql);
    }
}
