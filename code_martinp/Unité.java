package unité;

public class Unité {
	static int nombresUnite = 6;
	String nom;
	int longueurCase;
	int LargeurCase;
	int aireTotal;
	Boolean detruit;
	Boolean bonus;
	String nomBonus;
	
	/**
	 * Constructeur pour les unbité avec un bonus
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
	 * Constructeur pour les unbité sans le bonus
	 * */
	
	public Unité( String nom, int longueurCase, int LargeurCase, int aireTotal, Boolean detruit, Boolean bonus) {
		this.nom = nom;
		this.longueurCase = longueurCase;
		this.LargeurCase = LargeurCase;
		this.aireTotal = aireTotal;
		this.detruit = detruit;
		this.bonus = bonus;
	}
	
	public static void Bonus(Boolean Bonus,String element) {
		if(Bonus == true) {
			System.out.println(element +" a bien un bonus");
		}
		else {
			System.out.println(element +" n'a pas de bonus");
		}
	}
	public static void Detruit(int aire,String element) {
		if(aire == 0) {
			System.out.println(element +" a été détruit");
		}
		else {
			System.out.println(element +" est toujours en vie");
		}
	}
		
	public static void main( String [] args) {
		
		Unité batiment1 = new Unité("Airport", 2, 4, 8, false, true, "Air-Strike");
		Unité batiment2 = new Unité("Radar Tower", 2, 3, 6, false, true, "Radar Discovery");
		Unité batiment3 = new Unité("Headquarter", 2, 2, 4, false, false);
		Unité vehicule1 = new Unité("Railway Gun", 1, 6, 6, false, true, "Big-shot");
		Unité vehicule2 = new Unité("MMRL", 2, 2, 4, false, true, "Rocket-strike");
		Unité vehicule3 = new Unité("Tank", 1, 2, 2, false, false);
		
		
		while( nombresUnite > 0) {
						
			System.out.println("Batiments :\n");
			Bonus(batiment1.bonus, batiment1.nom);
			Bonus(batiment2.bonus, batiment2.nom);
			Bonus(batiment3.bonus, batiment3.nom);
			System.out.println("\n");
			Detruit(batiment1.aireTotal, batiment1.nom);
			Detruit(batiment2.aireTotal, batiment2.nom);
			Detruit(batiment3.aireTotal, batiment3.nom);
	
			System.out.println("--------------------------------------\nVéhicule : \n");

			Bonus(vehicule1.bonus, vehicule1.nom);
			Bonus(vehicule2.bonus, vehicule2.nom);
			Bonus(vehicule3.bonus, vehicule3.nom);
			System.out.println("\n");
			Detruit(vehicule1.aireTotal, vehicule1.nom);
			Detruit(vehicule2.aireTotal, vehicule2.nom);
			Detruit(vehicule3.aireTotal, vehicule3.nom);
			
			nombresUnite--;
			
		}
			System.out.println("\n La partie est fini et le gagnant est :");


	}


}
