package com.fisa.templates;


import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class VelocityTemplateProcessor {
    private final VelocityEngine velocityEngine;

    public VelocityTemplateProcessor() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }
    public String processTemplate(String templateName, VelocityContext context) {
        StringWriter writer = new StringWriter();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(templateName);
        try {
            String result = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            velocityEngine.evaluate(context,writer,"TemplateName",result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }
}
