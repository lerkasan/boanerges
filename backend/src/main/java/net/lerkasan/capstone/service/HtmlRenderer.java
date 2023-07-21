package net.lerkasan.capstone.service;


import net.lerkasan.capstone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class HtmlRenderer {

    private static final String VERIFICATION_TEMPLATE = "verification";
    private static final String VERIFIED_TEMPLATE = "email-verified";
    private final TemplateEngine templateEngine;

    @Autowired
    public HtmlRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String renderVerificationEmail(User user, String url) {
        final Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", url);
        return templateEngine.process(VERIFICATION_TEMPLATE, context);
    }

    public String renderEmailRegistation(String message) {
        final Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process(VERIFIED_TEMPLATE, context);
    }
}