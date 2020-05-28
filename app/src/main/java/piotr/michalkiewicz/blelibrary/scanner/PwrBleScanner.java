package piotr.michalkiewicz.blelibrary.scanner;

/*

autor: Piotr Michałkiewicz

 */

import android.app.Activity;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.SparseArray;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import piotr.michalkiewicz.blelibrary.R;
import piotr.michalkiewicz.blelibrary.presenter.PwrDataTracker;
import piotr.michalkiewicz.blelibrary.utils.PwrBleAdapter;
import piotr.michalkiewicz.blelibrary.utils.PwrConstants;
import piotr.michalkiewicz.blelibrary.utils.PwrUnsupportedDeviceException;

/**  This class represents Bluetooh Low Energy scanner, that listens to advertisement packets.
    It contains inner class PwrBleScannerCallback that is default callback for triage application.
    The callback can be specified when starting a scan. Default scan period is 60s. */
public class PwrBleScanner extends PwrBleAdapter {

    private static final long SCAN_PERIOD = 60000;

    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback mScanCallback;
    private PwrDataTracker mDataTracker;

    private final Handler mHandler = new Handler();
    private final Runnable mScanStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScanning();
        }
    };

    /**  This conustructor uses default data tracker to process incoming data. */
    public PwrBleScanner(){}

    /**  This conustructor takes PwrDataTracker as it's only parameter. The tracker processes
        received data.  */
    public PwrBleScanner(PwrDataTracker tracker){
        mDataTracker = tracker;
    }


    /*  This method calls super class'es initalize(Activity) method, then initalizes
        mBluetoothScanner field and creates mHandler object that stops scanning after
        default period. It returns true when initalization was a success.   */
    @Override
    public void initialize(Activity activity) throws NullPointerException, PwrUnsupportedDeviceException{
        super.initialize(activity);
        enableBluetoothIfDisabled();
        mBluetoothLeScanner = getBluetoothAdapter().getBluetoothLeScanner();
    }

    //  This method must be called within onDestroy() method of the calling activity.
    @Override
    public void destroy(){
        super.destroy();
        mBluetoothLeScanner = null;
        mScanCallback = null;
        mHandler.removeCallbacks(mScanStopRunnable);
        mDataTracker = null;
    }

    /**  Default method for starting a scan. If no data tracker was specified in constructor,
        it uses default callback.   */
    public void startScanning() {

        if(mBluetoothLeScanner==null){
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.cant_start_scan), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mScanCallback == null) {
                mScanCallback = new PwrBleScannerCallback(mDataTracker);
                mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
                mBluetoothLeScanner.startScan(buildScanFilters(), buildScanSettings(), mScanCallback);
            }
        else {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                        getString(R.string.scan_in_progress), Toast.LENGTH_SHORT).show();
            }

    }

    // Starts scanning for devices with UUID specified by parameter.
    public void startScanning(UUID uuidFilterParameter) {
        if(mBluetoothLeScanner==null){
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.cant_start_scan), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mScanCallback == null) {
            mScanCallback = new PwrBleScannerCallback(mDataTracker);
            mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
            mBluetoothLeScanner.startScan(buildScanFilters(uuidFilterParameter), buildScanSettings(), mScanCallback);

        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                        getString(R.string.scan_in_progress), Toast.LENGTH_SHORT).show();
        }
    }

    public void startScanningForUuids(List<ParcelUuid> uuidFilterList) {
        if (mBluetoothLeScanner == null) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.cant_start_scan), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mScanCallback == null) {
            mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
            mScanCallback = new PwrBleScannerCallback(mDataTracker);

            mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
            mBluetoothLeScanner.startScan(buildScanFilters(uuidFilterList), buildScanSettings(), mScanCallback);

        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.scan_in_progress), Toast.LENGTH_SHORT).show();
        }
    }

    // Starts scanning for devices with UUID specified by parameter.
    public void startScanning(List<ScanFilter> filters) {
        if (mBluetoothLeScanner == null) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.cant_start_scan), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mScanCallback == null) {
            mScanCallback = new PwrBleScannerCallback(mDataTracker);

            mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
            mBluetoothLeScanner.startScan(filters, buildScanSettings(), mScanCallback);

            }
        else {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                        getString(R.string.scan_in_progress), Toast.LENGTH_SHORT).show();
            }
    }

    // Starts scanning with given callback
    public void startScanning(ScanCallback callback, List<ScanFilter> filters) {
        if (mBluetoothLeScanner == null) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.cant_start_scan), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mScanCallback == null) {
            mScanCallback = callback;
            mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
            mBluetoothLeScanner.startScan(filters, buildScanSettings(), mScanCallback);

            }
        else {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                        getString(R.string.scan_in_progress), Toast.LENGTH_SHORT).show();
            }
    }

    // Starts scanning with given callback for UUID linked to status change.
    public void startScanningAsParamedic(ScanCallback callback) {
        if (mBluetoothLeScanner == null) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.cant_start_scan), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mScanCallback == null) {
            mScanCallback = callback;
            ArrayList <ParcelUuid> filters = new ArrayList<>();
            filters.add(new ParcelUuid(PwrConstants.MY_TRIAGE_SERVICE_KAT_UUID));
            mHandler.postDelayed(mScanStopRunnable, SCAN_PERIOD);
            mBluetoothLeScanner.startScan(buildScanFilters((filters)), buildScanSettings(), mScanCallback);
            }
        else {
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                        getString(R.string.scan_in_progress), Toast.LENGTH_SHORT).show();
            }
    }

    // Returns list of default scan filters.
    private List<ScanFilter> buildScanFilters(){
        List<ScanFilter> filterList = new ArrayList<>();
        ScanFilter.Builder scanFilterBuilder = new ScanFilter.Builder();

        ScanFilter defaultUuidFilter = scanFilterBuilder.
                setServiceUuid(new ParcelUuid(PwrConstants.MY_APP_DEFAULT_UUID))
                .build();
        filterList.add(defaultUuidFilter);

        return filterList;
    }

    // Returns list with 1 element that is the 1 UUID that is being the filter paramter.
    private List<ScanFilter> buildScanFilters(UUID uuidFilterParameter){
        List<ScanFilter> filterList = new ArrayList<>();
        ScanFilter.Builder scanFilterBuilder = new ScanFilter.Builder();

        ScanFilter singleUuidFilter = scanFilterBuilder.
                setServiceUuid(new ParcelUuid(uuidFilterParameter))
                .build();

        filterList.add(singleUuidFilter);

        return filterList;
    }

    // Returns list of filters for given list of UUIDs.
    private List<ScanFilter> buildScanFilters(List<ParcelUuid> uuidFilters){

        if(uuidFilters!=null && uuidFilters.size()>0) {
            List<ScanFilter> filterList = new ArrayList<>();
            ScanFilter.Builder scanFilterBuilder = new ScanFilter.Builder();

            for (ParcelUuid uuid : uuidFilters) {

                ScanFilter uuidFilter = scanFilterBuilder.setServiceUuid(uuid).build();
                filterList.add(uuidFilter);

            }

            return filterList;
        }
        else return null;
    }

    // Builds scan filters for particular device name.
    private List<ScanFilter> buildScanFilters(String deviceName){
        List<ScanFilter> filterList = new ArrayList<>();
        ScanFilter.Builder scanFilterBuilder = new ScanFilter.Builder();

        ScanFilter filterDeviceName = scanFilterBuilder.
                setDeviceName(deviceName).build();
        filterList.add(filterDeviceName);
        return filterList;
    }

    // Builds default scan settings.
    private ScanSettings buildScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        return builder.build();
    }

    // This method stops scanning but leaves the PwrBleScanner initalized.
    public void stopScanning(){
        if(mBluetoothLeScanner!=null)
            mBluetoothLeScanner.stopScan(mScanCallback);
        mScanCallback = null;
        mHandler.removeCallbacks(mScanStopRunnable);
        Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                getString(R.string.scan_stopped), Toast.LENGTH_SHORT).show();
    }

    // Retriving UUID from incoming data
    private ParcelUuid getIncomingServiceUuid(ScanRecord scanRecord){

        return  scanRecord.getServiceUuids().get(0);
    }

    // Retriving debice ID from incoming data
    private int getDeviceId(SparseArray<byte[]> manufacturerSpecificData){

        return manufacturerSpecificData.keyAt(0);
    }

    private class PwrBleScannerCallback extends ScanCallback {

        PwrDataTracker mTracker;

        PwrBleScannerCallback(PwrDataTracker tracker){
            mTracker = tracker;
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if(mTracker!=null) {
                mTracker.processIncomingData(result);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().
                    getString(R.string.scan_fail) + " " + errorCode, Toast.LENGTH_SHORT).show();
        }}


}
