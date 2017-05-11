/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class GetListeActivites extends Action {
    @Override
    boolean execute(HttpServletRequest request){
        
        HttpSession session = request.getSession(true);
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        
        ServiceMetier servM = new ServiceMetier();
        List<Activite> activites = servM.getActivites();
        
        request.setAttribute("liste", activites);
        return true;
        
    }
    
}
