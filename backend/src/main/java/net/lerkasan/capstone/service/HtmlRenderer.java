package net.lerkasan.capstone.service;


import net.lerkasan.capstone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class HtmlRenderer {

    private static final String VERIFICATION_TEMPLATE = "verification";
    private static final String VERIFIED_TEMPLATE = "email-verified";
    public static final String USERNAME = "username";

    public static final String URL = "url";
    public static final String MESSAGE = "message";
    private final TemplateEngine templateEngine;

    @Autowired
    public HtmlRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String renderVerificationEmail(User user, String url) {
        final Context context = new Context();
        context.setVariable(USERNAME, user.getUsername());
        context.setVariable(URL, url);
        return templateEngine.process(VERIFICATION_TEMPLATE, context);
    }

    public String renderEmailRegistation(String message) {
        final Context context = new Context();
        context.setVariable(MESSAGE, message);
        return templateEngine.process(VERIFIED_TEMPLATE, context);
    }
}