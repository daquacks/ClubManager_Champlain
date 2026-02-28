package org.example.clubmanager.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.example.clubmanager.model.Club;
import org.example.clubmanager.model.Post;
import org.example.clubmanager.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // --- User Operations ---
    public String saveUser(User user) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = getFirestore().collection("users").document(user.getUid()).set(user);
        return future.get().getUpdateTime().toString();
    }

    public User getUser(String uid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(User.class);
        }
        return null;
    }

    // --- Club Operations ---
    public String createClub(Club club) throws ExecutionException, InterruptedException {
        // If ID is not set, generate one
        if (club.getId() == null) {
            DocumentReference docRef = getFirestore().collection("clubs").document();
            club.setId(docRef.getId());
        }
        ApiFuture<WriteResult> future = getFirestore().collection("clubs").document(club.getId()).set(club);
        return future.get().getUpdateTime().toString();
    }

    public List<Club> getAllClubs() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = getFirestore().collection("clubs").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Club> clubs = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            clubs.add(document.toObject(Club.class));
        }
        return clubs;
    }

    public Club getClub(String clubId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection("clubs").document(clubId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(Club.class);
        }
        return null;
    }

    public void joinClub(String clubId, String userId) throws ExecutionException, InterruptedException {
        DocumentReference clubRef = getFirestore().collection("clubs").document(clubId);
        // Atomically add a new member to the "memberUids" array field.
        clubRef.update("memberUids", FieldValue.arrayUnion(userId));
    }

    // --- Post Operations ---
    public String createPost(Post post) throws ExecutionException, InterruptedException {
        if (post.getId() == null) {
            DocumentReference docRef = getFirestore().collection("posts").document();
            post.setId(docRef.getId());
        }
        ApiFuture<WriteResult> future = getFirestore().collection("posts").document(post.getId()).set(post);
        return future.get().getUpdateTime().toString();
    }

    public List<Post> getPostsByClub(String clubId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = getFirestore().collection("posts").whereEqualTo("clubId", clubId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Post> posts = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            posts.add(document.toObject(Post.class));
        }
        return posts;
    }
}
