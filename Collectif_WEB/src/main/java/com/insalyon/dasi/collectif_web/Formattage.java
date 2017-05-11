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
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.Lieu;

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
    
    String getJsonListeLieux(List<Lieu> lieux){
        
        JsonArray jsonListe = new JsonArray();
        String json = "";
        
        if(lieux!=null){
            for(Lieu lieu : lieux){
                JsonObject jsonActivite = new JsonObject();
                jsonActivite.addProperty("id", lieu.getId());
                jsonActivite.addProperty("adresse", lieu.getAdresse());

                jsonListe.add(jsonActivite);
            }

            JsonObject container = new JsonObject();
            container.add("lieux", jsonListe);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json = gson.toJson(container);
        }
        return json;
    }
    
    String getJsonListeEvenements(List<Evenement> ev){
        
        JsonArray jsonListe = new JsonArray();
        String json = "";
        
        if(ev!=null){
            for(Evenement evenement : ev){
                JsonObject jsonActivite = new JsonObject();
                jsonActivite.addProperty("id", evenement.getId());
                jsonActivite.addProperty("activite", evenement.getActivite().getDenomination());
                jsonActivite.addProperty("date", evenement.getDate_evenement().toString());
                jsonActivite.addProperty("moment", evenement.getMomentJournee());
                
                if(evenement.getLieu()==null){
                    jsonActivite.addProperty("lieu", "-");
                }else{
                    jsonActivite.addProperty("lieu", evenement.getLieu().getDenomination());
                }
                if(evenement.getCotisation()==-1){
                    jsonActivite.addProperty("paf", "free");
                }else{
                    jsonActivite.addProperty("paf",evenement.getCotisation());
                }

                jsonListe.add(jsonActivite);
            }

            JsonObject container = new JsonObject();
            container.add("evenements", jsonListe);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json = gson.toJson(container);
        }
        return json;
    }
    
    
    String getJsonListePositionParticipants(List<Adherent> ads){
        
        JsonArray jsonListe = new JsonArray();
        String json = "";
        
        if(ads!=null){
            for(Adherent ad : ads){
                JsonObject jsonActivite = new JsonObject();
                jsonActivite.addProperty("latitude", ad.getLatitude());
                jsonActivite.addProperty("longitude", ad.getLongitude());
                jsonActivite.addProperty("nom", ad.getNom());
                jsonActivite.addProperty("prenom", ad.getPrenom());

                jsonListe.add(jsonActivite);
            }

            JsonObject container = new JsonObject();
            container.add("adherents", jsonListe);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json = gson.toJson(container);
        }
        return json;
    }
    
}
