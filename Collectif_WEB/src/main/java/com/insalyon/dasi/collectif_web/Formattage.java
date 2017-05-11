/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import metier.modele.Activite;
import metier.modele.Demande;
import metier.modele.Evenement;

/**
 *
 * @author Jindun
 */
public class Formattage {
    
    String getJsonListeDemandes(List<Demande> liste){
        JsonArray jsonListe = new JsonArray();
        String json = "";
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
            json = gson.toJson(container);
        }
        return json;
    }
    
    String getJsonListeActivites(List<Activite> activites){
        
        JsonArray jsonListe = new JsonArray();
        String json = "";
        
        if(activites!=null){
            for(Activite act : activites){
                JsonObject jsonActivite = new JsonObject();
                jsonActivite.addProperty("id", act.getId());
                jsonActivite.addProperty("denomination", act.getDenomination());
                jsonActivite.addProperty("payant", (act.getPayant())?"Oui":"Non");

                //jsonListe.add("activites", "[" + act.getId() +"] " + act.getDenomination() + " " + "voir");
                jsonListe.add(jsonActivite);
            }

            JsonObject container = new JsonObject();
            container.add("activites", jsonListe);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json = gson.toJson(container);
        }
        return json;
    }
    
}
