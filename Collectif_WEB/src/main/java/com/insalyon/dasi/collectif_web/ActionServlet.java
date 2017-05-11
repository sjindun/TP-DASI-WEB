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
import metier.modele.Lieu;
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
            ConnexionAdherent coAdherent = new ConnexionAdherent();
            
            PrintWriter out=response.getWriter();
            response.setContentType("text/html;charset=UTF-8");
            if(coAdherent.execute(request)){
                out.println("success");
            }else{
                String mail = request.getParameter("mail");
                if(mail.equals("admin")){
                    out.println("admin");
                }else{
                    out.println("fail");
                }
            }
        }else if("inscriptionAdherent".equals(todo)){
            
            InscriptionAdherent inscriAdherent = new InscriptionAdherent();
            
            PrintWriter out=response.getWriter();
            response.setContentType("text/html;charset=UTF-8");
            if(inscriAdherent.execute(request)){
                out.println("success");
            }else{
                out.println("fail");
            }
            
        }else{
            
            // Vérification de la session
            String estAdmin = (String)session.getAttribute("admin");
            if(estAdmin == null){
                // Non connecté
                //this.getServletContext().getRequestDispatcher("index.html").forward(request, response);
                response.sendRedirect("index.html");
            }else{
                if("deconnexion".equals(todo)){
                    session.invalidate();
                    response.sendRedirect("index.html");
                }
                
                if("true".equals(estAdmin)){
                    if("getListeLieux".equals(todo)){
                        
                        GetListeLieux gLL = new GetListeLieux();
                        gLL.execute(request);
                        List<Lieu> lieux = (List<Lieu>)request.getAttribute("liste");
                        
                        Formattage formattage = new Formattage();
                        String json = formattage.getJsonListeLieux(lieux);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);

                    }else if("getListeEvenements".equals(todo)){
                        
                        GetListeEvenements gLE = new GetListeEvenements();
                        gLE.execute(request);
                        List<Evenement> ev = (List<Evenement>)request.getAttribute("liste");
                        
                        Formattage formattage = new Formattage();
                        String json = formattage.getJsonListeEvenements(ev);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);

                    }else if("affecterLieu".equals(todo)){
            
                        AffectationLieu aL = new AffectationLieu();
            
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        if(aL.execute(request)){
                            out.println("success");
                        }else{
                            out.println("fail");
                        }
            
                    }else if("affecterPAF".equals(todo)){
            
                        AffectationPAF aP = new AffectationPAF();
            
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        if(aP.execute(request)){
                            out.println("success");
                        }else{
                            out.println("fail");
                        }
            
                    }
                    else if("getLieuxParticipants".equals(todo)){
            
                        GetListeParticipants gLP = new GetListeParticipants();
            
                        gLP.execute(request);
                        List<Adherent> ad = (List<Adherent>)request.getAttribute("liste");
                        
                        Formattage formattage = new Formattage();
                        String json = formattage.getJsonListePositionParticipants(ad);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);
            
                    }
                  
                }else{
                    // todo : Adherent normal
                    if("getListeDemandes".equals(todo)){
                        
                        GetListeDemandes gLC = new GetListeDemandes();
                        gLC.execute(request);
                        List<Demande> liste = (List<Demande>)request.getAttribute("liste");
                        
                        Formattage formattage = new Formattage();
                        String json = formattage.getJsonListeDemandes(liste);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);
                        
                    }else if("getListeActivites".equals(todo)){
                        
                        GetListeActivites gLA = new GetListeActivites();
                        gLA.execute(request);
                        List<Activite> activites = (List<Activite>)request.getAttribute("liste");
                        
                        Formattage formattage = new Formattage();
                        String json = formattage.getJsonListeActivites(activites);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);

                    }else if("enregistrerDemande".equals(todo)){
                        
                        EnregistrementDemande eD =new EnregistrementDemande();
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        if(eD.execute(request)){
                            out.println("success");
                        }else{
                            out.println("fail");
                        }

                    }
                    
                }
            }
            /*
            Adherent sessionAdherent = (Adherent)session.getAttribute("adherent");
            if(sessionAdherent == null){
                // Redirection vers l'index
                
                //response.sendRedirect("/index.html");
                System.out.println("cc");
            }else{
                // Autres actions !!
            }
            */
        }
        
        /**
        if("getActivite".equals(todo)){
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
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

}
