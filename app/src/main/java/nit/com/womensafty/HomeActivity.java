package nit.com.womensafty;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity  {

    TextView tv1, tv2;
    LocationManager lManager;
    SupportMapFragment frag;
    GoogleMap gMap;
    String FirstNumber, SecondNumber, ThirdNumber, FourthNumber, FifthNumber;
    String text, text_2;
    String FirstMail, SecondMail, ThirdMail, FourthMail, FifthMail;
    private final int REQUEST_CODE = 99;
    //  private static final int VIDEO_CAPTURE = 101;
    //  private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        FirstMail = getIntent().getStringExtra("FirstMail");
        SecondMail = getIntent().getStringExtra("SecondMail");
        ThirdMail = getIntent().getStringExtra("ThirdMail");
        FourthMail = getIntent().getStringExtra("FourthMail");
        FifthMail = getIntent().getStringExtra("FifthMail");

        Log.e("Email Addresses", "" + FirstMail + "," + SecondMail + "," + ThirdMail + "," + FourthMail + "," + FifthMail);

        FirstNumber = getIntent().getStringExtra("First Number");
        SecondNumber = getIntent().getStringExtra("Second Number");
        ThirdNumber = getIntent().getStringExtra("Third Number");
        FourthNumber = getIntent().getStringExtra("Fourth Number");
        FifthNumber = getIntent().getStringExtra("Fifth Number");

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        if (!isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setMessage("Internet Connection Required")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        frag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lati = location.getLatitude();
                double longi = location.getLongitude();
                tv1.setText("Latitude :" + location.getLatitude());
                tv2.setText("Longitude :" + location.getLongitude());
                Log.e("My Location Data", "" + lati + "," + longi);

                lManager.removeUpdates(this);

                MarkerOptions options = new MarkerOptions();
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.place_holder));
                options.position(new LatLng(location.getLatitude(), location.getLongitude()));
                gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude() , location.getLongitude())).title("Its me!!!"));
                gMap.getUiSettings().setZoomControlsEnabled(true);
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12f));

                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                gMap.getUiSettings().setMapToolbarEnabled(true);
                gMap.getUiSettings().setMyLocationButtonEnabled(true);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        frag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
            }
        });
    }

    public void sendemail(View v) {
        text = tv1.getText().toString();
        text_2 = tv2.getText().toString();
        Intent intentemail = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
         FirstMail = intentemail.getStringExtra("FirstMail");
         SecondMail = intentemail.getStringExtra("SecondMail");
        ThirdMail = intentemail.getStringExtra("ThirdEmail");
          FourthMail = intentemail.getStringExtra("FourthMail");
          FifthMail = intentemail.getStringExtra("FifthMail");
        Log.e("Mail Data", "" + FirstMail + "" + SecondMail + "" + ThirdMail + "" + FourthMail + "" + FifthMail);
         String[] to = {FirstMail, SecondMail, ThirdMail, FourthMail, FifthMail};

        intentemail.setType("text/plain");

        intentemail.putExtra(Intent.EXTRA_EMAIL, new String[]{FirstMail, SecondMail, ThirdMail, FourthMail, FifthMail});
          intentemail.putExtra(Intent.EXTRA_EMAIL,""+FirstMail+""+SecondMail+""+ThirdMail+""+FourthMail+""+FifthMail);
        intentemail.putExtra(Intent.EXTRA_SUBJECT, "Please help me ");
        intentemail.putExtra(Intent.EXTRA_TEXT, "I am Unsafe.My Location is " + text + "  ,  " + text_2);
        startActivity(intentemail);

        try {
            startActivity(Intent.createChooser(intentemail, "Choose an email client from..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HomeActivity.this, "No email client installed.", Toast.LENGTH_LONG).show();
        }
    }

    public void call(View v) {

        final CharSequence[] options = {"Call Family Members", "Call Police", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Emergency Call To!");
        builder.setIcon(R.drawable.siren);
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Call Family Members")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);

                } else if (options[item].equals("Call Police")) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:100"));

                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    startActivity(callIntent);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void message(View v) {

        text = tv1.getText().toString();
        text_2 = tv2.getText().toString();
        Intent intent = getIntent();

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "" + FirstNumber + "," + SecondNumber + "," + ThirdNumber + "," + FourthNumber + "," + FifthNumber);
        Log.e("Phone Numbers", "" + FirstNumber + "," + SecondNumber + "," + ThirdNumber + "," + FourthNumber + "," + FifthNumber);
        smsIntent.putExtra("sms_body", "Hey!!! I'm not feeling safe now. Can you reach me out now??? My location is  " + text + "  " + text_2);
        startActivity(smsIntent);
    }

    public void disrection(View v) {
        text = tv1.getText().toString();
        text_2 = tv2.getText().toString();
        Uri gmmIntentUri = Uri.parse("geo:" + text + "," + text_2 + "?q=police");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        Log.e("Location Data", "" + text + "" + text_2);
        startActivity(mapIntent);

       /* Uri gmmIntentUri = Uri.parse("geo:0,0?q=");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent); */
    }

    public void tips(View v) {
        startActivity(new Intent(getApplicationContext(), TipsActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.about_us:
               // startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
              //  finish();
             //   return (true);
            case R.id.logout:
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void videorecording(View v) {

        Intent i=new Intent(HomeActivity.this,VideoRecordingActivity.class);
        startActivity(i);

   /*       hasCamera();
        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = Uri.fromFile(mediaFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VIDEO_CAPTURE) {
            Log.e("Video Capture", "" + requestCode);
            if (resultCode == RESULT_OK) {


              //  Intent intentParent = getIntent();
               // setResult(RESULT_OK, intentParent);
               // Uri uri = data.getData();
               // fileUri = data.getData();
                Log.e("Video Recording", "" + resultCode);

                Toast.makeText(HomeActivity.this,"Video has been saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(HomeActivity.this,"Video recording cancelled.", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(HomeActivity.this,"Failed to record video", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)) {
            return true;
        } else {
            return false;
        }
    }  */
    }
}