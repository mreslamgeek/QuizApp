package com.eslam.quizzapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {

    private  OnFiresStoreTaskComplete onFiresStoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference quizRef =firebaseFirestore.collection("QuizList");

    public FirebaseRepository(OnFiresStoreTaskComplete onFiresStoreTaskComplete){
    this.onFiresStoreTaskComplete = onFiresStoreTaskComplete;
    }
    public void getQuizData(){
        quizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    onFiresStoreTaskComplete.quizListDataAdded(task.getResult().toObjects(QuizListModel.class));
                }
                else{
                    onFiresStoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface OnFiresStoreTaskComplete{
        void quizListDataAdded(List<QuizListModel> quizListModelList);
        void onError(Exception e);
    }
}
