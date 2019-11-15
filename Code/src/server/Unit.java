package server;
/**
 * @author Martin Perdaens
 */
import java.util.HashMap;
/*
* Cette classe permet de récupérer toute les informations concernant les différentes unité disponible
* durant la partie.
* 
* Elle sert également à vérifier si une unité ou case à été détruite 
*/
public class Unit {
    /**
     * @param name {String} - Le nom de l'unité
     * @param size {int} -La taille de l'unité
     * @param coordState {String,Boolean}- case toujour en vie true = oui | false = non
     * @param isAlive {Boolean} - unité envie true = oui | false = non
     */
    private String name;
    private int size;
    private boolean isAlive;                     
    private HashMap<String, Boolean> coordState;

    /**
     * description 
     * 
     * @param name reprend le nom de l'unité
     * @param size reprend la taille
     * @param coordState
     * @param isAlive valeur par défaut "true"
     */
    protected Unit(String name, int size) {
        this.name = name;
        this.size = size;
        this.isAlive = true;
        this.coordState = new HashMap<String, Boolean>();
    }

    /**
     * desciption : Initier toutes les cases à true
     * 
     * @param coords {String} - un tabelau des coordonnées
     * 
     */    
    protected void initCoordState(String [] coords) {
    	for(int i= 0; i < coords.length; i++){
            coordState.put(coords[i],true);
        }
    }
    /**
     * Permet de récupérer le nom de l'unité fournit par le joueur
     * @return nom de l'unité
     */
    protected String getName() {
        return name;
    }
    /**
     * Permet de récupérer la taille d'une unité
     * @return taille de l'unité
     */
    protected int getSize() {
        return size;
    }
    protected boolean getCoordState(String key) {
    	return coordState.get(key);
    }
    /**
     * Permet de changer l'état d'une case d'une unité et de vérifier qu'elle case sont toujour en vie
     * @param key {String} - La case qui a été cibler 
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
     * Permet d'indiqué si  l'unité est toujours en vie (jouable)
     * @return l'état de la troupe
     */
    protected boolean getIsAlive(){
    	return isAlive;
    }

}
