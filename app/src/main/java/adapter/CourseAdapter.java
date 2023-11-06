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

    public CourseAdapter(Context context, ArrayList<String> courses) {
        super(context, 0, courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list, parent, false);
        }

        // Get the data item for this position
        String course = getItem(position);

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.txtVName);

        // Populate the data into the template view using the data object
        tvName.setText(course);

        // Return the completed view to render on screen
        return convertView;
    }



}
