package com.insalyon.dasi.collectif_web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.service.ServiceMetier;

/**
 *
 * @author lsordillon
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
                
        String todo = request.getParameter("todo");
        
        if("connexionAdherent".equals(todo)){   
            String mail = request.getParameter("mail");
            if(mail.equals("admin")){
                session.setAttribute("admin", "true");
                //todo: gerer Connexion admin
            }else{  // connexion d'un Adherent
                ServiceMetier servM = new ServiceMetier();
                Adherent adherent = servM.seConnecter(mail);
                
                PrintWriter out=response.getWriter();
                if(adherent==null){
                    out.println("fail");
                }else{
                    session.setAttribute("adherent", adherent);
                    session.setAttribute("admin", "false");
                    response.setContentType("text/html;charset=UTF-8");
                    out.println("success");
                }
            }
        }else{
            // Vérification de la session
            String estAdmin = (String)session.getAttribute("admin");
            if(estAdmin == null){
                // Redirection vers l'index
            }else if("true".equals(estAdmin)){
                // todo finir
            }else{
                // todo : Adherent normal
            }
            
            Adherent sessionAdherent = (Adherent)session.getAttribute("adherent");
            if(sessionAdherent == null){
                // Redirection vers l'index
                //this.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
                response.sendRedirect("/index.html");
                System.out.println("cc");
            }else{
                // Autres actions !!
            }
        }
        
        /**
        else if("inscriptionAdherent".equals(todo)){
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mail = request.getParameter("mail");
            String adresse = request.getParameter("adresse");
            
            ServiceMetier servM = new ServiceMetier();
            Adherent adherent = servM.creerAdherent(nom, prenom, mail, adresse);
            if(adherent==null){
                out.println("fail");
            }else{
                out.println("success");
            }
            
        }else if("getListeDemandes".equals(todo)){
            long idAdherent = Long.parseLong(request.getParameter("idAdherent"));
            
            ServiceMetier servM = new ServiceMetier();
            Adherent adherent = servM.getAdherent(idAdherent);
            
            List<Demande> liste = servM.demandesPersonnelles(adherent);
            
            JsonArray jsonListe = new JsonArray();
            if(liste!=null){
                for(Demande d : liste){
                    JsonObject jsonDemande = new JsonObject();
                    jsonDemande.addProperty("id", d.getId());
                    jsonDemande.addProperty("denomination", d.getActivite().getDenomination());
                    jsonDemande.addProperty("date", d.getDate().toString());
                    jsonDemande.addProperty("heure", d.getMomentJournee());
                    Evenement eve = d.getEvenement();
                    jsonDemande.addProperty("lieu", (eve==null)? "-" : ((eve.getLieu()==null)?"-": eve.getLieu().getDenomination() ));
                    jsonListe.add(jsonDemande);
                }
                JsonObject container = new JsonObject();
                container.add("demandes", jsonListe);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(container);
                out.println(json);
            }
            
        }else if("getListeActivites".equals(todo)){
            
            ServiceMetier servM = new ServiceMetier();
            List<Activite> activites = servM.getActivites();
        
            JsonArray jsonListe = new JsonArray();
            
            for(Activite act : activites){
                JsonObject jsonActivite = new JsonObject();
                jsonActivite.addProperty("id", act.getId());
                jsonActivite.addProperty("denomination", act.getDenomination());
                jsonActivite.addProperty("payant", act.getPayant());
                
                //jsonListe.add("activites", "[" + act.getId() +"] " + act.getDenomination() + " " + "voir");
                jsonListe.add(jsonActivite);
            }
            
            JsonObject container = new JsonObject();
            container.add("activites", jsonListe);
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(container);
            out.println(json);
            
        }else if("getActivite".equals(todo)){
            long id = Long.parseLong(request.getParameter("id"));
            
            ServiceMetier servM = new ServiceMetier();
            List <Activite> liste = servM.getActivites();
            JsonArray jsonListe = new JsonArray();
            for(Activite act : liste){
                if(act.getId()==id){
                    
                    JsonObject jsonActivite = new JsonObject();
                    jsonActivite.addProperty("denomination", act.getDenomination());
                    jsonActivite.addProperty("payant", (act.getPayant()?"oui":"non"));
                    jsonActivite.addProperty("nbParticipants", act.getNbParticipants());
                    jsonListe.add(jsonActivite);
                }
            }
            
            JsonObject container = new JsonObject();
            container.add("activites", jsonListe); 
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(container);
            out.println(json);
        }
        **/
        
        
        
        /*
        try (PrintWriter out = response.getWriter()) {
            // TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ActionServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActionServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        */
        
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
  
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

}
