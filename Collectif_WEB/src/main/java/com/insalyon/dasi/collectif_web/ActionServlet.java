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
import metier.modele.Activite;
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
        response.setContentType("text/html;charset=UTF-8");
        
        JpaUtil.init();
              
        String todo = request.getParameter("todo");
        PrintWriter out=response.getWriter();
        
        
        if("getListeActivites".equals(todo)){
            
            ServiceMetier servM = new ServiceMetier();
            List<Activite> activites = servM.getActivites();
        
            JsonArray jsonListe = new JsonArray();
            
            for(Activite act : activites){
                JsonObject jsonActivite = new JsonObject();
                jsonActivite.addProperty("id", act.getId());
                jsonActivite.addProperty("denomination", act.getDenomination());
                
                //jsonListe.add("activites", "[" + act.getId() +"] " + act.getDenomination() + " " + "voir");
                jsonListe.add(jsonActivite);
            }
            
            JsonObject container = new JsonObject();
            container.add("activites", jsonListe);
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(container);
            out.println(json);
            
            /*
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ActionServlet</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Liste des activités : </h1>");
                for(Activite act : activites){
                    out.println("<p>[" + act.getId() +"] " + act.getDenomination() + " " + "voir" + "</p>");
                }
                out.println("</body>");
                out.println("</html>");
            }
            */
        }else if("getActivite".equals(todo)){
            ServiceMetier servM = new ServiceMetier();
            List <Activite> liste = servM.getActivites();
            long id = Long.parseLong(request.getParameter("id"));
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
