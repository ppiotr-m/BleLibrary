package piotr.michalkiewicz.blelibrary.model;

/**

autor: Piotr Michałkiewicz

 */

import android.os.ParcelUuid;

import piotr.michalkiewicz.blelibrary.utils.PwrConstants;

/**  This class represents abstract data packet that is 9 bytes long. It's generic,
    so extensions may define data type that is stored within this object of this class  */

public abstract class PwrAbstractDataPacket<T> {

    private ParcelUuid mServiceUUID;
    private byte [] mServiceValueData;
    private int mManufacturerId;

    public PwrAbstractDataPacket(ParcelUuid serviceUUID, int manufacturerId){
        mServiceUUID = serviceUUID;
        mManufacturerId = manufacturerId;
    }

    public byte[] getServiceValueBytes() {
        return mServiceValueData;
    }

    /** Maximum size of parameter array is 9, if passed array has more elements, ArrayOfOutOfBoundException is thrown. */
    public void setServiceValueBytes(byte[] serviceValueData) {
        if (serviceValueData.length > PwrConstants.MAX_PACKET_LENGTH || serviceValueData.length==0)
            throw new ArrayIndexOutOfBoundsException("PwrAbstractDataPacket::setServiceValueBytes," +
                    "array length = " + serviceValueData.length);
        else {
            this.mServiceValueData = serviceValueData;
        }
    }

    public int getManufacturerId(){
        return mManufacturerId;
    }

    public ParcelUuid getServiceUUID() {
        return mServiceUUID;
    }

    public void setServiceUUID(ParcelUuid mServiceUUID) {
        this.mServiceUUID = mServiceUUID;
    }

    /**  This method returns type representation of bytes stored in mServiceValueData. User must
      implement the conversion and return the value.
     */
    public abstract <T> T getValue();

    /**  This method takes parameter of the classes type and stores it in mServiceValueData.
      User needs to implement the conversion to bytes.
     */
    public abstract void setAndConvertValue(T data);

}
