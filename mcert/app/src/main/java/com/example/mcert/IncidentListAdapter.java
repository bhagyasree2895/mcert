package com.example.mcert;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IncidentListAdapter  extends RecyclerView.Adapter<IncidentListAdapter.IncidentViewHolder> {
    private Model incident_model;

    public IncidentListAdapter(Model incident_model) {
        super();
        this.incident_model = incident_model;
    }

    public static class IncidentViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearView;

        public IncidentViewHolder(LinearLayout lin_layout) {
            super(lin_layout);
            linearView = lin_layout;
        }
    }



    @NonNull
    @Override
    public IncidentListAdapter.IncidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout lin_layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_view, parent, false);
        IncidentViewHolder linviewhol = new IncidentViewHolder(lin_layout);
        return linviewhol;
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentListAdapter.IncidentViewHolder holder, int position) {
        LinearLayout hold_view = holder.linearView;
        TextView word_holder = hold_view.findViewById(R.id.incident_holder);
        word_holder.setText(incident_model.getIncidentsarray().get(position).Incident);
    }

    @Override
    public int getItemCount() {
        return incident_model.getIncidentsarray().size();
    }
}
