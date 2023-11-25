package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mastersql.R;

import java.util.ArrayList;

import model.CategoryProgress;

public class ProgressAdapter extends ArrayAdapter<CategoryProgress> {



    public static final int USER_DASHBOARD_LIST = 1;

    private int viewType;


    public ProgressAdapter(Context context, ArrayList<CategoryProgress> courses) {
        super(context, 0, courses);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.user_dashboard_list, parent, false);
        }

        CategoryProgress progress = getItem(position);
        TextView tvName = convertView.findViewById(R.id.txtVName);
        ProgressBar pbCourseProgress = convertView.findViewById(R.id.pbCircular);

        tvName.setText(progress.getName() + " - Completed: " + progress.getCompleted() + "/" + progress.getTotal());
        pbCourseProgress.setMax(progress.getTotal());
        pbCourseProgress.setProgress(progress.getCompleted());

        return convertView;
    }

}
