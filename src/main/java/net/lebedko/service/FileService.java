package net.lebedko.service;


import java.io.InputStream;

public interface FileService {
    String saveImg(InputStream input);

    void deleteFile(String name);
}
