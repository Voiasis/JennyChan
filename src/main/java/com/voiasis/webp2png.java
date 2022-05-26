package com.voiasis;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class webp2png {
    public static void converter(String imageLink) throws IOException {
        URL url = new URL(imageLink);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        if (!imageLink.endsWith(".webp")) {
            FileOutputStream fos = new FileOutputStream("E:/Dev/JennyChan/settings/cache/converted_image.png");
            fos.write(response);
            fos.close();
        } else {
            FileOutputStream fos = new FileOutputStream("E:/Dev/JennyChan/settings/cache/borrowed_image.webp");
            fos.write(response);
            fos.close();
            String str1 = "E:/Dev/JennyChan/libwebp/bin/dwebp.exe";
            String str2 = "E:/Dev/JennyChan/settings/cache/borrowed_image.webp";
            String str3 = "E:/Dev/JennyChan/settings/cache/converted_image.png";
            String[] args = new String[]{str1, str2, "-o", str3};
            Runtime.getRuntime().exec(args);
        }
    }
}