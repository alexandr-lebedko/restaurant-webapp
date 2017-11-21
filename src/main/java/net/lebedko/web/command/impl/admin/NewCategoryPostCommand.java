package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;
import net.lebedko.service.CategoryService;
import net.lebedko.service.FileService;
import net.lebedko.service.exception.EntityExistsException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;

import net.lebedko.web.validator.item.CategoryValidator;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;

import java.io.InputStream;

import static java.util.Optional.ofNullable;
import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.web.util.constant.PageErrorNames.CATEGORY_EXISTS;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public class NewCategoryPostCommand extends AbstractCommand {
//    private static final IResponseAction CATEGORY_FORM_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN);
//    private static final IResponseAction ADMIN_MENU_REDIRECT = new RedirectAction(URL.ADMIN_MENU);

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
            context.addRequestAttribute("category", category);
return null;
//            return CATEGORY_FORM_FORWARD;
        }

        final InputStream image = getImage(context);

        String imageId = null;

        try {
            imageId = fileService.saveImg(image);
//            category.setImageId(imageId);

            categoryService.insert(category);
            return null;
//            return ADMIN_MENU_REDIRECT;
        } catch (EntityExistsException eee) {
            //TODO: is it good to register error and add to context in catch block ???
            LOG.info("Attempt to insert existent category: " + category);
            LOG.info("Deleting image file: " + fileService.deleteFile(imageId));
            errors.register("category-exists", CATEGORY_EXISTS);
            context.addErrors(errors);
        }
        return null;
//        return CATEGORY_FORM_FORWARD;
    }

    private InputStream getImage(IContext context) throws ServiceException {
        return getInputStream(context, "image");
    }

    private Category retrieveCategory(IContext context) {
        String enTitle = ofNullable(context.getRequestParameter("en-title")).orElse("");
        String ukrTitle = ofNullable(context.getRequestParameter("ukr-title")).orElse("");
        String ruTitle = ofNullable(context.getRequestParameter("ru-title")).orElse("");

        StringI18N titleI18N = new StringI18N();

        titleI18N.add(getByCode(EN_CODE), enTitle);
        titleI18N.add(getByCode(UA_CODE), ukrTitle);
        titleI18N.add(getByCode(RU_CODE), ruTitle);

        return new Category(titleI18N);
    }

    private void validateImage(IContext context, String name, Errors errors) throws ServiceException {
        imageValidator.validate(getImage(context), errors);
    }

    private void validateCategory(Category category, Errors errors) {
        categoryValidator.validate(category, errors);
    }
}
