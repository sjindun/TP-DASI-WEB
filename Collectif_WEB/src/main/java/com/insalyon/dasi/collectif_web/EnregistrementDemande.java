
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
        
        String activite = request.getParameter("activite");
        
        String da = request.getParameter("date");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        Date date;
        try{
            date = format.parse(da);
            Date auj = new Date();
            if(date.compareTo(auj) < 0){
                return false;
            }
        }catch(Exception e){
            return false;
        }
        
        String moment = request.getParameter("moment");
        
        HttpSession session = request.getSession(true);
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        
        ServiceMetier servM = new ServiceMetier();
        Demande demande =servM.creerDemande(adherent,activite,date,moment);
        
        if(demande==null){
            return false;
        }else{
            return true;
        }
    }
    
}
