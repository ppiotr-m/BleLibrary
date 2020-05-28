package piotr.michalkiewicz.blelibrary.utils;

/*

autor: Piotr Michałkiewicz

 */

import android.util.Log;
import android.util.Pair;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/*  This class implements static type converters. */
public final class PwrTypeConverter {

    // Byte array to int conversion
    public static int intFromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] intToByteArray(int value) {

        return new byte [] {(byte)(value >> 24),
                (byte)(value >> 16),
                (byte)(value >> 8),
                (byte)(value)};

    }

    // Joining two generic arrays
    public static <T> T[] concat(T[] first, T[] second) {
        Log.d(PwrConstants.TAG, "Before concat: " + first[0]);
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        Log.d(PwrConstants.TAG, "After concat: " + result[0]);
        return result;
    }

    // Returns byte array from double.
    public static byte[] doubleToByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    // Returns double from byte array.
    public static double doubleFromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    // Returns float from byte array
    public static float floatFromByteArray(byte [] bytes){
        float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();

        return f;
    }

    // Returns pair of floats from pair of byte arrays.
    public static Pair<Float, Float> floatPairFromByteArrayPair(Pair<byte[], byte []> byteArrayPair){
        float firstCoordinate = floatFromByteArray(byteArrayPair.first);
        float secondCoordinate = floatFromByteArray(byteArrayPair.second);

        return new Pair<>(firstCoordinate, secondCoordinate);
    }

    // Returns pair of doubles from pair of byte arrays.
    public static Pair<Double, Double> doublePairFromByteArrayPair(Pair<byte[], byte []> byteArrayPair){
        Double firstCoordinate = doubleFromByteArray(byteArrayPair.first);
        Double secondCoordinate = doubleFromByteArray(byteArrayPair.second);

        return new Pair<>(firstCoordinate, secondCoordinate);
    }


    // Returns byte array from float.
    public static byte [] floatToByteArray (float value)
    {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    // Converts byte array into pair of doubles representing GPS location.
    public static Pair<Double, Double> getGpsPositionFromByteArray(byte [] gpsDataBytes){

        return doublePairFromByteArrayPair(
                new Pair<>(Arrays.copyOfRange(gpsDataBytes, PwrConstants.GPS_LONGITUDE_INDEX_0,
                        PwrConstants.GPS_LONGITUDE_INDEX_1 + 1), Arrays.copyOfRange(gpsDataBytes,
                        PwrConstants.GPS_LONGITUDE_INDEX_1 + 1, PwrConstants.GPS_LONGITUDE_INDEX_2 + 1)));

    }

}