package net.lebedko.service.impl;

import net.lebedko.service.FileService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.util.constant.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * alexandr.lebedko : 10.08.2017.
 */
public class FileServiceImpl implements FileService {


    @Override
    public String saveImg(InputStream inputStream) throws ServiceException {
        String fileName = generateUniqueImageName();

        File file = new File(new File(Image.DESTINATION_FOLDER), fileName);
        try {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return fileName;
    }

    @Override
    public boolean deleteFile(String fileName) {
        File file = new File(new File(Image.DESTINATION_FOLDER), fileName);
        return file.delete();
    }

    private String generateUniqueImageName() {
        return UUID.randomUUID().toString() + Image.SAVING_EXTENSION;
    }
}
