package org.example.ClubManager.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.example.clubmanager.model.Club;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ClubService {

    private static final String COLLECTION_NAME = "clubs";

    public String createClub(Club club) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).add(club);
        return collectionsApiFuture.get().getId();
    }

    public Club getClub(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists()) {
            return document.toObject(Club.class);
        } else {
            return null;
        }
    }

    public List<Club> getAllClubs() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Club> clubs = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            clubs.add(document.toObject(Club.class));
        }
        return clubs;
    }

    public String updateClub(Club club) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(club.getId()).set(club);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteClub(String id) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
        return "Document with ID " + id + " has been deleted";
    }
}
