package org.example.clubmanager.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
        
        if (!resource.exists()) {
            System.err.println("ERROR: serviceAccountKey.json not found in src/main/resources. Please add it to run the application.");
            return null; 
        }

        try (InputStream serviceAccount = resource.getInputStream()) {
            // Check if the file is empty
            if (serviceAccount.available() == 0) {
                System.err.println("ERROR: serviceAccountKey.json is empty. Please paste your Firebase service account key JSON content into it.");
                return null;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            }
            return FirebaseApp.getInstance();
        } catch (IOException e) {
            System.err.println("ERROR: Failed to read serviceAccountKey.json: " + e.getMessage());
            throw e;
        }
    }
}
