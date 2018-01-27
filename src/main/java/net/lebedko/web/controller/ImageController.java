package net.lebedko.web.controller;

import net.lebedko.util.PropertyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/images/*")
public class ImageController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger();
    private static final String IMAGE_FOLDER = PropertyUtil.loadProperties("image/image.properties").getProperty("images.folder");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            String filename = req.getPathInfo().substring(1);
            File file = new File(new File(IMAGE_FOLDER), filename);
            resp.setHeader("Content-Type", getServletContext().getMimeType(filename));
            resp.setHeader("Content-Length", String.valueOf(file.length()));
            resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
            Files.copy(file.toPath(), resp.getOutputStream());
        }catch (IOException e){
            LOG.error(e);
            resp.sendError(SC_NOT_FOUND);
        }
    }
}