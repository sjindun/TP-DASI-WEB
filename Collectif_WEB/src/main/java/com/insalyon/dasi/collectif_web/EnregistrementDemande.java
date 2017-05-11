/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class EnregistrementDemande extends Action{
    @Override
    boolean execute(HttpServletRequest request){
        System.out.println("la date est "+request.getParameter("date"));
        
        String activite = request.getParameter("activite");
        System.out.println(activite);
        
        String da = request.getParameter("date");
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.FRENCH);
        Date date=new Date();
        try{
            date = format.parse(da);
        }catch(Exception e){
            
        }
        
        String moment = request.getParameter("moment");
        
        HttpSession session = request.getSession(true);
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        
        ServiceMetier servM = new ServiceMetier();
        Demande demande =servM.creerDemande(adherent,activite,date,moment);
        
        System.out.println(date);
        System.out.println(demande);
        
        if(demande==null){
            return false;
        }else{
            return true;
        }
    }
    
}
