package com.example.jacquot_mickael_spring_exam.controller;


import com.example.jacquot_mickael_spring_exam.model.Annonce;
import com.example.jacquot_mickael_spring_exam.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
@RequestMapping(path ="/")
public class AnnonceController {
    @Autowired
    private AnnonceService cs;

    private static String UPLOADED_FOLDER = "D:\\jacquot_mickael_spring_exam\\src\\main\\resources\\static\\images\\";
//Ici ca upload bien le fichier mais pas moyen de le recuperer , sinon ca fonctionne avec un lien google image
    @RequestMapping(value = "/")
    public ModelAndView listAnnonce() {
        ModelAndView modelAndView = new ModelAndView("annonces");
        modelAndView.addObject("annonces", cs.findAllAnnonces());
        return modelAndView;

    }

    @RequestMapping(value = "/{annonce}", method = RequestMethod.GET)
    public ModelAndView detailAnnonce(@PathVariable(name = "annonce", required = false) Annonce annonce) {

        if (annonce == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            ModelAndView modelAndView = new ModelAndView("detail_annonce");
            modelAndView.addObject("annonce", annonce);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView annonceForm() {
        Annonce annonce = new Annonce();
        ModelAndView modelAndView = new ModelAndView("annonces_form");
        modelAndView.addObject("annonce", annonce);
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAnnonce(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes ,@Valid Annonce annonce, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "annonces_form";
        } else {

            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                return "redirect:/";
            }

            Path path = null;
            try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);

                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");

            } catch (IOException e) {
                e.printStackTrace();
            }


            annonce.setImgLink(path.toString());
            annonce.setDatePublication(new Date());
            cs.saveOrUpdateAnnonce(annonce);
            return "redirect:/";
        }
    }


    @RequestMapping(value = "/edit/{annonce}", method = RequestMethod.GET)
    public ModelAndView annonceEditForm(@PathVariable(name = "annonce", required = false) Annonce annonce) {
        if (annonce == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            ModelAndView modelAndView = new ModelAndView("annonces_form");
            modelAndView.addObject("annonce", annonce);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/edit/{annonce}", method = RequestMethod.POST)
    public String editAnnonce(@Valid Annonce annonce,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "annonces";
        }else{

            cs.saveOrUpdateAnnonce(annonce);
            return "redirect:/";
        }
    }


    @RequestMapping(value = "/delete/{annonce}", method = RequestMethod.GET)
    public String annonceDelete(@PathVariable(name = "annonce", required = false) Annonce annonce) {
        if (annonce == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        } else {
            ModelAndView modelAndView = new ModelAndView("annonces");
            modelAndView.addObject("annonce", annonce);
            cs.deleteAnnonce(annonce);
            return "redirect:/";
        }

    }

}
