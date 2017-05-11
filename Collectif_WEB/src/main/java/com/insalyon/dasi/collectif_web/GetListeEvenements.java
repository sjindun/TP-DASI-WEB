/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Evenement;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class GetListeEvenements extends Action {
    
    @Override
    boolean execute(HttpServletRequest request){
        
        ServiceMetier servM = new ServiceMetier();
        List<Evenement> ev = servM.getEvenement();
        
        request.setAttribute("liste", ev);
        return true;
    }
    
}
