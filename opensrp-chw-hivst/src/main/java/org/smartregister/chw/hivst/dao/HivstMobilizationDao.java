package org.smartregister.chw.hivst.dao;

import org.smartregister.chw.hivst.model.HivstMobilizationModel;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.chw.hivst.util.DBConstants;
import org.smartregister.dao.AbstractDao;

import java.util.List;


public class HivstMobilizationDao extends AbstractDao {

    public static void updateData(String baseEntityID, String mobilization_date, String female_clients_reached, String male_clients_reached, String male_condoms_issued, String female_condoms_issued) {
        String sql = "INSERT INTO ec_hivst_mobilization " +
                "           (id, mobilization_date, female_clients_reached, male_clients_reached, male_condoms_issued, female_condoms_issued) " +
                "           VALUES (" +
                "                   '" + baseEntityID + "', " +
                "                   '" + mobilization_date + "', " +
                "                   '" + female_clients_reached + "', " +
                "                   '" + male_clients_reached + "', " +
                "                   '" + male_condoms_issued + "', " +
                "                   '" + female_condoms_issued + "') " +
                "              " +
                " ON CONFLICT (id) DO UPDATE SET mobilization_date      = '" + mobilization_date + "',      " +
                "                               female_clients_reached = '" + female_clients_reached + "',  " +
                "                               male_clients_reached   = '" + male_clients_reached + "',    " +
                "                               male_condoms_issued    = '" + male_condoms_issued + "',     " +
                "                               female_condoms_issued  = '" + female_condoms_issued + "'    ";
        updateDB(sql);
    }

    public static List<HivstMobilizationModel> getMobilizationSessions() {
        String sql = "SELECT * FROM " + Constants.TABLES.HIVST_MOBILIZATION + " ORDER BY mobilization_date DESC";

        DataMap<HivstMobilizationModel> dataMap = cursor -> {
            HivstMobilizationModel hivstMobilizationModel = new HivstMobilizationModel();
            hivstMobilizationModel.setSessionId(cursor.getString(cursor.getColumnIndex(DBConstants.KEY.BASE_ENTITY_ID)));
            hivstMobilizationModel.setSessionDate(cursor.getString(cursor.getColumnIndex(DBConstants.KEY.MOBILIZATION_DATE)));
            hivstMobilizationModel.setSessionParticipants(computeSessionParticipants(cursor.getString(cursor.getColumnIndex(DBConstants.KEY.FEMALE_CLIENTS_REACHED)), cursor.getString(cursor.getColumnIndex(DBConstants.KEY.MALE_CLIENTS_REACHED))));
            hivstMobilizationModel.setCondomsIssued(computeCondomsIssued(cursor.getString(cursor.getColumnIndex(DBConstants.KEY.FEMALE_CONDOMS_ISSUED)), cursor.getString(cursor.getColumnIndex(DBConstants.KEY.MALE_CONDOMS_ISSUED))));
            return hivstMobilizationModel;
        };

        List<HivstMobilizationModel> res = readData(sql, dataMap);
        if (res == null || res.size() == 0)
            return null;
        return res;
    }

    private static String computeSessionParticipants(String femaleParticipants, String maleParticipants) {
        int sum = Integer.parseInt(femaleParticipants) + Integer.parseInt(maleParticipants);
        return String.valueOf(sum);
    }

    private static String computeCondomsIssued(String femaleCondoms, String maleCondoms) {
        int sum = Integer.parseInt(femaleCondoms) + Integer.parseInt(maleCondoms);
        return String.valueOf(sum);
    }


}
