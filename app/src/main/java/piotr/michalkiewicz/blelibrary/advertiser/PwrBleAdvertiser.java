package piotr.michalkiewicz.blelibrary.advertiser;

/*

autor: Piotr Michałkiewicz

 */

import android.app.Activity;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.AdvertisingSetCallback;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.util.Log;

import piotr.michalkiewicz.blelibrary.model.PwrAbstractDataPacket;
import piotr.michalkiewicz.blelibrary.utils.PwrBleAdapter;
import piotr.michalkiewicz.blelibrary.utils.PwrConstants;
import piotr.michalkiewicz.blelibrary.utils.PwrUnsupportedDeviceException;

/**
 * This class encloses BluetoothLeAdvertiser object and methods used to set and start advertising.
 * It serves it's initialization, configuration and ensures proper usage of BluetoothLeAdvertiser.
 * There are two inner classes representing callbacks.
 */
public class PwrBleAdvertiser extends PwrBleAdapter {


    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private AdvertisingSetCallback mAdvertiseSetCallback;
    private AdvertiseCallback mAdvertiseCallback;


    /** This method initializes mBluetoothLeAdvertiser field.
       It returns true if the initialization was a success and "false" if
       it didn't succeed. It takes calling activity as a parameter and stores it in
       mActivity field of the superclass.   */
    @Override
    public void initialize(Activity activity) throws NullPointerException, PwrUnsupportedDeviceException {
        super.initialize(activity);
        enableBluetoothIfDisabled();
        mBluetoothLeAdvertiser = getBluetoothAdapter().getBluetoothLeAdvertiser();
    }

    @Override
    public void destroy(){
        super.destroy();
        mBluetoothLeAdvertiser = null;
        mAdvertiseCallback = null;
        mAdvertiseSetCallback = null;
    }

    /** This method starts advertising data given as the first parameter with timeout
     specified by second parameter.It uses defualt configuration.
     The result is processed by a default callback.. */
    public void startAdvertising(PwrAbstractDataPacket dataPacket, int timeOut){

        if(dataPacket!=null) {
            if (mAdvertiseSetCallback == null) {

                AdvertiseData data = buildAdvertiseDataSet(dataPacket);
                AdvertiseSettings parameters = buildAdvertiseSettings(timeOut);
                mAdvertiseCallback = new PwrDefaultAdvertiseCallback();

                if (mBluetoothLeAdvertiser != null) {
                    mBluetoothLeAdvertiser.startAdvertising(parameters, data, null, mAdvertiseCallback);
                }
            }
        }
    }
    public void startAdvertising(PwrAbstractDataPacket dataPacket){

        if(dataPacket!=null) {
            if (mAdvertiseSetCallback == null) {

                AdvertiseData data = buildAdvertiseDataSet(dataPacket);
                AdvertiseSettings parameters = buildAdvertiseSettings();
                mAdvertiseCallback = new PwrDefaultAdvertiseCallback();

                if (mBluetoothLeAdvertiser != null) {
                    mBluetoothLeAdvertiser.startAdvertising(parameters, data, null, mAdvertiseCallback);
                }
            }
        }
    }

    public void startAdvertising(PwrAbstractDataPacket dataPacket, AdvertiseCallback callback){

        if(dataPacket!=null) {
            if (mAdvertiseSetCallback == null) {

                AdvertiseData data = buildAdvertiseDataSet(dataPacket);
                AdvertiseSettings parameters = buildAdvertiseSettings();

                if (mBluetoothLeAdvertiser != null) {
                    mBluetoothLeAdvertiser.startAdvertising(parameters, data, null, callback);
                }
            }
        }
    }

    public void startAdvertising(PwrAbstractDataPacket dataPacket, AdvertiseCallback callback,
                                 AdvertiseSettings settings){

        if(dataPacket!=null) {
            if (mAdvertiseSetCallback == null) {

                AdvertiseData data = buildAdvertiseDataSet(dataPacket);

                if (mBluetoothLeAdvertiser != null) {
                    mBluetoothLeAdvertiser.startAdvertising(settings, data, null, callback);
                }
            }
        }
    }


    public void stopAdvertisingSet() {
        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
        destroy();
    }

    /** This method builds AdvertiseData object out of PwrAbstractDataPacket object.
    The data is stored in manufacturer data array and whole packet has associated UUID
    retirved from dataPacket. */
    private AdvertiseData buildAdvertiseDataSet(PwrAbstractDataPacket dataPacket){

        if(dataPacket != null) {
            AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();

            dataBuilder.addManufacturerData(dataPacket.getManufacturerId(), dataPacket.getServiceValueBytes());
            dataBuilder.addServiceUuid(dataPacket.getServiceUUID());

            AdvertiseData advertiseData = dataBuilder.build();
            Log.d(PwrConstants.TAG, "Advertise data set has been built:\n" + advertiseData.toString());

            return advertiseData;
        }
        return null;
    }

    /** This method builds default settings used in the transmission with specified timeout. */
    private AdvertiseSettings buildAdvertiseSettings(int timeOut) {
        AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
        settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        settingsBuilder.setTimeout(timeOut);
        settingsBuilder.setConnectable(false);
        settingsBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW);
        Log.d(PwrConstants.TAG,"Advertise settings have been built");

        return settingsBuilder.build();
    }

    /** This method builds default settings used in the transmission with no timeout. */
    private AdvertiseSettings buildAdvertiseSettings() {
        AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
        settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        settingsBuilder.setTimeout(0);
        settingsBuilder.setConnectable(false);
        settingsBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW);
        Log.d(PwrConstants.TAG,"Advertise settings have been built");

        return settingsBuilder.build();
    }

    /** This is the default callback that logs result of starting advertising.  */
    class PwrDefaultAdvertiseCallback extends AdvertiseCallback{

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect){
            Log.d(PwrConstants.TAG, "PwrDefaultAdvertiseCallback:onStartSuccess");
        }

        @Override
        public void onStartFailure(int errorCode){
            Log.d(PwrConstants.TAG, "PwrDefaultAdvertiseCallback:onStartFailure code: " + errorCode);
        }
    }
}