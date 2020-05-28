package piotr.michalkiewicz.blelibrary.utils;

/*

autor: Piotr Michałkiewicz

 */

import java.util.UUID;

/** This class contains all global constants used in this library.
 *
 */
public final class PwrConstants {

    public final static String TAG = "PwrBleLibarary";

    public final static int MAX_PACKET_LENGTH = 13;
    public final static int ADVERTISEMENTS_LIMIT = 15;

    public final static UUID MY_APP_DEFAULT_UUID =
            UUID.fromString("ee745ee7-3ae3-4c60-8592-37f1cae7c6aa");
    public final static  UUID HEART_RATE_MEASUREMENT_UUID =
            UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    public final static UUID GPS_POSITION_UUID =
            UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    public final static UUID MY_TRIAGE_SERVICE_UUID =
            UUID.fromString("ecde0161-97ce-45ab-99bf-3a2589c9e799");
    public final static UUID MY_TRIAGE_SERVICE_KAT_UUID =
            UUID.fromString("ecde0161-97ce-45ab-99bf-3a2589c9e997");
    public final static UUID MY_TRIAGE_SERVICE_PM_UUID =
            UUID.fromString("ecde0161-97ce-45ab-99bf-3a2589c9e797");
    public final static UUID PARAMEDIC_DEVICE_NAME_UUID =
            UUID.fromString("ecde0161-87ce-45ab-99bf-3a2589c9e799");
    public final static UUID BLUE_RING_UUID =
            UUID.fromString("ecde0161-87ce-45ab-99bf-3a2589c9e799");

    public final static int REQUEST_ENABLE_BT = 1;
    public final static int DEV_ID = 71;
    public final static int ACCESS_COARSE_LOCATION_CODE = 10;

    public final static int GPS_LONGITUDE_INDEX_0 = 0;
    public final static int GPS_LONGITUDE_INDEX_1 = 8;
    public final static int GPS_LONGITUDE_INDEX_2 = 16;

}
