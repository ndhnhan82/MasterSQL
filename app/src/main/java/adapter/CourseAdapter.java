package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mastersql.R;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<String> {

    private int viewType;


    public CourseAdapter(Context context, ArrayList<String> courses, int viewType)
    {
        super(context, 0, courses);

        this.viewType = viewType;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            switch (viewType) {
                case 1: // For course view
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list, parent, false);
                    break;
                case 2: // For another view
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.answers_list, parent, false);
                    break;


            }
        }

        // Get the data item for this position
        String course = getItem(position);

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.txtVName);

        // Populate the data into the template view using the data object
        tvName.setText(course);
        return convertView;
    }



}

