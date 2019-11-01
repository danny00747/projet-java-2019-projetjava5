package unité;

import java.util.Scanner;

public class Unité {
	//Déclaration des différentes variable
	String nom;
	String nomBonus;
	String nomJoueur;

	static int nombresUnite = 6;
	int longueurCase;
	int LargeurCase;
	int aireTotal;

	
	Boolean detruit;
	Boolean bonus;
	Boolean statutJ; // savoir si c'est à lui de jouer

	/**
	 * Constructeur pour les unité avec un bonus
	 * */
	
	public Unité( String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus, String nomBonus) {
		this.nom = nom;
		this.longueurCase = longueurCase;
		this.LargeurCase = LargeurCase;
		this.aireTotal = aireTotal;
		this.detruit = detruit;
		this.bonus = bonus;
		this.nomBonus = nomBonus;
	}
	/**
	 * Constructeur pour les unité sans le bonus
	 * */
	
	public Unité( String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus) {
		this.nom = nom;
		this.longueurCase = longueurCase;
		this.LargeurCase = LargeurCase;
		this.aireTotal = aireTotal;
		this.detruit = detruit;
		this.bonus = bonus;
	}
	
	/**
	 * Constructeur pour le statut des joueurs et son nom
	 * */
	public Unité(String nomJoueur,Boolean statutJ) {
		this.nomJoueur = nomJoueur;
		this.statutJ = statutJ;
	}
	
	public static void Bonus(Boolean Bonus,String element) {
		if(Bonus == true) {
			System.out.println(element +" a bien un bonus");
		}
		else {
			System.out.println(element +" n'a pas de bonus");
		}
	}
	public static void Detruit(int aire,String element, Boolean detruit) {
		if(aire == 0) {
			System.out.println(element +" a été détruit");
			detruit = false;
			nombresUnite--;
		}
		else {
			System.out.println(element +" est toujours en vie");
			detruit = true;
		}
	}
		
	public static void main( String [] args) {
		/**
		 * Information sur le joueur
		 * */
	    Scanner NomJoueur1 = new Scanner(System.in);
	    String nomJoueur1;
	    
		System.out.println("Veuillez enter un pseudo/Nom");
		System.out.println("---------------------------");

	    nomJoueur1 = NomJoueur1.nextLine();
		
		/**
		 * Boucle pour attribuer la position des unités
		 * */
		
	   String TableauUnite[][] = new String[6][3];
	   int UniteJoueur = 0;
	   while(UniteJoueur < 6) {
		   
		    //Notre objet Scanner
		    Scanner NomUnite = new Scanner(System.in);
		    Scanner Coordonee1 = new Scanner(System.in);
		    Scanner Coordonee2 = new Scanner(System.in);

		    String nomUnite;
		    String coordonee1;
		    String coordonee2;

		    
			System.out.println("Eléments pour placer les différentes unitées");
			System.out.println("---------------------------");
			System.out.println("Entrer le nom de l'unité");
			System.out.println("Entrer la coordonné du début");
			System.out.println("Entrer la coordonné de fin");

		    nomUnite = NomUnite.nextLine();
		    coordonee1 = Coordonee1.nextLine();
		    coordonee2 = Coordonee2.nextLine();
 
		    TableauUnite[UniteJoueur][0] = nomUnite;
		    TableauUnite[UniteJoueur][1] = coordonee1;	
		    TableauUnite[UniteJoueur][2] = coordonee2;	   
		    
		    UniteJoueur++;

	   }	
	   System.out.println(TableauUnite[0][0]+" "+TableauUnite[1][0]); 
	    

		// String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus, String nomBonus
		//String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus
		Unité batiment1 = new Unité("Airport", 2, 4, 8, false, true, "Air-Strike");
		Unité batiment2 = new Unité("Radar Tower", 2, 3, 6, false, true, "Radar Discovery");
		Unité batiment3 = new Unité("Headquarter", 2, 2, 4, false, false);
		Unité vehicule1 = new Unité("Railway Gun", 1, 6, 6, false, true, "Big-shot");
		Unité vehicule2 = new Unité("MMRL", 2, 2, 4, false, true, "Rocket-strike");
		Unité vehicule3 = new Unité("Tank", 1, 2, 2, false, false);
		Unité joueur1 = new Unité(nomJoueur1,false);
		Unité joueur2 = new Unité("Marc",false);


		
		//while( nombresUnite > 0) {
		    
			/**
			 * Information sur une position à vérifier
			 * */
		    Scanner PositionaToucher = new Scanner(System.in);
		    String positionaToucher;
		    
			System.out.println("Veuillez donner une position à cibler");
			System.out.println("---------------------------");
	
			positionaToucher = PositionaToucher.nextLine();
			System.out.println(positionaToucher);
			
			System.out.println("Batiments :\n");
			Bonus(batiment1.bonus, batiment1.nom);
			Bonus(batiment2.bonus, batiment2.nom);
			Bonus(batiment3.bonus, batiment3.nom);
			System.out.println("\n");
			Detruit(batiment1.aireTotal, batiment1.nom, batiment1.detruit);
			Detruit(batiment2.aireTotal, batiment2.nom, batiment2.detruit);
			Detruit(batiment3.aireTotal, batiment3.nom, batiment3.detruit);
			
			System.out.println("--------------------------------------\nVéhicule : \n");
			
			Bonus(vehicule1.bonus, vehicule1.nom);
			Bonus(vehicule2.bonus, vehicule2.nom);
			Bonus(vehicule3.bonus, vehicule3.nom);
			System.out.println("\n");
			Detruit(vehicule1.aireTotal, vehicule1.nom, vehicule1.detruit);
			Detruit(vehicule2.aireTotal, vehicule2.nom, vehicule2.detruit);
			Detruit(vehicule3.aireTotal, vehicule3.nom, vehicule3.detruit);
			
			System.out.println(joueur1.nomJoueur);
			System.out.println(joueur2.nomJoueur);


			//nombresUnite--;
			
		//}
			//System.out.println("\n La partie est fini et le gagnant est :");


	}


}
