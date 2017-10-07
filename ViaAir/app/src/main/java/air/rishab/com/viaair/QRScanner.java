package air.rishab.com.viaair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
    }

    public void onClick(View v){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.i("yo", "handleResult: "+result.getText());

        String[] result_arr = result.getText().split(" ");

        var.name = result_arr[0];
        var.flight = result_arr[1];
        var.fl_from = result_arr[2];
        var.fl_to = result_arr[3];
        var.Gate = result_arr[4];
        var.seat = result_arr[5];
        var.b_time = result_arr[6];
        var.gate_time_in_min = result_arr[7];

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        QRScanner.this.finish();
    }
}
