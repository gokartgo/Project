package ite.kmitl.project.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import ite.kmitl.project.R;
import ite.kmitl.project.dao.ProductItemDao;
import ite.kmitl.project.dao.UploadProductDao;

import static android.app.Activity.RESULT_OK;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ShelveManageFragment extends Fragment {

    int index;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChooseImage;
    private Button btnUploadImage;
    private TextView tvShowUpload;
    private EditText edImageName;
    private ImageView ivImage;
    private ProgressBar pbUploadImage;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private Uri imageUri;
    private UploadTask uploadTask;
    ProductItemDao productItemDao;
    private boolean isHaveImage = false;

    public ShelveManageFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ShelveManageFragment newInstance(int index) {
        ShelveManageFragment fragment = new ShelveManageFragment();
        Bundle args = new Bundle();
        args.putInt("index",index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        index = getArguments().getInt("index");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shelve_manage, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnChooseImage = (Button) rootView.findViewById(R.id.btnChooseImage);
        btnUploadImage = (Button) rootView.findViewById(R.id.btnUploadImage);
        tvShowUpload = (TextView) rootView.findViewById(R.id.tvShowUpload);
        edImageName = (EditText) rootView.findViewById(R.id.edImageName);
        ivImage = (ImageView) rootView.findViewById(R.id.ivImage);
        pbUploadImage = (ProgressBar) rootView.findViewById(R.id.pbUploadImage);
        btnChooseImage.setOnClickListener(buttonClick);
        btnUploadImage.setOnClickListener(buttonClick);
        tvShowUpload.setOnClickListener(buttonClick);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("product");
        bindingData();
        if(imageUri != null){
            Toast.makeText(getContext(),imageUri+" image uri string",Toast.LENGTH_LONG).show();
            Picasso.get().load(imageUri).into(ivImage);
        }
    }

    private void bindingData() {
        databaseReference.child("product"+index).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().toString().equals("image") && imageUri == null) {
                    if(!dataSnapshot.getValue().toString().trim().equals("")) {
                        Toast.makeText(getContext(),"Have Image",Toast.LENGTH_SHORT).show();
                        Picasso.get().load(dataSnapshot.getValue().toString()).into(ivImage);
                        isHaveImage = true;
                    }
                }
                if(dataSnapshot.getKey().toString().equals("name") && edImageName.getText().toString().trim().equals("")) {
                    edImageName.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        if(imageUri != null) {
            outState.putString("imageUri", imageUri + "");
            Toast.makeText(getContext(),imageUri+" save",Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        if(savedInstanceState.getString("imageUri") != null) {
            imageUri = Uri.parse(savedInstanceState.getString("imageUri"));
            Toast.makeText(getContext(), imageUri + " restore", Toast.LENGTH_SHORT).show();
        }
    }

    final View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == btnChooseImage) {
                openFileChooser();
            }

            if(view == btnUploadImage) {
                uploadFile();
            }

            if(view == tvShowUpload) {

            }
        }
    };

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(ivImage);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pbUploadImage.setProgress((int) progress);
                }
            }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pbUploadImage.setProgress(0);
                        }
                    },1000);
                    Toast.makeText(getContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                    UploadProductDao uploadImageDao = new UploadProductDao(edImageName.getText().toString(),
                            task.getResult().toString());
                    databaseReference.child("product"+index).child("image").setValue(task.getResult().toString());
                    databaseReference.child("product"+index).child("name").setValue(edImageName.getText().toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } else if(isHaveImage){
            Toast.makeText(getContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
            databaseReference.child("product"+index).child("name").setValue(edImageName.getText().toString());
        } else{
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
