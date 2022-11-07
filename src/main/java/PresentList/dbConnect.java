package PresentList;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class dbConnect {
private static DatabaseReference particpantDatabase;
    public static void initFireBase(){
        FileInputStream refreshTokem = null;
        try {
            FileInputStream refreshToken = new FileInputStream("I:\\MAS\\QR\\QRCodeGenerateAndSendEmailOnOutlook" +
                    "\\src\\main\\java\\DBHelper\\masqr-ba96a-firebase-adminsdk-5wpn5-0dc11cf461.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://masqr-ba96a-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        }catch (IOException ex){

        }
    }
    public static void readData(){
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");*/
        particpantDatabase = FirebaseDatabase.getInstance().getReference().child("message");
        particpantDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                System.out.println(value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error");
            }
        });

    }

}


