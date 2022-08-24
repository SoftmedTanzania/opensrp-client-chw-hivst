package org.smartregister.chw.hivst.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.hivst.model.HivstMobilizationModel;
import org.smartregister.hivst.R;

import java.util.List;

public class HivstMobilizationRegisterAdapter extends RecyclerView.Adapter<HivstMobilizationRegisterAdapter.HivstMobilzationViewHolder> {

    private final Context context;
    private final List<HivstMobilizationModel> hivstMobilizationModels;


    public HivstMobilizationRegisterAdapter(List<HivstMobilizationModel> hivstMobilizationModels, Context context) {
        this.hivstMobilizationModels = hivstMobilizationModels;
        this.context = context;
    }

    @NonNull
    @Override
    public HivstMobilzationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View followupLayout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hivst_mobilization_session_card_view, viewGroup, false);
        return new HivstMobilzationViewHolder(followupLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull HivstMobilzationViewHolder holder, int position) {
        HivstMobilizationModel hivstMobilizationModel = hivstMobilizationModels.get(position);
        holder.bindData(hivstMobilizationModel);
    }

    @Override
    public int getItemCount() {
        return hivstMobilizationModels.size();
    }

    protected class HivstMobilzationViewHolder extends RecyclerView.ViewHolder {
        public TextView mobilizationSessionDate;
        public TextView mobilizationSessionParticipants;
        public TextView mobilizationSessionCondomsIssued;

        public HivstMobilzationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(HivstMobilizationModel hivstMobilizationModel) {
            mobilizationSessionDate = itemView.findViewById(R.id.mobilization_session_date);
            mobilizationSessionParticipants = itemView.findViewById(R.id.mobilization_session_condoms_issued);
            mobilizationSessionCondomsIssued = itemView.findViewById(R.id.mobilization_session_condoms_issued);

            mobilizationSessionDate.setText(context.getString(R.string.mobilziation_session_date, hivstMobilizationModel.getSessionDate()));
            mobilizationSessionParticipants.setText(context.getString(R.string.mobilization_session_participants, hivstMobilizationModel.getSessionParticipants()));
            mobilizationSessionCondomsIssued.setText(context.getString(R.string.mobilization_condoms_issued, hivstMobilizationModel.getCondomsIssued()));
        }
    }
}
