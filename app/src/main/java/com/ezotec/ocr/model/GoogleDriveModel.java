package com.ezotec.ocr.model;


import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ezotec.ocr.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * 作成途中
 */
public class GoogleDriveModel {

    static final int RC_SIGN_IN=1;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private Fragment fragment;
    private Activity activity;
    private String CREDENTIALS_FILE_PATH = "/credentials.json";
    private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private List<String> SCOPES= Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private String TOKENS_DIRECTORY_PATH ="tokens";

    public void initOptions(){
        gso=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(String.valueOf(R.string.client_id))
                .requestEmail()
                .build();
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void signIn(){
        googleSignInClient= GoogleSignIn.getClient(activity,gso);
        Intent signInIntent=googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    public GoogleDriveModel(Fragment fragment){
        this.fragment=fragment;
        this.initOptions();
    }

    public GoogleDriveModel(Activity activity){
        this.activity=activity;
        this.initOptions();
    }

    public GoogleDriveModel(Activity activity,Fragment fragment){
        this.activity=activity;
        this.fragment=fragment;
        this.initOptions();
    }

    public String getUserName(){
        return firebaseUser.getDisplayName();

    }

    public void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseUser=firebaseAuth.getCurrentUser();
                }
            }
        });
    }

    public String getIdToken(Intent data) {
        String idToken="";
        try {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            idToken=account.getIdToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return idToken;
    }

    public Credential getCredentials(NetHttpTransport HTTP_TRNASPORT) throws IOException{
        InputStream in = GoogleDriveModel.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if(in==null){
            throw  new FileNotFoundException("Resource not found :"+CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow= new GoogleAuthorizationCodeFlow
                .Builder(HTTP_TRNASPORT,JSON_FACTORY,clientSecrets,SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver=new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow,receiver).authorize("user");
    }
}
