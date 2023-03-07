package com.CC4mpbe11.university.RecyclerViewAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.DetailsScreens.CourseDetails;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.R;

import java.util.List;

// Polymorphism and inheritance examples within this class (and many others).
// Inheritance example: CoursesRecAdapter extends RecyclerView. A parent-child class relationship.
// Different type of polymorphism example: two different CourseRecAdapters are below (between lines 150 and 165)
// Both have the same name, but different sets of arguments. I believe this is called 'Overloading' Polymorphism.

public class CoursesRecAdapter extends RecyclerView.Adapter<CoursesRecAdapter.CoursesViewHolder> {

    class CoursesViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseTextID;
        private final TextView courseDescriptionID;
        private final TextView courseDatesID;
        private final TextView courseStatusID;
        private final ImageView teacherPictureID;

        private CoursesViewHolder(View itemView) {
            super(itemView);
            courseTextID = itemView.findViewById(R.id.assessmentDisplayName);
            courseDescriptionID = itemView.findViewById(R.id.assessmentDisplayCode);
            courseDatesID = itemView.findViewById(R.id.assessmentDisplayDatetime);
            courseStatusID = itemView.findViewById(R.id.assessmentDisplayType);
            teacherPictureID = itemView.findViewById(R.id.teacherPictureID);


            switch(recVersion)
            {
                case "attachToTerm":
                    itemView.setOnClickListener(v -> {
                        int position = getAdapterPosition();
                        final CourseEntity current = mCourses.get(position);
                        current.setTermID(theTermID);
                        Intent intent = new Intent(context, CourseDetails.class);
                        intent.putExtra("courseID", current.getCourseID());
                        intent.putExtra("courseName", current.getCourseName());
                        intent.putExtra("courseDescription", current.getCourseDescription());
                        intent.putExtra("courseCode", current.getCourseCode());
                        intent.putExtra("courseStatus", current.getCourseStatus());
                        intent.putExtra("instructorName", current.getCourseInstructor());
                        intent.putExtra("instructorEmail", current.getCourseInstructorEmail());
                        intent.putExtra("instructorPhone", current.getCourseInstructorPhone());
                        intent.putExtra("courseStart", current.getCourseStartMmDdYy());
                        intent.putExtra("courseEnd", current.getCourseEndMmDdYy());
                        intent.putExtra("associatedTerm", current.getTermID());
                        intent.putExtra("courseNotes", current.getCourseNotes());
                        intent.putExtra("type", "course");
                        intent.putExtra("attachOrDetach", "attach");
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    });
                    break;
                case "removeFromTerm":
                    itemView.setOnClickListener(v -> {
                        int position = getAdapterPosition();
                        final CourseEntity current = mCourses.get(position);
                        Intent intent = new Intent(context, CourseDetails.class);
                        intent.putExtra("courseID", current.getCourseID());
                        intent.putExtra("courseName", current.getCourseName());
                        intent.putExtra("courseDescription", current.getCourseDescription());
                        intent.putExtra("courseCode", current.getCourseCode());
                        intent.putExtra("courseStatus", current.getCourseStatus());
                        intent.putExtra("instructorName", current.getCourseInstructor());
                        intent.putExtra("instructorEmail", current.getCourseInstructorEmail());
                        intent.putExtra("instructorPhone", current.getCourseInstructorPhone());
                        intent.putExtra("courseStart", current.getCourseStartMmDdYy());
                        intent.putExtra("courseEnd", current.getCourseEndMmDdYy());
                        intent.putExtra("associatedTerm", current.getTermID());
                        intent.putExtra("courseNotes", current.getCourseNotes());
                        intent.putExtra("type", "course");
                        intent.putExtra("attachOrDetach", "detach");
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    });
                    break;
                case "searchCourses":
                    itemView.setOnClickListener(v -> {
                        int position = getAdapterPosition();
                        final CourseEntity current = mCourses.get(position);
                        System.out.println("Gweebo");
                        current.setTermID(theTermID);
                        Intent intent = new Intent(context, CourseDetails.class);
                        intent.putExtra("courseID", current.getCourseID());
                        intent.putExtra("courseName", current.getCourseName());
                        intent.putExtra("courseDescription", current.getCourseDescription());
                        intent.putExtra("courseCode", current.getCourseCode());
                        intent.putExtra("courseStatus", current.getCourseStatus());
                        intent.putExtra("instructorName", current.getCourseInstructor());
                        intent.putExtra("instructorEmail", current.getCourseInstructorEmail());
                        intent.putExtra("instructorPhone", current.getCourseInstructorPhone());
                        intent.putExtra("courseStart", current.getCourseStartMmDdYy());
                        intent.putExtra("courseEnd", current.getCourseEndMmDdYy());
                        intent.putExtra("associatedTerm", current.getTermID());
                        intent.putExtra("courseNotes", current.getCourseNotes());
                        intent.putExtra("type", "course");
                        intent.putExtra("attachOrDetach", "attach");
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    });
                    break;
                default:
                    itemView.setOnClickListener(v -> {
                        int position = getAdapterPosition();
                        final CourseEntity current = mCourses.get(position);
                        Intent intent = new Intent(context, CourseDetails.class);
                        intent.putExtra("courseID", current.getCourseID());
                        intent.putExtra("courseName", current.getCourseName());
                        intent.putExtra("courseDescription", current.getCourseDescription());
                        intent.putExtra("courseCode", current.getCourseCode());
                        intent.putExtra("courseStatus", current.getCourseStatus());
                        intent.putExtra("instructorName", current.getCourseInstructor());
                        intent.putExtra("instructorEmail", current.getCourseInstructorEmail());
                        intent.putExtra("instructorPhone", current.getCourseInstructorPhone());
                        intent.putExtra("courseStart", current.getCourseStartMmDdYy());
                        intent.putExtra("courseEnd", current.getCourseEndMmDdYy());
                        intent.putExtra("associatedTerm", current.getTermID());
                        intent.putExtra("courseNotes", current.getCourseNotes());
                        intent.putExtra("type", "course");
                        intent.putExtra("attachOrDetach", "neither");
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    });
            }
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;
    private List<TermEntity> mTerms;
    private final String recVersion;
    private int theTermID = -1;
    private String searchWords;

    public CoursesRecAdapter(Context context, String recVersion, int theTermID){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recVersion = recVersion;
        this.theTermID = theTermID;
    }

    public CoursesRecAdapter(Context context, String recVersion, String searchWords){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recVersion = recVersion;
        this.searchWords = searchWords;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.courses_row, parent, false);
        return new CoursesViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        if(mCourses != null){
            final CourseEntity current = mCourses.get(position);
            holder.courseTextID.setText(current.getCourseName());
            holder.courseDescriptionID.setText(current.getCourseDescription());
            holder.courseDatesID.setText(current.getCourseStartMmDdYy() + " - " + current.getCourseEndMmDdYy());
            holder.courseStatusID.setText(current.getCourseStatus());

            int teacherPicture = current.getCourseID();
            switch (teacherPicture) {
                case 1:
                    holder.teacherPictureID.setImageResource(R.drawable.person2);
                    break;
                case 2:
                    holder.teacherPictureID.setImageResource(R.drawable.person3);
                    break;
                case 3:
                    holder.teacherPictureID.setImageResource(R.drawable.person4);
                    break;
                case 4:
                    holder.teacherPictureID.setImageResource(R.drawable.person5);
                    break;
                case 5:
                    holder.teacherPictureID.setImageResource(R.drawable.person6);
                    break;
                case 6:
                    holder.teacherPictureID.setImageResource(R.drawable.person7);
                    break;
                case 7:
                    holder.teacherPictureID.setImageResource(R.drawable.person8);
                    break;
                case 8:
                    holder.teacherPictureID.setImageResource(R.drawable.person9);
                    break;
                case 9:
                    holder.teacherPictureID.setImageResource(R.drawable.person10);
                    break;
                case 10:
                    holder.teacherPictureID.setImageResource(R.drawable.person11);
                    break;
                default:
                    holder.teacherPictureID.setImageResource(R.drawable.person1);
                    break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTermWords(List<TermEntity> termWords) {
        mTerms = termWords;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourseWords(List<CourseEntity> courseWords) {
        mCourses = courseWords;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

}
