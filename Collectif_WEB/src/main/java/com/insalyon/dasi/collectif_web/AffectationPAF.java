/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Evenement;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class AffectationPAF extends Action {
     @Override
    boolean execute(HttpServletRequest request){
        
        long idEv =Long.valueOf(request.getParameter("idEvenement"));
        int paf = Integer.valueOf(request.getParameter("paf"));
        
        ServiceMetier servM = new ServiceMetier();
        Evenement ev =servM.affecterPAF(idEv,paf);
        //System.out.println("tada   "+ev);
        
        if(ev==null){
            return false;
        }else{
            return true;
        }
    }
    
}
