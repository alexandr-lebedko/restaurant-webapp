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

public class FileServiceImpl implements FileService {
    @Override
    public String saveImg(InputStream inputStream) throws ServiceException {
        try {
            String fileName = generateUniqueImageName();
            Files.copy(inputStream,
                    new File(new File(Image.DESTINATION_FOLDER), fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        File file = new File(new File(Image.DESTINATION_FOLDER), fileName);
        file.delete();
    }

    private String generateUniqueImageName() {
        return UUID.randomUUID().toString() + Image.SAVING_EXTENSION;
    }
}
