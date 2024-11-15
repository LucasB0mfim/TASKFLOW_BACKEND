package br.com.fatto.config;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@Service
public class FirebaseInitializer {

	@Value("${firebase.database.url}")
	private String databaseUrl;

	@Value("${firebase.database.credentials.path}")
	private String credentialsPath;

	private DatabaseReference database;

	@PostConstruct
	public void initialize() {
		if (database == null) {
			try {
				FileInputStream refreshToken = new FileInputStream(credentialsPath);
				FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(refreshToken)).setDatabaseUrl(databaseUrl).build();
				FirebaseApp.initializeApp(options);
				database = FirebaseDatabase.getInstance().getReference();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public DatabaseReference getDatabase() {
		return database;
	}
}