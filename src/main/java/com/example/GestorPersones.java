package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GestorPersones {
    Boolean programRunning = true;
    Scanner lector = new Scanner(System.in);
    UsaPersones usaPersones = new UsaPersones();

    /**
     * Descripcio de la funcio
     * 
     * @param args[]
     */
    public static void main(String args[]) {
        GestorPersones gestorPersones = new GestorPersones();
        gestorPersones.start();
    }

    void start() {
        usaPersones.connectDatabase();
        menu();
    }

    void menu() {
        while (programRunning) {
            String ordre = preguntarString("[persones]");
            switch (ordre) {
                case "help":
                    help();
                    break;
                case "llista":
                    llista();
                    break;
                case "carregarllista":
                    try {
                        carregarllista();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "llistaedat":
                    llistaedat();
                    break;
                case "llistaprofessio":
                    llistaprofessio();
                    break;
                case "llistapoblacio":
                    llistapoblacio();
                    break;
                case "afegir":
                    afegir();
                    break;
                case "borrar":
                    borrar();
                    break;
                case "edat":
                    edat();
                    break;
                case "adreca":
                    adreça();
                    break;
                case "editaredat":
                    editaredat();
                    break;
                case "quit":
                    programRunning = false;
                    break;
                default:
                    System.out.println("no s'ha trobat l'ordre");
            }
            menu();
        }
    }

    void help() {
        System.out.println("-------");
        System.out.println("> help              ajuda");
        System.out.println("> llista            mostrar totes les persones");
        System.out.println("> llistaedat        mostrar persones filtrant per edat");
        System.out.println("> llistaprofessio   mostrar persones filtrant per professió");
        System.out.println("> llistapoblacio    mostrar persones filtrant per adreça.població");
        System.out.println("> afegir            afegir una persona");
        System.out.println("> borrar            borrar una persona");
        System.out.println("> edat              saber l'edat d'una persona en concret");
        System.out.println("> editaredat        editar l'edat d'una persona en concret");
        System.out.println("-------");
    }

    /**
     * @throws FileNotFoundException
     */
    void carregarllista() throws FileNotFoundException {
        String path = preguntarString("ruta de l'arxiu que vols carregar a la BD de persones:");
        File arxiu = new File(path);
        usaPersones.carregarLlistaDePersones(arxiu);
    }

    void llista() {
        usaPersones.mostrarTotesLesPersones();
    }

    void llistaedat() {
        int min = preguntarInt("Edat mínima:");
        int max = preguntarInt("Edat màxima:");
        usaPersones.mostrarPersonesAmbEdat(min, max);
    }

    void edat() {
        String nom = preguntarString("Nom de la persona de qui vols saber l'edat:");
        usaPersones.mostrarEdatDePersonesAmb("nom", nom);
    }

    /**
     * Funció que ens diu si un número és parell
     * 
     * @param numero el número que volem analitzar
     * @return Boolean serà true si és parell i false si no ho és
     */
    Boolean esParell(int numero) {
        return numero % 2 == 0;
    }

    void editaredat() {
        String nom = preguntarString("Nom de la persona de qui vols editar l'edat:");
        if (usaPersones.exiteixUnaPersonaAmbNom(nom)) {
            usaPersones.mostrarEdatDePersonesAmb("nom", nom);
            int novaEdat = preguntarInt("nova edat:");
            usaPersones.editarEdatDePersonesAmb("nom", nom, novaEdat);
        } else {
            System.out.println("no s'ha trobat una persona amb {nom:" + nom + "}");
        }
    }

    void adreça() {
        String nom = preguntarString("Nom de la persona de qui vols saber l'adreça:");
        usaPersones.mostrarAdreçaDePersonesAmb("nom", nom);
    }

    /**
     * @param pregunta
     * @return String
     */
    String preguntarString(String pregunta) {
        System.out.print(pregunta);
        String resposta = lector.nextLine();
        return resposta;
    }

    /**
     * @param pregunta
     * @return int
     */
    int preguntarInt(String pregunta) {
        System.out.print(pregunta);
        int resposta = Integer.parseInt(lector.nextLine());
        return resposta;
    }

    void llistaprofessio() {
        String professio = preguntarString("De quina professió vols veure la llista de persones? ");
        usaPersones.mostrarPersonesAmb("professió", professio);
    }

    void llistapoblacio() {
        String poblacio = preguntarString("De quina població vols veure la llista de persones? ");
        usaPersones.mostrarPersonesAmb("adreça.població", poblacio);
    }

    void borrar() {
        String nom = preguntarString("Nom de la persona que vols borrar:");
        if (usaPersones.exiteixUnaPersonaAmbNom(nom)) {
            usaPersones.BorrarPersonaAmbElNom(nom);
        } else {
            System.out.println("no s'han trobat persones amb {nom:" + nom + "}");
        }

    }

    void afegir() {
        String nom = preguntarString("Nom i cognoms:");
        if (nom == "") {
            System.out.println("el nom no pot ser nul.");
            afegir();
        }
        int edat = preguntarInt("edat:");
        String professio = preguntarString("professió:");
        int sou = preguntarInt("sou:");
        String codiPostal = preguntarString("codi postal:");
        String carrer = preguntarString("carrer");
        String poblacio = preguntarString("població:");
        String localitat = preguntarString("localitat:");
        String provincia = preguntarString("província:");
        Adreça adreça = new Adreça(carrer, poblacio, localitat, provincia);
        Persona persona = new Persona(nom, edat, professio, sou, adreça, codiPostal);
        persona.setAdreça(adreça);
        usaPersones.inserirUnaPersona(persona);
    }
}
