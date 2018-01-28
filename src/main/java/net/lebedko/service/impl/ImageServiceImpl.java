package net.lebedko.service.impl;

import net.lebedko.service.ImageService;
import net.lebedko.util.PropertyUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;
import java.util.UUID;

public class ImageServiceImpl implements ImageService {
    private static final Properties PROPS = PropertyUtil.loadProperties("image/image.properties");

    private File destinationFolder;
    private String fileExtension;

    public ImageServiceImpl() {
        this(PROPS.getProperty("images.folder"), PROPS.getProperty("images.extension"));
    }

    public ImageServiceImpl(String destinationFolderPath, String fileExtension) {
        this.destinationFolder = new File(destinationFolderPath);
        this.fileExtension = fileExtension;
    }

    @Override
    public String saveImg(InputStream inputStream) {
        try {
            String fileName = generateUniqueImageName();

            Files.copy(inputStream, new File(destinationFolder, fileName).toPath());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImg(String imgName) {
        new File(destinationFolder, imgName).delete();
    }

    private String generateUniqueImageName() {
        return UUID.randomUUID().toString() + fileExtension;
    }
}