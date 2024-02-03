import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class UNO {
    public static void main(String[] args) {
        ArrayList<Integer> CartesJ1 = new ArrayList<>();
        ArrayList<Integer> CartesJ2 = new ArrayList<>();
        ArrayList<Integer> Pioche = new ArrayList<>();
        ArrayList<Integer> TasJeu = new ArrayList<>();

        //Initialisation du jeu
        remplirMelangerPioche(Pioche);
        distributionCartes(CartesJ1, CartesJ2, Pioche);
        System.out.println("Bienvenue à UNO !");
        System.out.println("Joueur 1, voici vos cartes :");
        afficherCarteJoueur(CartesJ1);
        System.out.println("\n\nJoueur 2, voici vos cartes :");
        afficherCarteJoueur(CartesJ2);
        initialisationTasJeu(TasJeu, Pioche);

        //Lancement du jeu
        jeu(CartesJ1, CartesJ2, Pioche, TasJeu);

    }

    // Cette méthode remplit et mélange la pioche
    public static void remplirMelangerPioche(ArrayList<Integer> Pioche) {
        for (int i = 1; i <= 64; i++) {
            Pioche.add(i);
        }
        Collections.shuffle(Pioche);
    }

    // Cette méthode distribue les cartes aux joueurs
    public static void distributionCartes(ArrayList<Integer> CartesJ1, ArrayList<Integer> CartesJ2, ArrayList<Integer> Pioche) {
        for (int i = 0; i < 7; i++) {
            CartesJ1.add(Pioche.remove(0));
            CartesJ2.add(Pioche.remove(0));
        }
    }

    // Affiche la carte qui correspond à codeCarte
    public static void afficherCarte(int codeCarte) {
        String couleur = "";
        int valeur;

        if (codeCarte >= 1 && codeCarte <= 15) {
            couleur = "\033[0;31mrouge\033[0m";
            valeur = (codeCarte - 1) % 15 + 1;
        } else if (codeCarte >= 16 && codeCarte <= 30) {
            couleur = "\033[0;34mbleu\033[0m";
            valeur = (codeCarte - 16) % 15 + 1;
        } else if (codeCarte >= 31 && codeCarte <= 45) {
            couleur = "\033[0;32mvert\033[0m";
            valeur = (codeCarte - 31) % 15 + 1;
        } else if (codeCarte >= 46 && codeCarte <= 60) {
            couleur = "\033[0;33mjaune\033[0m";
            valeur = (codeCarte - 46) % 15 + 1;
        } else if (codeCarte >= 61 && codeCarte <= 64) {
            couleur = "\033[0;30mnoir\033[0m";
            valeur = (codeCarte - 61) % 4 + 1;
        } else {
            System.out.println("Numéro de carte invalide");
            return;
        }

        String typeCarte = "";
        if (codeCarte == 61 || codeCarte == 62) {
            typeCarte = "+4";
        } else if (codeCarte == 63 || codeCarte == 64) {
            typeCarte = "Bonus";
        }else if (valeur >= 1 && valeur <= 9) {
            typeCarte = String.valueOf(valeur);
        } else if (valeur == 10 || valeur == 11) {
            typeCarte = "Passer le tour";
        } else if (valeur == 12 || valeur == 13) {
            typeCarte = "Changement de sens";
        } else if (valeur == 14 || valeur == 15) {
            typeCarte = "+2";
        }
        System.out.println("Carte " + codeCarte + " : " + typeCarte + " - " + couleur);
    }

    // Cette méthode affiche les cartes du joueur
    public static void afficherCarteJoueur(ArrayList<Integer> CartesJoueur) {
        for (int i = 0; i < CartesJoueur.size(); i++) {
            afficherCarte(CartesJoueur.get(i));
        }
    }

    // Cette méthode prend une carte de la pioche pour initialiser le jeu
    public static void initialisationTasJeu(ArrayList<Integer> TasJeu, ArrayList<Integer> Pioche) {
        TasJeu.add(Pioche.remove(0));
    }

    // Méthode qui lance le jeu
    public static void jeu(ArrayList<Integer> CartesJ1, ArrayList<Integer> CartesJ2, ArrayList<Integer> Pioche, ArrayList<Integer> TasJeu) {
        boolean finDePartie = false;
        int joueurActuel = 1; // Le joueur 1 commence
        String couleur = "rouge";
        while (!finDePartie) {

            if (joueurActuel == 1) {
                initialisationTourJeu(TasJeu, CartesJ1, 1, couleur);
                int choix = demanderChoix(CartesJ1,nbCartePosable(CartesJ1,TasJeu.get(TasJeu.size()- 1), couleur));
                if (choix ==1){
                    poserCarte(TasJeu.get(TasJeu.size()- 1),CartesJ1, TasJeu, couleur);
                    joueurActuel = 2;
                    if(traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==1){
                        joueurActuel = 1 ;
                    }else if (traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==2){
                        piocherCarteSpecial(Pioche, TasJeu, CartesJ2,2);
                    }else if (traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==3){
                        piocherCarteSpecial(Pioche, TasJeu, CartesJ2,4);
                    }else if (traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==4){
                        couleur = carteBonus();
                    }
                }else{
                    piocherCarte(Pioche, TasJeu,CartesJ1, 1);
                    joueurActuel = 2;
                }
                if (CartesJ1.size() == 1) {
                    unoContreUno( TasJeu.get(TasJeu.size()- 1),CartesJ1, TasJeu, Pioche);
                    joueurActuel = 2;
                }

            } else {
                initialisationTourJeu(TasJeu, CartesJ2, 2, couleur);
                int choix = demanderChoix(CartesJ2,nbCartePosable(CartesJ2,TasJeu.get(TasJeu.size()- 1),couleur));
                if (choix ==1){
                    poserCarte(TasJeu.get(TasJeu.size()- 1),CartesJ2, TasJeu, couleur);
                    joueurActuel = 1;
                    if(traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==1){
                        joueurActuel = 2 ;
                    }else if (traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==2){
                        piocherCarteSpecial(Pioche, TasJeu, CartesJ1,2);

                    }else if (traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==3){
                        piocherCarteSpecial(Pioche, TasJeu, CartesJ1,4);
                    }else if (traitementCarteSpecial(TasJeu.get(TasJeu.size()- 1)) ==4){
                        couleur = carteBonus();
                    }
                }else{
                    piocherCarte(Pioche, TasJeu,CartesJ2,1);
                    joueurActuel = 1;
                }
                if (CartesJ2.size() == 1) {
                    unoContreUno( TasJeu.get(TasJeu.size()- 1),CartesJ2, TasJeu, Pioche );
                    joueurActuel = 1;
                }
            }

            //Vérifie si un joueur a gagné (aucune carte dans sa main)
            if (CartesJ1.isEmpty()) {
                System.out.println("Le joueur 1 a gagné !");
                finDePartie = true;
            } else if (CartesJ2.isEmpty()) {
                System.out.println("Le joueur 2 a gagné !");
                finDePartie = true;
            }
        }
    }

    public static void initialisationTourJeu (ArrayList<Integer> TasJeu, ArrayList<Integer> CarteJ, int numJoueur, String couleur){
        System.out.println(" ");
        System.out.println("---------- JOUEUR " + numJoueur + " ----------");
        System.out.println("Voici vos cartes : ");
        afficherCarteJoueur(CarteJ);
        System.out.println(" ");

        System.out.print("La carte du dessus est : ");
        afficherCarte( TasJeu.get(TasJeu.size()- 1));
        System.out.println(" ");

        afficherCartePosable(CarteJ, TasJeu.get(TasJeu.size()- 1), couleur);
    }

    // Méthode qui affiche les cartes que le joueur peut poser
    public static void afficherCartePosable(ArrayList<Integer> CartesJoueur, int JeuEnCours, String couleur) {
        System.out.println("Voici les cartes que vous pouvez poser :");

        for (int carte : CartesJoueur) {
            if (peutPoser(carte, JeuEnCours,couleur)) {
                afficherCarte(carte);
            }
        }
    }

    public static int demanderChoix(ArrayList<Integer> CarteJ, int nbCarte) {
        boolean choice = false;
        Scanner scanner = new Scanner(System.in);
        int choixFinal = 0;
        while (!choice) {
            if(CarteJ.size() <31 && nbCarte>0) {
                System.out.println("Que voulez-vous faire ? 1 pour poser une carte ou 2 pour piocher");
                int choix = scanner.nextInt();
                scanner.nextLine();
                if (choix == 1 || choix == 2) {
                    choixFinal = choix;
                    choice = true;
                }
            }else if (nbCarte == 0) {
                System.out.println("Vous devez piocher");
                choixFinal = 2;
                choice = true;

            }else{
                choixFinal = 1;
                choice = true;
            }
        }
        return choixFinal;
    }

    public static int nbCartePosable(ArrayList<Integer> CartesJoueur, int JeuEnCours, String couleur) {
        int i=0;
        for (int carte : CartesJoueur) {
            if (peutPoser(carte, JeuEnCours, couleur)) {
                i++;
            }
        }
        return i;
    }


    // Méthode pour vérifier si une carte peut être posée sur une autre
    public static boolean peutPoser(int carte, int JeuEnCours, String couleur) {
        if (carte >= 61 && carte <= 64){
            return true;
        }else if (JeuEnCours >= 61 && JeuEnCours <= 62){
            return true;
        }else if (JeuEnCours >= 63 && JeuEnCours <= 64){
            if (couleur.equals("jaune") && carte >= 46 && carte <= 60){
                return true;
            }else if ((couleur.equals("vert")) && (carte >= 31 && carte <= 45)){
                return true;
            }else if (couleur.equals("bleu") && carte >= 16 && carte <= 30){
                return true;
            }else if (couleur.equals("rouge") && carte >= 1 && carte <= 15) {
                return true;
            }
        }

        int couleurCarte = (carte - 1) / 15;
        int valeurCarte = (carte - 1) % 15 + 1;

        int couleurJeuEnCours = (JeuEnCours - 1) / 15;
        int valeurJeuEnCours = (JeuEnCours - 1) % 15 + 1;

        // Vérification de la couleur ou de la valeur
        if (couleurCarte == couleurJeuEnCours || valeurCarte == valeurJeuEnCours){
            return true;
        } else if (valeurCarte == 10 && valeurJeuEnCours == 11 || valeurCarte == 11 && valeurJeuEnCours == 10 ){
            return true;
        }else if (valeurCarte == 12 && valeurJeuEnCours == 13 || valeurCarte == 13 && valeurJeuEnCours == 12){
            return true;
        }else if (valeurCarte == 14 && valeurJeuEnCours == 15 || valeurCarte == 15 && valeurJeuEnCours == 14){
            return true;
        }
        return false;
    }

    public static boolean peutPoserBis (int cartes,ArrayList<Integer> cartesJ){
        for (int i=0; i<cartesJ.size(); i++){
            if( cartesJ.get(i) == cartes) {
                return true;
            }
        }
        return false;
    }

    public static void poserCarte (int JeuEnCours, ArrayList<Integer> CartesJ, ArrayList<Integer> TasJeu, String couleur){
        Scanner scanner = new Scanner(System.in);
        boolean exist = false;

        while (!exist) {
            System.out.println("Veuillez saisir la carte que vous voulez poser : ");
            int carteChoisie = scanner.nextInt();
            scanner.nextLine();

            if (peutPoser(carteChoisie, JeuEnCours,couleur) && peutPoserBis(carteChoisie,CartesJ)) {
                JeuEnCours = carteChoisie;
                exist = true;
                TasJeu.add(JeuEnCours);
                CartesJ.remove(Integer.valueOf(carteChoisie));

            } else {
                System.out.println("Vous ne pouvez pas poser cette carte !");
                exist = false;
            }
        }

    }

    public static void unoContreUno(int JeuEnCours, ArrayList<Integer> CarteJ, ArrayList<Integer> TasJeu, ArrayList<Integer> Pioche){
        Scanner scanner = new Scanner(System.in);
        boolean choice = false;
        while(!choice){
            System.out.print("ECRIVEZ Uno OU Contre Uno !!!");
            String derniereCarte = scanner.nextLine();
            if (derniereCarte.equals("Contre Uno")) {
                System.out.println("CONTRE UNO");
                piocherCarte(Pioche, TasJeu, CarteJ,2);
                choice = true;
            } else if (!derniereCarte.equals("Uno")) {
                choice= false;
            }else{
                choice = true;
            }

        }
    }

    public static int traitementCarteSpecial(int Carte) {
        if (Carte % 15 == 10 || Carte % 15 == 11 || Carte % 15== 12 || Carte % 15 == 13) {
            return 1;
        }else if (Carte % 15 == 14 || Carte % 15 == 0) {
            return 2;
        }else if (Carte ==61 || Carte == 62 ) {
            return 3;
        }else if (Carte ==63 || Carte == 64 ) {
            return 4;
        }
        else {
            return 0;
        }
    }

    public static String carteBonus(){
        Scanner scanner = new Scanner(System.in);
        String couleur ="";
        while (!couleur.equals("jaune") && !couleur.equals("vert") && !couleur.equals("rouge") && !couleur.equals("bleu")){
            System.out.println("Choisissez la couleur :");
            couleur = scanner.nextLine();
        }
        return couleur;
    }



    public static void piocherCarte(ArrayList<Integer> Pioche,ArrayList<Integer> newPioche, ArrayList<Integer> CarteJ,  int ajout){
        if(Pioche.size() - 1 - ajout > 1){
            for (int i=0; i<ajout; i++) {
                CarteJ.add(Pioche.remove(i));
            }
        } else {
            for (int i=0; i<newPioche.size()-1; i++) {
                Pioche.add(newPioche.remove(i));
            }
            for (int i=0; i<ajout; i++) {
                CarteJ.add(Pioche.remove(i));
            }


        }

    }

    public static void piocherCarteSpecial(ArrayList<Integer> Pioche,ArrayList<Integer> newPioche, ArrayList<Integer> CarteJ,  int ajout){

        if(Pioche.size() - 1 - ajout > 1){
            for (int i=0; i<ajout; i++) {
                CarteJ.add(Pioche.remove(i));
            }

        } else if (newPioche.size() + Pioche.size() <= ajout+1 ) {
            System.out.println("Vous ne pouvez pas utiliser cette carte");

        }else{
            for (int i=0; i<newPioche.size(); i++) {
                Pioche.add(newPioche.remove(i));
            }
            for (int i=0; i<ajout; i++) {
                CarteJ.add(Pioche.remove(i));
            }
        }
    }
}