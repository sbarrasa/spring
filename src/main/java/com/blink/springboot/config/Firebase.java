package com.blink.springboot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class Firebase {
	
	@PostConstruct
	public FirebaseApp initialize() throws IOException {
	
		InputStream serviceAccount = 
				getClass().getClassLoader().getResourceAsStream("firebase-admin.json");
		
		
		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .setDatabaseUrl("https://zaiper-spring-test-default-rtdb.firebaseio.com")
		  .build();

		return FirebaseApp.initializeApp(options);
	}	
	
}