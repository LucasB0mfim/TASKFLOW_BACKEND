package br.com.fatto.config;

import java.io.ByteArrayInputStream;
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

    @Value("${firebase.database.credentials.env-var-name:FIREBASE_ADMIN_SDK_JSON}")
    private String credentialsEnvVarName;

    private DatabaseReference database;

    @PostConstruct
    public void initialize() {
        if (database == null) {
            try {
                // Obter o conteúdo do arquivo JSON de credenciais da variável de ambiente
                String credentialsJson = System.getenv(credentialsEnvVarName);

                // Verifique se a variável de ambiente está definida
                if (credentialsJson == null || credentialsJson.isEmpty()) {
                    throw new IllegalStateException("A variável de ambiente contendo as credenciais não foi encontrada.");
                }

                // Converte o JSON em InputStream
                ByteArrayInputStream credentialsStream = new ByteArrayInputStream(credentialsJson.getBytes());

                // Inicializa o Firebase com as credenciais
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                        .setDatabaseUrl(databaseUrl)
                        .build();

                FirebaseApp.initializeApp(options);
                database = FirebaseDatabase.getInstance().getReference();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // Se a variável de ambiente não for encontrada ou estiver vazia
                e.printStackTrace();
            }
        }
    }

    public DatabaseReference getDatabase() {
        return database;
    }
}