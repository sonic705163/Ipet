package idv.jack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class petInformation extends Fragment  implements OnMapReadyCallback {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_petinformation, container, false);
        cs = (Case) getArguments().getSerializable("cs");

        mapview = (MapView) view.findViewById(R.id.mapview);
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        MapsInitializer.initialize(getActivity());
        mapview.getMapAsync(this);
        findViwe(cs);
        btwebmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),WebmailSend.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("memno",cs.getMemNo());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;

    }

    private void setUpMap() {
        myLocation = new LatLng(cs.getPetLatitude(), cs.getPetLongitude());
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
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
        petName = (TextView) view.findViewById(R.id.petName);
        petColor = (TextView) view.findViewById(R.id.petColor);
        petAge = (TextView) view.findViewById(R.id.petAge);
        petSize = (TextView) view.findViewById(R.id.petSize);
        petTnr = (TextView) view.findViewById(R.id.petTnr);
        petIc = (TextView) view.findViewById(R.id.petIc);
        petPosition = (TextView) view.findViewById(R.id.petposition);
        petSex = (TextView) view.findViewById(R.id.petSex);
        btwebmail =(Button)view.findViewById(R.id.btwebmail);

    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        petName.setText(cs.getPetName());
        petColor.setText(cs.getPetColor());
        petAge.setText(cs.getPetAge());
        petSize.setText(cs.getPetSize());
        petTnr.setText(cs.getTNR());
        petIc.setText(cs.getPetIc());
        petPosition.setText(cs.getPetPosition());
        petSex.setText(cs.getPetSex());


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap();
    }
}