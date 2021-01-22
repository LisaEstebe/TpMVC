/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galerie.controller;

import galerie.dao.TableauRepository;
import galerie.entity.Tableau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Lisa
 */
@Controller
@RequestMapping(path="/tableau")
public class TableauController {
    
    @Autowired
    private TableauRepository dao;
    
    @GetMapping(path = "show")
    public String afficheToutesLesGaleries (Model model) {
        model.addAttribute("tableau", dao.findAll());
        return "afficheTableau";
    }
    
    @PostMapping (path="save")
    public String ajouteLeTableauPuisMontreLaListe(Tableau tableau, RedirectAttributes redirectInfo) {
        String message;
        try {
            dao.save(tableau);
            message = "Le tableau '"+ tableau.getTitre()+"' a été correctement enregistré";
        }catch (DataIntegrityViolationException e) {
            message = "Erreur : Le Tableau '"+tableau.getTitre()+"' existe déjà";
        }
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show";  
    }
    
    @GetMapping (path= "delete")
    public String supprimeUnTableauPuisMontreLaListe (@RequestParam("id") Tableau tableau, RedirectAttributes redirectInfo){
      String message = "Le tableau '" + tableau.getTitre() + "' a bien été supprimé";
        dao.delete(tableau);
        
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show"; // on se redirige vers l'affichage de la liste
    }
    
}