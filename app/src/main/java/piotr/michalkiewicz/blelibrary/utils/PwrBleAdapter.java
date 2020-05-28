package piotr.michalkiewicz.blelibrary.utils;

/*

autor: Piotr Michałkiewicz

 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/*  This class initlizes Android's BluetoothAdapter and checks for required permissions.
    It stores calling Activity reference so it's object can interact with GUI.
 */
public class PwrBleAdapter {

    private BluetoothAdapter mBluetoothAdapter;
    private Activity mActivity;

    private boolean checkIfDeviceSupportsBLE() {
        if(mActivity!=null) {
            return mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        }
        return false;
    }

    /** Initliazes mBluetoothAdapter field. Takes Context object as parameter.  */
    private void initalizeAndroidBluetoothAdapter(Context context) throws NullPointerException {
        if(context==null) throw new NullPointerException("PwrBleAdapter::initializeBluetoothAdapter, context is null");

        BluetoothManager bluetoothManager = (BluetoothManager)
                context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    /** If Bluetooth is disabled on the device, this method shows dialog to ask user to enable it. */
    protected void enableBluetoothIfDisabled() {
        if (getBluetoothAdapter() == null || !getBluetoothAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(enableBtIntent, PwrConstants.REQUEST_ENABLE_BT);

        }
    }

    /** This method is returns Bluetooth status (on/off) and throws RuntimeException when
        mBluetoothAdapter is null. */
    public boolean isBluetoothEnabled() throws Exception{
        if(mBluetoothAdapter==null)
            throw new NullPointerException("PwrBleAdapter::isBluetoothEnabled(), mBluetoothAdapter is null");

        return mBluetoothAdapter.isEnabled();
    }

    protected BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    /* This method performs complete initalization of PwrBleAdapter object. It checks if device
        supports BLE and initializes mBluetoothAdapter field. If any of those failed, a dialog
        is shown. If passed Activity paremeter is null then the method throws RuntimeException. */
    protected void initialize(Activity activity) throws NullPointerException, PwrUnsupportedDeviceException {
        if(activity==null) throw new NullPointerException("PwrBleAdapter::initialize, activity is null");

        mActivity = activity;
        if (!checkIfDeviceSupportsBLE()) {
            throw new PwrUnsupportedDeviceException("PwrBleAdapter::initialize, device does not support Bluetooth Low Energy");
        }
        initalizeAndroidBluetoothAdapter(activity);

    }

    protected Activity getActivity() {
        return mActivity;
    }

    /** Needs to be called in onDestroy() method of calling activity. */
    protected void destroy(){
        mActivity = null;
        mBluetoothAdapter = null;
    }

}
