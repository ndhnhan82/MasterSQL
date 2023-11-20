package model;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DatabaseManagement {

    public static void getCourseList(final CourseListCallback callback){
        DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference("Courses");
        ArrayList<Courses> arrListCourse = new ArrayList<>();

        courseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Courses courses = snapshot.getValue(Courses.class);
                arrListCourse.add(courses);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle changes if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle move if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                callback.onFailure(error.toException());
            }
        });

        // After all child events are processed, notify the callback
        courseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onCourseListUpdated( arrListCourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                callback.onFailure(error.toException());
            }
        });
    }

    public static void getUserList(final UserListCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        ArrayList<User> arrListUsers = new ArrayList<>();

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                arrListUsers.add(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle changes if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle move if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                callback.onFailure(error.toException());
            }
        });

        // After all child events are processed, notify the callback
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onUserListUpdated(arrListUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                callback.onFailure(error.toException());
            }
        });
    }
    public static void refreshProfilePicture(Context context, String email, ImageView imgProfile) {
        String safeEmail = email.replace( "@", "-" );
        safeEmail = safeEmail.replace( ".", "-" );
        String pathString = "Images/" + safeEmail + ".jpg";
        // Create a reference with an initial file path and name
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child( pathString ).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with( context).load( uri ).into( imgProfile );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText( context, "Cannot loading image!", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    public interface UserListCallback {
        void onUserListUpdated(ArrayList<User> userList);

        void onFailure(Exception e);
    }
    public interface CourseListCallback {
        void onCourseListUpdated(ArrayList<Courses> courseList);

        void onFailure(Exception e);
    }

}
