package piotr.michalkiewicz.blelibrary.advertiser;

/*

autor: Piotr Michałkiewicz

This class wraps multiple PwrBleAdvertiser objects, using each one of them for separate
advertising. It also stores data that is being advertised. Maximum packet limit is set to 15.

 */

import android.app.Activity;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertisingSetCallback;

import java.util.ArrayList;
import java.util.List;

import piotr.michalkiewicz.blelibrary.model.PwrAbstractDataPacket;
import piotr.michalkiewicz.blelibrary.utils.PwrConstants;
import piotr.michalkiewicz.blelibrary.utils.PwrUnsupportedDeviceException;

public class PwrBleAdvertiserManager {

    private List<PwrBleAdvertiser> mAdvertisersList;
    private List<PwrAbstractDataPacket> mAdvertiseData;
    private AdvertisingSetCallback mSetCallback;
    private AdvertiseCallback mCallback;

    /** Non-parameter constructor using default callback -
     PwrBleAdvertiser.PwrDefaultAdvertiseSetCallback */
    public PwrBleAdvertiserManager (){
        mAdvertisersList = new ArrayList<>();
        mAdvertiseData = new ArrayList<>();
    }


    /** Constructor that takes AdvertisingSetCallback as parameter */
    public PwrBleAdvertiserManager (AdvertisingSetCallback callback){
        mAdvertisersList = new ArrayList<>();
        mAdvertiseData = new ArrayList<>();
        mSetCallback = callback;

    }

    /** Constructor that takes AdvertiseCallback as parameter */
    public PwrBleAdvertiserManager (AdvertiseCallback callback){
        mAdvertisersList = new ArrayList<>();
        mAdvertiseData = new ArrayList<>();
        mCallback = callback;
    }

    /** Adds data packets to a list. If the limit has been reached, the method returns "false".
    When data has been added to list, the method returns "true". */
    public boolean addAdvertiseData(PwrAbstractDataPacket dataPacket){
        if(dataPacket!=null) {
            if(mAdvertiseData.size()> PwrConstants.ADVERTISEMENTS_LIMIT) {
                return false;
            }
            mAdvertiseData.add(dataPacket);
            return true;
        }
        return false;
    }

    /** Starts advertising packets with specified or default callback. */
    public void startAdvertising(Activity activity) throws NullPointerException, PwrUnsupportedDeviceException {
        if(mAdvertiseData.size()>0) {

            if (mCallback != null) {

                for (PwrAbstractDataPacket packet : mAdvertiseData) {
                    PwrBleAdvertiser advertiser = new PwrBleAdvertiser();
                    advertiser.initialize(activity);
                    mAdvertisersList.add(advertiser);
                    advertiser.startAdvertising(packet, mCallback);
                }
                return;
            }
            else if(mSetCallback != null){

                for (PwrAbstractDataPacket packet : mAdvertiseData) {
                    PwrBleAdvertiser advertiser = new PwrBleAdvertiser();
                    advertiser.initialize(activity);
                    mAdvertisersList.add(advertiser);
                    advertiser.startAdvertising(packet);
                }
                return;

            }
            else if(mSetCallback == null && mCallback == null) {
                for (PwrAbstractDataPacket packet : mAdvertiseData) {
                    PwrBleAdvertiser advertiser = new PwrBleAdvertiser();
                    advertiser.initialize(activity);
                    mAdvertisersList.add(advertiser);
                    advertiser.startAdvertising(packet);
                }
            }
        }
    }

    /** This method stops advertising, cleans data and assings newly created lists
    to fields: mAdvertisersList and mAdvertiseData. It also sets callbacks to null. */
    public void stopAdvertising(){
        for(PwrBleAdvertiser advertiser : mAdvertisersList) {
            advertiser.stopAdvertisingSet();
            advertiser.destroy();
        }
        mAdvertisersList = new ArrayList<>();
        mAdvertiseData = new ArrayList<>();
        mCallback = null;
        mSetCallback = null;
    }

}
