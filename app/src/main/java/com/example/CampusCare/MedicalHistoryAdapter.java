package com.example.campuscare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MedicalHistoryAdapter extends RecyclerView.Adapter<MedicalHistoryAdapter.ViewHolder> {

    private final List<MedicalHistoryItem> medicalHistoryList;

    public MedicalHistoryAdapter(List<MedicalHistoryItem> medicalHistoryList) {
        this.medicalHistoryList = medicalHistoryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;

        public ViewHolder(View view) {
            super(view);
            tvInfo = view.findViewById(R.id.tvInfo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medical_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicalHistoryItem item = medicalHistoryList.get(position);
        String info = "Name: " + item.name + "\n"
                + "DOB: " + item.birthDate + "\n"
                + "Blood Type: " + item.bloodType + "\n"
                + "Condition: " + item.medicalCondition + "\n"
                + "Allergies: " + item.allergies + "\n"
                + "Medications: " + item.medications;
        holder.tvInfo.setText(info);
    }

    @Override
    public int getItemCount() {
        return medicalHistoryList.size();
    }
}
