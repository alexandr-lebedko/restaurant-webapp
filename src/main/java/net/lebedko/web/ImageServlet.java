package net.lebedko.web;

import net.lebedko.web.util.constant.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static net.lebedko.web.util.constant.Image.DESTINATION_FOLDER;

/**
 * alexandr.lebedko : 10.08.2017.
 */
@WebServlet(urlPatterns = "/images/*",
        initParams = @WebInitParam(name = "imagesFolder", value = DESTINATION_FOLDER))
public class ImageServlet extends HttpServlet {
    private String imagesFolder;

    @Override
    public void init() {
        this.imagesFolder = this.getInitParameter("imagesFolder");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getPathInfo().substring(1);
        File file = new File(filename, filename);
        resp.setHeader("Content-Type", getServletContext().getMimeType(filename));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
