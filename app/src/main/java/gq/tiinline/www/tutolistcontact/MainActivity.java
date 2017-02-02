package gq.tiinline.www.tutolistcontact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraCaptureSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gq.tiinline.www.tutolistcontact.data.CacheChecker;
import gq.tiinline.www.tutolistcontact.ui.GroupListActivity;
import gq.tiinline.www.tutolistcontact.utils.DownloadImage;

import static android.support.v4.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int SELECT_FILE = 3;
    private TextView ui_welcomeLabel;
    private final  int PICK_CONTACT_REQUEST=1;
    private ImageView mImageView;
    private String mCurrentPhotoPath;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        mStorageRef = FirebaseStorage.getInstance().getReference();
        ui_welcomeLabel = (TextView)findViewById(R.id.hello);
        mImageView = (ImageView)findViewById(R.id.photocamera);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Utilisation des processus async ou threeds
        //CacheChecker cacheChecker = new CacheChecker((TextView)findViewById(R.id.hello));
        //Créer un tableau de string de 3 elements
        //cacheChecker.execute("data 1","data 2","data 3");

        //downloadFromUrl("http://www.google.fr");
       }

    /**
     * download des fichiers avec Volley Downloader
     * @param url de la requette.
     */
    private void downloadFromUrl(String url) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("Volley", "Success: " + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley","Error:"+volleyError.getLocalizedMessage());
            }
        });
        // VolleyDownloader.getInstance(this.getApplicationContext()).addToRequestQueue(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void welcomeButtonPressed(View view) {
        Intent openGroups = new Intent(this, GroupListActivity.class);
        startActivity(openGroups);
        ui_welcomeLabel.setText("Hello Word!!! life is good if you live with Jesus(Jesus is God)");

        //pickContact();
    }
    public void  pickContact(){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(pickContactIntent,PICK_CONTACT_REQUEST);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File f= new File("/storage/9C33-6BBD/Android");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        System.out.println("1-f:"+image.getAbsolutePath());
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                System.out.println("0-if");
                Uri photoURI=null;
               /* try {
                    photoURI = /*new Uri.Builder()
                            .path(photoFile.getPath())
                            .authority(this.getPackageName()+".FileProvider")
                            .scheme(ContentResolver.SCHEME_CONTENT)
                            .build();

                            getUriForFile(this,
                            "gq.tiinline.www.tutolistcontact.FileProvider",
                            photoFile);
                    System.out.println("Ref:"+photoURI.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)/*photoURI*/);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    private void dispatchTakePictureIntent1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri));



    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("OK Super:"+ requestCode +"-"+resultCode);

        if (requestCode ==PICK_CONTACT_REQUEST && resultCode== Activity.RESULT_OK) {
            //tratement des données envoyé comme résultat

        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
             //   System.out.print("OK data 1 null:");
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            //setPic();
            uploadFile();
            galleryAddPic();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void GetFromgalleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    public void startCamera(View view) {
        //GetFromgalleryIntent();
       dispatchTakePictureIntent();
    }

    //FireBase Storage

    public void uploadFile(){
        final File file = new File(mCurrentPhotoPath);
        Uri uriFile = Uri.fromFile(file);

        StorageReference riversRef = mStorageRef.child("images/"+file.getName());

        riversRef.putFile(uriFile)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        System.out.println("fait:"+downloadUrl.getPath());
                        new DownloadImage(mImageView,getApplicationContext())
                                .execute(downloadUrl.toString());
                        Picasso.with(getApplicationContext()).load(downloadUrl).into((ImageView)findViewById(R.id.photocameraPicasso));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

}
