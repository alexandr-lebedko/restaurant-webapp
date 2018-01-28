package net.lebedko.service;

import java.io.InputStream;

public interface ImageService {
    String saveImg(InputStream input);

    void deleteImg(String name);
}