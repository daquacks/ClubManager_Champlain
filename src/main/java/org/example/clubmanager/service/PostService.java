package org.example.clubmanager.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.example.clubmanager.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    private static final String COLLECTION_NAME = "posts";

    public String createPost(Post post) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).add(post);
        return collectionsApiFuture.get().getId();
    }

    public Post getPost(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists()) {
            return document.toObject(Post.class);
        } else {
            return null;
        }
    }

    public List<Post> getPostsByClubId(String clubId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("clubId", clubId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Post> posts = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            posts.add(document.toObject(Post.class));
        }
        return posts;
    }

    public String updatePost(Post post) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(post.getId()).set(post);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deletePost(String id) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
        return "Document with ID " + id + " has been deleted";
    }
}
