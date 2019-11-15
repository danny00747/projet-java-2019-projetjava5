package server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class UnitTest2{
	/**
	 * on créé trois unités, une avec la plus grande taille possible, une avec laplus petite taille possible 
	 * */
	Unit testUnit = new Unit("test",2);
	Unit testUnit2 = new Unit("test2",6);
	Unit testUnit3 = new Unit("test3",8);
	private Unit UnitTest2 = new coordState();
	
	/**
	 * On initie les coordonnées pour trois unité : de 2 cases; de 6 cases; de 8 cases.
	 * 
	 * On introduit ces coordonées dans la fonction "initCoordState" 
	 * pour  initier les cases à "true"
	 * 
	 * On introduit ces coordonées de l'unité de 6 cases dans la fonction "setCoordState" 
	 * pour faire passer les cases à "false"
	 * */
	private UnitTest2() {
		String [] coords = {"D1","D2"};
		String [] coords2 = {"A1","A2","A3","A4","A5","A6"};
		String [] coords3 = {"C1","C2","C3","C4","D1","D2","D3","D4"};

		testUnit.initCoordState(coords);
		testUnit2.initCoordState(coords2);
		testUnit3.initCoordState(coords3);
		
		testUnit2.setCoordState("A1");
		testUnit2.setCoordState("A2");
		testUnit2.setCoordState("A3");
		testUnit2.setCoordState("A4");
		testUnit2.setCoordState("A5");
		testUnit2.setCoordState("A6");
	}
	
	/**
	 * Test qui permet de vérifier la taille des unités
	 * on teste 3 unités qui correpondent aux 2 extremes et celle du milieu
	 * */
	@Test
	void testGetSize() {
		//fail("Not yet implemented");
		assertEquals(2, testUnit.getSize());
		assertEquals(6, testUnit2.getSize());
		assertEquals(8, testUnit3.getSize());

	}
	
	/**
	 * Test qui permet de vérifier le nom des unités
	 * Valeur attendue pour "GetName":
	 * ==> Le nom de l'unité
	 * */
	@Test
	void testGetname() {
		//fail("Not yet implemented");
		assertEquals("test", testUnit.getName());
		assertEquals("test2", testUnit2.getName());
		assertEquals("test3", testUnit3.getName());
	}
	
	/**
	 * Test qui permet de vérifier si "les cases" d'une unité ont bien été initiées à "true"
	 * Valeur attendue pour "initCoordState":
	 * ==> true = case en vie
	 * */
	@Test
	void testinitCoordState() {
		//fail("Not yet implemented");
		//unité de 2
		assertEquals(true,testUnit.coordState.get("D1"));
		assertEquals(true,testUnit.coordState.get("D2"));
		
		//unité de 8
		assertEquals(true,testUnit3.coordState.get("C1"));
		assertEquals(true,testUnit3.coordState.get("C2"));
		assertEquals(true,testUnit3.coordState.get("C3"));
		assertEquals(true,testUnit3.coordState.get("C4"));
		assertEquals(true,testUnit3.coordState.get("C5"));
		assertEquals(true,testUnit3.coordState.get("C6"));
		assertEquals(true,testUnit3.coordState.get("C7"));
		assertEquals(true,testUnit3.coordState.get("C8"));
	}
	
	/**
	 * Test qui permet de vérifier la fonction "setCoordState" a bien mis une case à "false"
	 *Valeur attendue pour "setCoordState":
	 * ==> false = case détruite
	 * */
	@Test
	void testsetCoordState() {
		//fail("Not yet implemented");
		assertEquals(false,testUnit2.coordState.get("A1"));
		assertEquals(false,testUnit2.coordState.get("A2"));
		assertEquals(false,testUnit2.coordState.get("A3"));
		assertEquals(false,testUnit2.coordState.get("A4"));
		assertEquals(false,testUnit2.coordState.get("A5"));
		assertEquals(false,testUnit2.coordState.get("A6"));
        }
	
	/**
	 * Teste qui permet de voir si une unité est toujours en vie ou détruite
	 * Valeur attendue pour "getIsAlive" :
	 * ==> true = En vie
	 * ==> false = Détruite
	 * */
	@Test
	void testGetIsAlive() {
		//fail("Not yet implemented");
		assertEquals(true, testUnit.getIsAlive());
		assertEquals(false, testUnit2.getIsAlive());

	}
	}



