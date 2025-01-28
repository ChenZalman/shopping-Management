package com.example.shoppingmanagement.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.shoppingmanagement.data.myData;
import com.example.shoppingmanagement.R;
import com.example.shoppingmanagement.Ui.Cart;
import com.example.shoppingmanagement.Ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {

        String email = ((EditText) findViewById(R.id.etEmailLogin)).getText().toString();
        String password = ((EditText) findViewById(R.id.etPasswordLogin)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                            bundle.putString("email", email);
                            Navigation.findNavController(view).navigate(R.id.action_homePage_to_userPage,bundle);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Log in failed!!!!!!!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void register(View view) {

        String email = ((EditText) findViewById(R.id.etEmailRegistration)).getText().toString();
        String password = ((EditText) findViewById(R.id.etPasswordRegistration)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                            addData();
                            Navigation.findNavController(view).navigate(R.id.action_registrationPage_to_homePage);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Registration Failed!!!", Toast.LENGTH_LONG).show();
                            Log.d("createUserWithEmail:failure", task.getException().toString());

                        }
                    }
                });
    }

    public void addData(){
        EditText phone = ((EditText) findViewById(R.id.etPhoneNumber));
        EditText email = ((EditText) findViewById(R.id.etEmailRegistration));
        EditText name = ((EditText) findViewById(R.id.etNameRegistration));
        EditText password = ((EditText) findViewById(R.id.etPasswordRegistration));
        //get data from the layout or register (for now)
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(email.getText().toString().replace('.','_'));

        User user = new User(name.getText().toString(),email.getText().toString(),password.getText().toString() ,phone.getText().toString());
        myRef.setValue(user);

        myRef = database.getReference("carts").child(email.getText().toString().replace('.','_'));

        Cart cart = new Cart(email.getText().toString());
        for (int i = 0; i < myData.nameArray.length;i++){
            cart.addItem(myData.nameArray[i],0);
        }
        myRef.setValue(cart);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Navigation.findNavController(view).navigate(R.id.action_registrationPage_to_homePage);
//        }
//    }
}