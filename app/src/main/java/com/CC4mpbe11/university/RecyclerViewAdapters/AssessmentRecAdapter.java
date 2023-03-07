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

import com.CC4mpbe11.university.DetailsScreens.AssessmentDetails;
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.R;

import java.util.List;

public class AssessmentRecAdapter extends RecyclerView.Adapter<AssessmentRecAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {


        private final TextView assessmentDisplayCode;
        private final TextView assessmentDisplayDatetime;
        private final TextView assessmentDisplayType;
        private final TextView assessmentAssociatedCourse;
        private final TextView assessmentDisplayName;
        private final TextView assessmentDisplayVendor;

        private AssessmentViewHolder(View itemView) {
            super(itemView);

            assessmentDisplayCode = itemView.findViewById(R.id.assessmentDisplayCode);
            assessmentDisplayDatetime = itemView.findViewById(R.id.assessmentDisplayDatetime);
            assessmentDisplayType = itemView.findViewById(R.id.assessmentDisplayType);
            assessmentAssociatedCourse = itemView.findViewById(R.id.assessmentAssociatedCourse);
            assessmentDisplayName = itemView.findViewById(R.id.assessmentDisplayName);
            assessmentDisplayVendor = itemView.findViewById(R.id.assessmentDisplayVendor);

            itemView.setOnClickListener(v -> {
                String courseName = "";
                int courseID = 0;
                int position = getAdapterPosition();
                final AssessmentEntity currentAssessment = mAssessments.get(position);
                for (CourseEntity currentCourse : mCourses){
                    if (currentAssessment.getCourseID() == currentCourse.getCourseID()){
                        courseName = currentCourse.getCourseName();
                        courseID = currentCourse.getCourseID();
                    }
                }
                Intent intent = new Intent(context,  AssessmentDetails.class);
                intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                intent.putExtra("assessmentCode", currentAssessment.getAssessmentCode());
                intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
                intent.putExtra("assessmentType", currentAssessment.getAssessmentType());
                intent.putExtra("assessmentVendor", currentAssessment.getAssessmentVendor());
                intent.putExtra("assessmentTime", currentAssessment.getAssessmentStartHhMm());
                intent.putExtra("assessmentDate", currentAssessment.getAssessmentDateMmDdYy());
                intent.putExtra("associatedCourse", courseID);
                intent.putExtra("associatedCourseName", courseName);
                intent.putExtra("type", "existingAssessment");
                context.startActivity(intent);
                ((Activity)context).finish();
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;
    private List<CourseEntity> mCourses;

    public AssessmentRecAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_row, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            String courseName = "";
            final AssessmentEntity currentAssessment = mAssessments.get(position);
            for (CourseEntity currentCourse : mCourses){
                if (currentAssessment.getCourseID() == currentCourse.getCourseID()){
                    courseName = currentCourse.getCourseName();
                }
            }
            //final AssessmentEntity currentAssessment = mAssessments.get(position);
            holder.assessmentDisplayCode.setText(currentAssessment.getAssessmentCode());
            holder.assessmentDisplayDatetime.setText(currentAssessment.getAssessmentDateMmDdYy());
            holder.assessmentDisplayType.setText(currentAssessment.getAssessmentType());
            holder.assessmentAssociatedCourse.setText(courseName);
            holder.assessmentDisplayName.setText(currentAssessment.getAssessmentName());
            holder.assessmentDisplayVendor.setText(currentAssessment.getAssessmentVendor());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourseWords(List<CourseEntity> courseWords) {
        this.mCourses = courseWords;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAssessmentWords(List<AssessmentEntity> assessmentWords) {
        this.mAssessments = assessmentWords;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }

}
