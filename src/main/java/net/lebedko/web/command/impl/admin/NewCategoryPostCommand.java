package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.demo.general.StringI18N;
import net.lebedko.entity.demo.item.Category;
import net.lebedko.service.demo.CategoryService;
import net.lebedko.service.demo.FileService;
import net.lebedko.service.exception.EntityExistsException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.PageLocations;

import net.lebedko.web.validator.CategoryValidator;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static java.util.Optional.ofNullable;
import static net.lebedko.web.util.constant.PageErrorNames.CATEGORY_EXISTS;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public class NewCategoryPostCommand extends AbstractCommand {
    private static final IResponseAction CATEGORY_FORM_FORWARD = new ForwardAction(PageLocations.CATEGORY_NEW);
    private static final IResponseAction ADMIN_MENU_REDIRECT = new RedirectAction(URL.ADMIN_MENU);

    private CategoryService categoryService;
    private CategoryValidator categoryValidator;

    private FileService fileService;
    private ImageValidator imageValidator;


    public NewCategoryPostCommand(CategoryService categoryService,
                                  CategoryValidator categoryValidator,
                                  FileService fileService,
                                  ImageValidator imageValidator) {

        this.categoryService = categoryService;
        this.fileService = fileService;
        this.categoryValidator = categoryValidator;
        this.imageValidator = imageValidator;
    }


    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {

        final Errors errors = new Errors();

        final Category category = retrieveCategory(context);

        validateCategory(category, errors);
        validateImage(context, "image", errors);

        if (errors.hasErrors()) {
            context.addErrors(errors);
            return CATEGORY_FORM_FORWARD;
        }

        final InputStream imageInputStream = retrieveFileInputStream(context, "image");

        String imageId = null;

        try {
            imageId = fileService.saveImg(imageInputStream);
            category.setImageId(imageId);

            categoryService.insert(category);
            return ADMIN_MENU_REDIRECT;
        } catch (EntityExistsException eee) {
            //TODO: is it good to register error and add to context in catch block ???
            LOG.info("Attempt to insert existent category: " + category);
            LOG.info("Deleting image file: " + fileService.deleteFile(imageId));
            errors.register("category-exists", CATEGORY_EXISTS);
            context.addErrors(errors);
        }

        return CATEGORY_FORM_FORWARD;
    }

    private InputStream retrieveFileInputStream(IContext context, String name) throws ServiceException {
        try {
            return context.getPart(name).getInputStream();
        } catch (IOException e) {
            //TODO: localize message
            throw new ServiceException(e);
        } catch (ServletException e) {
            //TODO: localize message
            throw new ServiceException(e);
        }
    }

    private Category retrieveCategory(IContext context) {
        String enTitle = ofNullable(context.getRequestParameter("en-title")).orElse("");
        String ukrTitle = ofNullable(context.getRequestParameter("ukr-title")).orElse("");
        String ruTitle = ofNullable(context.getRequestParameter("ru-title")).orElse("");

        StringI18N titleI18N = new StringI18N();

        titleI18N.add(Locale.ENGLISH, enTitle);
        titleI18N.add(new Locale("ukr"), ukrTitle);
        titleI18N.add(new Locale("ru"), ruTitle);
        //TODO: insert image id;
        return new Category(titleI18N, null);
    }

    private void validateImage(IContext context, String name, Errors errors) throws ServiceException {
        imageValidator.validate(retrieveFileInputStream(context, name), errors);
    }

    private void validateCategory(Category category, Errors errors) {
        categoryValidator.validate(category, errors);
    }
}