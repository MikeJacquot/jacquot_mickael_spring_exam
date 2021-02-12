package com.example.jacquot_mickael_spring_exam.service;

import com.example.jacquot_mickael_spring_exam.model.Annonce;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
@Service
public class PdfService {

//Toujours le meme souci que l'on avait vu ensemble j'espere qu'il fonctionnera chez toi
    public static String output = "src/main/resources/pdf/annonces_pdf.pdf";

    private String parseThymeleafTemplate(List<Annonce> annonces){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("annonces",annonces);

        return templateEngine.process("templates/annonces.html",context);
    }

    public void convertHtmlToPdf(List<Annonce> annonces) throws IOException, DocumentException {

        File folder=new File("src/main/resources/pdf/annonces_pdf.pdf");
        System.out.println(folder);
        folder.mkdirs();

        FileOutputStream fos = new FileOutputStream(output);

        String html = this.parseThymeleafTemplate(annonces);
        OutputStream outputStream = new FileOutputStream(output);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);


        outputStream.close();
    }
}
