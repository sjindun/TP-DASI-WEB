/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author Jindun
 */
public class ConnexionAdherent extends Action{
    @Override
    boolean execute(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String mail = request.getParameter("mail");
        if(mail.equals("admin")){
            session.setAttribute("admin", "true");
            return false;
            //todo: gerer Connexion admin
        }else{  // connexion d'un Adherent
            ServiceMetier servM = new ServiceMetier();
            Adherent adherent = servM.seConnecter(mail);

            if(adherent==null){
                return false;
            }else{
                session.setAttribute("adherent", adherent);
                session.setAttribute("admin", "false");
                return true;
            }
        }
    }
}
