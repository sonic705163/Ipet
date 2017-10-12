package idv.jack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class petInformation extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "petInformation";
    private TextView petName, petColor, petAge, petSize, petTnr, petIc, petPosition, petSex, lng, lon;
    private Button btwebmail;
    MapView mapview;
    private Case cs;
    private GoogleMap googleMap;
    private LatLng myLocation;

    private View view;


    public void getCase(Case cs) {
        this.cs = cs;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petinformation);
//        cs = (Case) getArguments().getSerializable("cs");


        mapview = (MapView)findViewById(R.id.mapview);
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        MapsInitializer.initialize(this);
        mapview.getMapAsync(this);
        findViwe(cs);
        btwebmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(petInformation.this,WebmailSend.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("memno",cs.getMemNo());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void setUpMap() {
        myLocation = new LatLng(cs.getPetLatitude(), cs.getPetLongitude());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);

        }
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        googleMap.addMarker(new MarkerOptions().position(myLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_add)));
        googleMap.moveCamera(cameraUpdate);


    }


    private void findViwe(Case cs) {
        petName = (TextView) findViewById(R.id.petName);
        petColor = (TextView) findViewById(R.id.petColor);
        petAge = (TextView) findViewById(R.id.petAge);
        petSize = (TextView) findViewById(R.id.petSize);
        petTnr = (TextView) findViewById(R.id.petTnr);
        petIc = (TextView) findViewById(R.id.petIc);
        petPosition = (TextView) findViewById(R.id.petposition);
        petSex = (TextView) findViewById(R.id.petSex);
        btwebmail =(Button)findViewById(R.id.btwebmail);

    }


//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        petName.setText(cs.getPetName());
//        petColor.setText(cs.getPetColor());
//        petAge.setText(cs.getPetAge());
//        petSize.setText(cs.getPetSize());
//        petTnr.setText(cs.getTNR());
//        petIc.setText(cs.getPetIc());
//        petPosition.setText(cs.getPetPosition());
//        petSex.setText(cs.getPetSex());
//
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap();
    }
    @Override
    public void  onStart(){
        super.onStart();
        cs = (Case) this.getIntent().getExtras().getSerializable("cs");
        petName.setText(cs.getPetName());
        petColor.setText(cs.getPetColor());
        petAge.setText(cs.getPetAge());
        petSize.setText(cs.getPetSize());
        petTnr.setText(cs.getTNR());
        petIc.setText(cs.getPetIc());
        petPosition.setText(cs.getPetPosition());
        petSex.setText(cs.getPetSex());

    }

}