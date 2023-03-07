package com.CC4mpbe11.university.RecyclerViewAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.DetailsScreens.TermDetails;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.PrimaryActivities.TermCourseActivity;
import com.CC4mpbe11.university.R;

import java.util.List;

public class TermsRecAdapter extends RecyclerView.Adapter<TermsRecAdapter.TermsViewHolder> {

    class TermsViewHolder extends RecyclerView.ViewHolder {

        private final TextView coursesText;
        private final TextView termText;
        private final TextView termDate;

        private TermsViewHolder(View itemView) {
            super(itemView);
            coursesText = itemView.findViewById(R.id.coursesText);
            termText = itemView.findViewById(R.id.termText);
            termDate = itemView.findViewById(R.id.termDate);


            itemView.setOnClickListener(v -> {
                if(recVersion.equals("Non Edit")) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, TermCourseActivity.class);
                    intent.putExtra("termNumber", current.getTermNumber());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("position", position);
                    intent.putExtra("type", "newTerm");
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
                else if (recVersion.equals("Edit")){
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termNumber", current.getTermNumber());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termStart", current.getTermStart());
                    intent.putExtra("termEnd", current.getTermEnd());
                    intent.putExtra("position", position);
                    intent.putExtra("type", "existingTerm");
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
                else{
                    System.out.println("Error with TermsRecAdapter.");
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerms;
    private List<CourseEntity> allCourses;
    private String associatedCourses = "";
    private final String recVersion;

    public TermsRecAdapter (Context context, String recVersion) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recVersion = recVersion;
    }

    @NonNull
    @Override
    public TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.terms_row, parent, false);
        return new TermsViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermsViewHolder holder, final int position) {

        if(mTerms != null) {
            final TermEntity current = mTerms.get(position);
            holder.termText.setText(current.getTermNumber() + "");
            holder.termDate.setText(current.getTermStartMmDdYy() + " - " + current.getTermEndMmDdYy());
            for (CourseEntity course : allCourses) {
                if (course.getTermID() == current.getTermID()) {
                    associatedCourses = associatedCourses + course.getCourseCode() + ":  " + course.getCourseName() + "\n";
                }
                holder.coursesText.setText(associatedCourses);
            }
            associatedCourses = "";
        }
        else {
            holder.termText.setText("No Courses Added!");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTermWords(List<TermEntity> termWords) {
        mTerms = termWords;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourseWords(List<CourseEntity> courseWords) {
        allCourses = courseWords;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }

    /*@Override
    public int getItemCount() {
        if(data1 != null){
           return data1.length;}
        return 0;
    }*/

   /* public class TermsViewHolder extends RecyclerView.ViewHolder{

        TextView termText, courseText;
        ConstraintLayout termsLayout;

        public TermsViewHolder(@NonNull View itemView) {
            super(itemView);
            termText = itemView.findViewById(R.id.termText);
            courseText = itemView.findViewById(R.id.coursesText);
            termsLayout = itemView.findViewById(R.id.termsLayout);
        }
    }*/
}
