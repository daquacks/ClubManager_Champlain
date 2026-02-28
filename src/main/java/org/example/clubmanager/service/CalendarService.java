package org.example.clubmanager.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.example.clubmanager.model.Event;
import org.example.clubmanager.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CalendarService {

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public String createEvent(Event event) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = getFirestore().collection("events").add(event);
        return future.get().getId();
    }

    public List<Event> getEventsForClub(String clubId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = getFirestore().collection("events").whereEqualTo("clubId", clubId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Event> events = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Event event = document.toObject(Event.class);
            event.setId(document.getId());
            events.add(event);
        }
        return events;
    }

    public void addEvent(Post post) {

    }
}
