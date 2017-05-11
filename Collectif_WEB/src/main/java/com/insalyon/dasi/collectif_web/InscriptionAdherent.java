/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class InscriptionAdherent extends Action {
    @Override
    boolean execute(HttpServletRequest request){
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String mail = request.getParameter("mail");
        String adresse = request.getParameter("adresse");

        ServiceMetier servM = new ServiceMetier();
        Adherent adherent = servM.creerAdherent(nom, prenom, mail, adresse);
        if(adherent==null){
            return false;
        }else{
            return true;
        }
    }
    
}
