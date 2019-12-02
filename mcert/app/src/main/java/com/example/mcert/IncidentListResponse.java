package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IncidentListResponse extends AppCompatActivity {

    private Model incident_model;
    private IncidentListAdapter incidentServer = null;
    private RecyclerView incidentrecycler = null;
    private GestureDetectorCompat gesture_detector = null;
    static List<Model> incident_array = new ArrayList<>();

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = incidentrecycler.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = incidentrecycler.getChildViewHolder(view);
                if (holder instanceof IncidentListAdapter.IncidentViewHolder
                ) {
                    int position = holder.getAdapterPosition();

                    // handle single tap
                    Log.d("click", "clicked on item "+ position);
//                    TextView outputTV = findViewById(R.id.outputTV);
//                    outputTV.setText("Clicked on " + myModel.thePlanets.get(position).name);
//                    // Remove the selected data from the model
//                    myModel.thePlanets.remove(position);
//                    planetServer.notifyItemRemoved(position);

                    return true;  // Use up the tap gesture
                }
            }

            // we didn't handle the gesture so pass it on
            return false;
        }
    }

    public void onSubmit(View v){
        Intent ini = new Intent(this, TabsActivity.class);
        startActivity(ini);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_list_response);
        incident_model = Model.getModel();
        incidentServer = new IncidentListAdapter(incident_model);
        incidentrecycler = findViewById(R.id.IncidentRecycler);
        incidentrecycler.setAdapter(incidentServer);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        incidentrecycler.setLayoutManager(linearLayoutManager);

        gesture_detector = new GestureDetectorCompat(this, new RecyclerViewOnGestureListener());

        incidentrecycler.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return gesture_detector.onTouchEvent(e);
            }
        });


        try {
            //JSONObject jsonObj = new JSONObject(TabsActivity.response_incident_api);
            JSONArray jsonArray = new JSONArray(TabsActivity.response_incident_api);
            //JSONArray data = perdata.getJSONArray("results");
            Log.d("lolraja",jsonArray.toString() );
            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String incident_name = jsonObj.getString("incidentName");
                String date_time = jsonObj.getString("dateAndTime");

                incident_model.getIncidentsarray().add(new Model.Incident(incident_name + " " + date_time));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("lolraja",e.toString() );
        }

    }
}
