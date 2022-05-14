package com.voiasis;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.dv8tion.jda.api.entities.MessageChannel;

public class webp2png {
    public static void converter(String imageLink, MessageChannel channel) throws IOException {
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
        FileOutputStream fos = new FileOutputStream("E:/Dev/JennyChan/settings/cache/borrowed_image.webp");
        fos.write(response);
        fos.close();

        String str1 = "E:/Dev/JennyChan/libwebp/bin/dwebp.exe";
        String str2 = "E:/Dev/JennyChan/settings/cache/borrowed_image.webp";
        String str3 = "E:/Dev/JennyChan/settings/cache/converted_image.png";

        String[] args = new String[]{str1, str2, "-o", str3};

        try {
            Runtime.getRuntime().exec(args);
        } catch (IOException e) {
        e.printStackTrace();
        }
        File file = new File("E:/Dev/JennyChan/settings/cache/converted_image.png");
        channel.sendFile(file).queue();
    }
}