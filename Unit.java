package server;
/**
 * @author Martin Perdaens
 */
import java.util.HashMap;
/*
* Cette classe permet de r�cup�rer toute les informations concernant les diff�rentes unit� disponible
* durant la partie.
* 
* Elle sert �galement � v�rifier si une unit� ou case � �t� d�truite 
*/
public class Unit {

    private String name;
    private int size;
    private boolean isAlive;                     
    private HashMap<String, Boolean> coordState;

    /**
     * description 
     * 
     * @param name reprend le nom de l'unit�
     * @param size reprend la taille
     * @param coordState
     * @param isAlive valeur par d�faut "true"
     */
    protected Unit(String name, int size) {
        this.name = name;
        this.size = size;
        this.isAlive = true;
        this.coordState = new HashMap<String, Boolean>();
    }

    /**
     * desciption : Initier toutes les cases � true
     * 
     * @param coords {String} - un tabelau des coordonn�es
     * 
     */    
    protected void initCoordState(String [] coords) {
    	for(int i= 0; i < coords.length; i++){
            coordState.put(coords[i],true);
        }
    }
    /**
     * Permet de r�cup�rer le nom de l'unit� fournit par le joueur
     * @return nom de l'unit�
     */
    protected String getName() {
        return name;
    }
    /**
     * Permet de r�cup�rer la taille d'une unit�
     * @return taille de l'unit�
     */
    protected int getSize() {
        return size;
    }
    protected boolean getCoordState(String key) {
    	return coordState.get(key);
    }
    /**
     * Permet de changer l'�tat d'une case d'une unit� et de v�rifier qu'elle case sont toujour en vie
     * @param key {String} - La case qui a �t� cibler 
     */  
    protected void setCoordState(String key) {
    	coordState.replace(key, false);
    	for (boolean cellValue : coordState.values()) {
    		if(cellValue){
                isAlive = true;
                break;
            }
            isAlive = false;
        }
    }
    /**
     * Permet d'indiqu� si  l'unit� est toujours en vie (jouable)
     * @return l'�tat de la troupe
     */
    protected boolean getIsAlive(){
    	return isAlive;
    }

}
