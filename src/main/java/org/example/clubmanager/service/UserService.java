package org.example.ClubManager.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.clubmanager.model.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private static final String COLLECTION_NAME = "users";

    public String createUser(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(user.getUid()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public User getUser(String uid) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(uid);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists()) {
            return document.toObject(User.class);
        } else {
            return null;
        }
    }

    public String updateUser(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(user.getUid()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUser(String uid) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(uid).delete();
        return "Document with ID " + uid + " has been deleted";
    }
}
