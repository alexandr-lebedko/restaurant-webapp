package net.lebedko.web.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.isNull;
import static net.lebedko.web.util.constant.PageErrorNames.INVALID_IMAGE;

/**
 * alexandr.lebedko : 10.08.2017.
 */
public class ImageValidator implements IValidator<InputStream> {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void validate(InputStream is, Errors errors) {
        try {
            if (isNull(ImageIO.read(is))) {
                errors.register("invalid_img", INVALID_IMAGE);
            }
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
