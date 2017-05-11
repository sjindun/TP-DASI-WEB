/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class EnregistrementDemande extends Action{
    @Override
    boolean execute(HttpServletRequest request){
        String activite = request.getParameter("activite");
        Date date = new Date((String)request.getParameter("date"));
        String moment = request.getParameter("moment");
        
        ServiceMetier servM = new ServiceMetier();
        
        return true;
    }
    
}
