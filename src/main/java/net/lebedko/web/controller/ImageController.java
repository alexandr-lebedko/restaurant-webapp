package net.lebedko.web.controller;

import net.lebedko.util.PropertyUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@WebServlet("/images/*")
public class ImageController extends HttpServlet {
    private String IMAGE_FOLDER = PropertyUtil.loadProperties("image/image.properties").getProperty("images.folder");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filename = req.getPathInfo().substring(1);
        File file = new File(new File(IMAGE_FOLDER), filename);
        resp.setHeader("Content-Type", getServletContext().getMimeType(filename));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
