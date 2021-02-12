package com.example.jacquot_mickael_spring_exam.controller;

import com.example.jacquot_mickael_spring_exam.model.Annonce;
import com.example.jacquot_mickael_spring_exam.service.AnnonceService;
import com.example.jacquot_mickael_spring_exam.service.PdfService;
import com.lowagie.text.DocumentException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/export")
public class ExportController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private AnnonceService cs;

    @RequestMapping(value = "/pdf" ,method = RequestMethod.GET)
    public void pdf(HttpServletResponse response) throws IOException, DocumentException {
        List<Annonce> annonces = this.cs.findAllAnnonces();

        this.pdfService.convertHtmlToPdf(annonces);

        InputStream inputStream = new FileInputStream(new File("src/main/resources/static/pdf/candidat_listing_pdf.pdf"));
        IOUtils.copy(inputStream,response.getOutputStream());

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-disposition";
        String headerValue = "attachement; filename=export_candidats" + currentDateTime + ".pdf";
        response.setHeader(headerKey,headerValue);

        response.flushBuffer();




    }}