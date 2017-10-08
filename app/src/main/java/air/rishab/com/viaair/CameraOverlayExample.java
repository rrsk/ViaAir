package air.rishab.com.viaair;

/**
 * Created by RISHAB on 07-10-2017.
 */

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Handler;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class CameraOverlayExample extends Activity implements SurfaceHolder.Callback{

    ImageView imageView;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;
    static Button degree ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_overaly);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.camerapreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.control, null);
        LayoutParams layoutParamsControl
                = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);
        degree = (Button) findViewById(R.id.degree);
        imageView = (ImageView)findViewById(R.id.imageView);
        conti();
    }

    public void conti(){
        new CountDownTimer(30000,10){

            @Override
            public void onTick(long l) {
                degree.setText(String.valueOf(var.degree));

                RotateAnimation ra = new RotateAnimation(
                        var.currentdegree,
                        var.Bdegree-((var.degree+90)%360),
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);

                // how long the animation will take place
                ra.setDuration(210);

                // set the animation after the end of the reservation status
                ra.setFillAfter(true);

                // Start the animation
                imageView.startAnimation(ra);
                var.currentdegree = var.Bdegree-((var.degree+90)%360) ;

            }

            @Override
            public void onFinish() {
                conti();
            }
        }.start();

    }

    /*public static void changeDegree(){
        degree.setText((int) var.degree);
    }*/

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
// TODO Auto-generated method stub
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
// TODO Auto-generated method stub
        camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }
}