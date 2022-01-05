package com.example.emargementnfc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ExamSessionActivity extends AppCompatActivity {

    private final static int CREATE_CODE = 0;

    private DBMain dbHandler;
    ListView examSessionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_session);

        this.dbHandler = new DBMain(getApplicationContext());
        this.updateListView();
    }

    public void updateListView() {
        List<ExamSession> esList = this.dbHandler.getAllExamSession();

        this.examSessionListView = findViewById(R.id.highscores_list_view);
        this.examSessionListView.setAdapter(new ExamSessionItemAdaptater(this, esList));
    }

    public void deleteToast() {
        Toast.makeText(this, "Delete success", Toast.LENGTH_LONG).show();
    }

    public void createNewExamSession(View view) {
        Intent settingsIntent = new Intent(this, CreateExamSessionActivity.class);
        startActivityForResult(settingsIntent, CREATE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)	{
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CREATE_CODE:
                    this.updateListView();
                    break;
            }
        }
    }

    class ExamSessionItemAdaptater extends BaseAdapter {

        private Context context;
        private List<ExamSession> esList;
        private LayoutInflater inflater;

        public ExamSessionItemAdaptater(Context context, List<ExamSession> esList) {
            this.context = context;
            this.esList = esList;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return this.esList.size();
        }

        @Override
        public ExamSession getItem(int position) {
            return this.esList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inflater.inflate(R.layout.exam_session_item, null);

            ExamSession currentItem = this.getItem(position);

            TextView nameView = view.findViewById(R.id.es_name);
            nameView.setText(currentItem.getName());

            TextView dateView = view.findViewById(R.id.es_date);
            dateView.setText(currentItem.getDate());

            TextView startHourView = view.findViewById(R.id.es_startDate);
            startHourView.setText(currentItem.getStartHour());

            TextView endHourView = view.findViewById(R.id.es_endDate);
            endHourView.setText(currentItem.getEndHour());

            Button delButton = view.findViewById(R.id.es_delete);
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteExamSession(currentItem.getId());
                    deleteToast();
                    updateListView();
                }
            });

            return view;
        }
    }

    public void quit(View view) {
        this.finish();
    }
}