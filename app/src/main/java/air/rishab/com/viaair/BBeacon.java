package air.rishab.com.viaair;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Created by RISHAB on 07-10-2017.
 */


public class BBeacon extends Service implements BeaconConsumer {
    private BeaconManager beaconManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                int temp = -1000;
                for (Beacon b :beacons){
                   //if(b.getId1().equals("9584228f-a86b-46b9-9e77-ce601dc1b318")) {
                       Log.i("Distance: ", String.valueOf(calculateAccuracy(b.getTxPower(), b.getRssi())));
//                    b.getTxPower();

                       Log.i("RSSI", String.valueOf(b.getRssi()));

                           var.Bdegree = Integer.parseInt(b.getId2().toString());
                           var.beacon = b.getId1().toString();
                       
                       Log.i("id1: ", b.getId1().toString());
                       Log.i("id2: ", b.getId2().toString());
                       Log.i("id3: ", b.getId3().toString());
                   //}/
                }
                /*if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }*/
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    protected static double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Log.i("onStartCommand: ","service started");

        beaconManager = BeaconManager.getInstanceForApplication(this);

        // Set the beacon layout so that iBeacons can be detected
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
}
