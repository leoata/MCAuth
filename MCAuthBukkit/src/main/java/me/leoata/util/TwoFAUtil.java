package me.leoata.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TwoFAUtil {

    public static String getGoogleAuthenticatorBarCode(String secretKey, String uuid) {
        String issuer = "MCAuth";
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + " - " + BukkitUtils.getUsername(uuid), "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static BitMatrix createQRCode(String barCodeData, int height, int width)
            throws WriterException {
        return new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
    }
}
