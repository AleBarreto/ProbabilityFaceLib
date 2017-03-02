package barreto.alessandro.mobilevision;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements FaceTracker.OnFaceProbability {

    private static final int REQUEST_CAMERA_PERM = 69;
    ProbabilityFace probabilityFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tem permissao?
        if (isCameraPermissionGranted()) {
            initLibFace();
        } else {
            // ...else pede a permissao
            requestCameraPermission();
        }

    }

    private void initLibFace(){
        probabilityFace = new ProbabilityFace(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isCameraPermissionGranted()){
            if (probabilityFace != null)
                probabilityFace.startCamera();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (probabilityFace != null)
            probabilityFace.cameraOnPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (probabilityFace != null)
            probabilityFace.cameraOnDestroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initLibFace();
            return;
        }

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aplicacao")
                .setMessage("Sem permissão da câmara")
                .setPositiveButton("Ok", listener)
                .show();

    }


    /**
     * Check permissao da camera
     *
     * @return <code>true</code> if granted
     */
    private boolean isCameraPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Pede a permissao da camera
     */
    private void requestCameraPermission() {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERM);
    }

    @Override
    public void getLeftEyeOpenProbability(float probability) {
        Log.d("Ale","olho esquerdo = "+probability);
    }

    @Override
    public void getRightEyeOpenProbability(float probability) {
        Log.d("Ale","olho direito = "+probability);
    }

    @Override
    public void getSmilingProbability(float probability) {
        Log.d("Ale","Sorriso ="+probability);
    }
}
