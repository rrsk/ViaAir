package air.rishab.com.viaair;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;


/**
 * Created by RISHAB on 07-10-2017.
 */

public class Gorientation extends Service implements SensorEventListener {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private SensorManager mSensorManager;
    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        //Log.i("yo", "onStartCommand: service started ");
        return super.onStartCommand(intent, flags, startId);
    }





    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
         var.degree = Math.round(event.values[0]);
//        Log.i("yo", "onSensorChanged: "+ var.degree);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}
