
/**
 * Interface qui fournit les methodes pour consulter et modifier le numero 
 * d'identification d'une groupe.
 * 
 * @author melanie lord
 * @version 17/05/2017
 */
public interface IGroupe {
   
   /**
    * Permet d'obtenir le numero d'indentification de ce groupe.
    * 
    * @return le numero d'identification de ce groupe.
    */
   int getId();
   
   /**
    * Permet de modifier le numero d'identification de ce groupe par
    * l'id donne en parametre.
    * 
    * @param id le nouveau numero d'identification de ce groupe.
    */
   void setId(int id);
  
}
