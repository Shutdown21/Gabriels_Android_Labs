package algonquin.cst2335.hube0078;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailaddress = fromPrevious.getStringExtra("EmailAddress");
        TextView emailText = findViewById(R.id.emailtext);
        emailText.setText("Welcome back "+ emailaddress);

        Intent call = new Intent(Intent.ACTION_DIAL);
        EditText phoneNumber = findViewById(R.id.editTextPhone);
        call.setData(Uri.parse("tel:" + phoneNumber));

        Button callButton = findViewById(R.id.btnCall);
        callButton.setOnClickListener(clk ->{
            call.putExtra("PhoneNumber", phoneNumber.getText().toString());
            startActivity( call);
        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ImageView profileImage = findViewById(R.id.imageView);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult() ,
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;

                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();

                                fOut.close();

                            }

                            catch (FileNotFoundException e)

                            { e.printStackTrace();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        Button pictureButton = findViewById(R.id.btnPic);
        pictureButton.setOnClickListener(clk ->{
            cameraResult.launch(cameraIntent);
        });
        String filename = "Picture.png";
        File file = new File( getFilesDir(), filename);
        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            profileImage.setImageBitmap( theImage );
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        EditText phoneNumber = findViewById(R.id.editTextPhone);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor et = prefs.edit();
        et.putString("PhoneNumber", phoneNumber.getText().toString());

        et.apply();



    }
}