package net.lebedko.service;

import net.lebedko.service.exception.ServiceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * alexandr.lebedko : 10.08.2017.
 */
public interface FileService {
    String saveImg(InputStream input) throws ServiceException;

    boolean deleteFile(String name);
}
