package com.insalyon.dasi.collectif_web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
                        
                        Formatage formattage = new Formatage();
                        String json = formattage.getJsonListeLieux(lieux);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);

                    }else if("getListeEvenements".equals(todo)){
                        
                        GetListeEvenements gLE = new GetListeEvenements();
                        gLE.execute(request);
                        List<Evenement> ev = (List<Evenement>)request.getAttribute("liste");
                        
                        Formatage formattage = new Formatage();
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
                        
                        Formatage formattage = new Formatage();
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
                        
                        Formatage formattage = new Formatage();
                        String json = formattage.getJsonListeDemandes(liste);
                        
                        PrintWriter out=response.getWriter();
                        response.setContentType("text/html;charset=UTF-8");
                        out.println(json);
                        
                    }else if("getListeActivites".equals(todo)){
                        
                        GetListeActivites gLA = new GetListeActivites();
                        gLA.execute(request);
                        List<Activite> activites = (List<Activite>)request.getAttribute("liste");
                        
                        Formatage formattage = new Formatage();
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
        }

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
