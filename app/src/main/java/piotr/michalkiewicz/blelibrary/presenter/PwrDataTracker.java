package piotr.michalkiewicz.blelibrary.presenter;

/*

autor: Piotr Michałkiewicz

 */

import android.app.Activity;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;

/**  This interface is used to process data found by BluetoothLeScanner. */
public interface PwrDataTracker {
    void processIncomingData(ScanResult incomingData);

}
