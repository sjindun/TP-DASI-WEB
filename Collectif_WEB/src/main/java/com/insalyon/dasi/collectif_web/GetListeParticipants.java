/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class GetListeParticipants extends Action {
    @Override
    boolean execute(HttpServletRequest request){

        long idEv =Long.valueOf(request.getParameter("idEvenement"));
        ServiceMetier servM = new ServiceMetier();
        List<Adherent> adherents = servM.consulterParticipants(idEv);
        
        request.setAttribute("liste", adherents);
        return true;
        
    }
    
}
