import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 *
 * Created by bruno on 2017-05-26.
 */
public class ListeGroupesArrayList<T extends IGroupe> implements IListeGroupes<T> {

    // Attributs d"instances ===============================================================================================

    private ArrayList<ArrayList<T>> elements;
    private int nbrElements;

    // Constructeur ========================================================================================================

    public ListeGroupesArrayList() {
        setElements(new ArrayList<ArrayList<T>>());
    }

    // Méthodes =============================================================================================================

    /**
     * Ajoute l'element donne a cette ListeGroupes.
     *
     * Si le groupe dont le numero d'identification est element.getId() existe deja
     * dans cette listeGroupes, l'element est ajoute A LA FIN du groupe existant
     * SI ET SEULEMENT S'IL n'existe aucun element de ce groupe etant egal a
     * l'element donne. Autrement, l'element donne n'est pas ajoute.
     *
     * Si le groupe element.getId() n'existe pas dans cette listeGroupes, un nouveau
     * groupe est ajoute (A LA FIN de cette listeGroupes) et l'element donne devient
     * le premier element du nouveau groupe element.getId().
     *
     * @param element l'element a ajouter a cette listeGroupes.
     * @return true si l'element donne est ajoute, false sinon.
     * @throws NullPointerException si l'element donne est null.
     */
    @Override
    public boolean ajouter(T element) {
        boolean isAjoute = false;

        if (element == null) {
            throw new NullPointerException();
        }

        if (groupeExiste(element.getId())) {

            final int groupePosition = trouveGroupeIndex(element.getId());

            if (!elementExiste(element)) {
                getElements().get(groupePosition).add(0, element);
                setNbrElements(getNbrElements() + 1);
                isAjoute = true;
            }

        } else {

            final int position = nbrGroupes();
            getElements().add(new ArrayList<T>());
            getElements().get(position).add(element);
            setNbrElements(getNbrElements() +1);
            isAjoute = true;

        }

        return isAjoute;
    }

    /**
     * Supprime de cette listeGroupes le groupe dont l'id est l'idGroupe donne.
     * Si le groupe ayant l'idGroupe donne n'existe pas dans cette listeGroupes,
     * celle-ci demeure inchangee.
     *
     * @param idGroupe le numero d'identification du groupe a supprimer.
     * @return le nombre d'elements dans le groupe supprime ou 0 si la suppression n'a pas lieu.
     */
    @Override
    public int supprimerGroupe(int idGroupe) {
        
        if (groupeExiste(idGroupe)) {
            final int positionGroupe = trouveGroupeIndex(idGroupe);
            int nbGroupElement = getElements().get(positionGroupe).size();
            setNbrElements(getNbrElements() - nbGroupElement);
            getElements().remove(positionGroupe);

            return nbGroupElement;
        }

        return 0;
    }

    /**
     * Supprime l'element donne de son groupe d'appartenance.
     * <p>
     * Si apres suppression de l'element le groupe est vide, il est supprime
     * de cette listeGroupes.
     * <p>
     * Si le groupe element.getId() n'existe pas dans cette listeGroupes ou
     * si l'element donne n'est pas dans le groupe element.getId(), cette
     * listeGroupes demeure inchangee.
     *
     * @param element l'element a supprimer de cette listeGroupes.
     * @return true si l'element est supprime, false sinon.
     * @throws NullPointerException si element est null.
     */
    @Override
    public boolean supprimerElement(T element) {
        if (element == null) {
            throw new NullPointerException();
        }

        if (groupeExiste(element.getId())) {
            if (elementExiste(element)) {
                final int groupePosition = trouveGroupeIndex(element.getId());
                final int elementPosition = trouveElementIdex(element);
                getElements().get(groupePosition).remove(elementPosition);
                this.nbrElements -= 1;

                if (getElements().get(groupePosition).isEmpty()) {
                    getElements().remove(groupePosition);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Permet d'obtenir le numero d'identification (l'id) du groupe ayant la
     * postion donnee dans cette listeGroupes, si cette position est valide.
     * Une position valide doit etre comprise entre 0 et nbrGroupes() - 1
     * inclusivement.
     *
     * @param position la position, dans cette listeGroupes, du groupe dont on
     *                 veut obtenir l'id.
     * @return l'id du groupe ayant la position donnee.
     * @throws NoSuchElementException si la position donnee n'est pas une position
     *                                valide dans cette listeGroupes.
     */
    @Override
    public int obtenirIdGroupe(int position) {

        if (position < 0 || position > nbrGroupes() - 1) {
            throw new NoSuchElementException();
        }

        return getElements().get(position).get(0).getId();
    }

    /**
     * Permet d'obtenir l'element qui se trouve a la position donnee, dans le
     * groupe dont l'id est l'idGroupe donne, si la position donnee est valide.
     * Une position valide doit etre comprise entre 0 et taille(idGroupe) - 1
     * inclusivement.
     *
     * @param idGroupe l'id du groupe dans lequel rechercher l'element a retourner.
     * @param position la postition de l'element a retourner, dans le groupe dont
     *                 l'id est l'idGroupe donne.
     * @return l'element a la position donnee, dans le groupe dont l'id est
     * l'idGroupe donne.
     * @throws NoSuchElementException si le groupe ayant l'idGroupe donne n'existe
     *                                pas dans cette listeGroupe ou si la position donnee n'est pas un
     *                                indice valide dans le groupe dont l'id est l'idGroupe donne.
     */
    @Override
    public T obtenirElement(int idGroupe, int position) {
        if (!groupeExiste(idGroupe)) {
            throw new NoSuchElementException();
        }

        final int positionGroup = trouveGroupeIndex(idGroupe);
        if (position < 0 || position > getElements().get(positionGroup).size() - 1) {
            throw new NoSuchElementException();
        }

        return getElements().get(positionGroup).get(position);
    }

    /**
     * Permet d'obtenir le nombre d'elements presents dans cette listeGroupes.
     *
     * @return le nombre d'elements presents dans cette listeGroupes.
     */
    @Override
    public int taille() {
        int nbElements = 0;

        for (int i = 0; i < this.elements.size(); i++) {
            for (int j = 0; j < this.elements.get(i).size(); j++) {
                nbElements += 1;
            }
        }

        return nbElements;
    }

    /**
     * Permet d'obtenir le nombre d'elements presents dans le groupe ayant
     * comme numero d'identification l'idGroupe donne.
     *
     * @param idGroupe le numero d'identification du groupe dont on veut
     *                 le nombre d'elements.
     * @return le nombre d'elements dans le groupe ayant l'idGroupe donne ou
     *         -1 si l'idGroupe donne ne correspond a aucun groupe dans cette
     *         listeGroupes.
     */
    @Override
    public int taille(int idGroupe) {
        return groupeExiste(idGroupe) ? this.elements.get(idGroupe).size() : -1;
    }

    /**
     * Permet d'obtenir le nombre de groupes dans cette listeGroupes.
     *
     * @return le nombre de groupes dans cette listeGroupes.
     */
    @Override
    public int nbrGroupes() {
        return this.elements.size();
    }

    /**
     * Teste si le groupe ayant l'idGroupe donne existe dans cette listeGroupes.
     *
     * @param idGroupe le groupe dont on teste l'existence.
     * @return true si le groupe ayant l'idGroupe donne existe dans cette listeGroupes, false sinon.
     */
    @Override
    public boolean groupeExiste(int idGroupe) {

        for (ArrayList<T> group : this.elements) {
            if (group.get(0).getId() == idGroupe) {
                return true;
            }
        }

        return false;
    }

    /**
     * Teste si l'element donne existe dans cette listeGroupes (s'il existe dans
     * le groupe element.getId() de cette listeGroupes). Un element e1 existe dans
     * un groupe si ce groupe contient un element e2 tel que e1.equals(e2)
     * retourne true.
     *
     * @param element l'element dont on teste l'existence.
     * @return true si element existe dans cette listeGroupes, false sinon.
     * @throws NullPointerException si element est null.
     */
    @Override
    public boolean elementExiste(T element) {
        if (element == null) {
            throw new NullPointerException();
        }

        if (groupeExiste(element.getId())) {
            for (T groupe : this.elements.get(trouveGroupeIndex(element.getId()))) {
                if (groupe.equals(element)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Transfere les elements du groupe dont le numero d'identification est
     * idGroupe2 dans le groupe ayant le numero d'identification idGroupe1, en
     * respectant les regles donnees ci-dessous.
     * <p>
     * 1. Les deux groupes donnes (celui ayant l'idGroupe1 et celui ayant
     * l'idGroupe2) doivent exister dans cette listeGroupes pour que le
     * transfert ait lieu.
     * <p>
     * 2. Les elements du groupe ayant l'idGroupe2 doivent etre transferes DANS
     * L'ORDRE (du premier au dernier), A LA FIN du groupe ayant l'idGroupe1.
     * <p>
     * 3. L'appel de element.getId() sur chaque element transfere doit retourner
     * idGroupe1 (et non plus idGroupe2) apres le transfert (les elements
     * transferes doivent changer leur idGroupe pour celui du groupe
     * dans lequel ils sont transferes).
     * <p>
     * 4. Apres le transfert, il ne doit y avoir aucun doublon dans le groupe
     * ayant l'idGroupe1 (on ne transfere pas les doublons).
     * <p>
     * 5. Apres le transfert, le groupe ayant l'idGroupe2 n'existe plus dans cette
     * listeGroupes.
     *
     * @param idGroupe1 le numero d'identification du groupe qui recevra les
     *                  elements du groupe ayant l'idGroupe2.
     * @param idGroupe2 le numero d'identification du groupe dont on veut
     *                  transferer les elements dans le groupe ayant l'idGroupe1.
     * @return le nombre d'elements du groupe ayant l'idGroupe2 qui ont ete
     * transferes dans le groupe ayant l'idGroupe1.
     */
    @Override
    public int transferer(int idGroupe1, int idGroupe2) {

        if (groupeExiste(idGroupe1) && groupeExiste(idGroupe2)) {
            final int posGroup1 = trouveGroupeIndex(idGroupe1);
            final int posGroup2 = trouveGroupeIndex(idGroupe2);

            for (T elementGroup2 : this.elements.get(posGroup2)) {
                for (T elementGroup1 : this.elements.get(posGroup1)) {
                    if (!elementGroup2.equals(elementGroup1)) {
                        elementGroup2.setId(idGroupe1);
                        this.elements.get(posGroup1).add(elementGroup2);
                    }
                }
            }
        }

        return 0;
    }

    /**
     * Remplace l'element1 de cette listeGroupes par l'element2 donne.
     *
     * - Si element1 n'existe pas dans cette listeGroupes, le remplacement n'a
     *   pas lieu.
     *
     * - Si l'id de element1 est different de l'id de l'element2, le remplacement
     *   n'a pas lieu.
     *
     * @param element1 l'element a remplacer par element2.
     * @param element2 l'element qui remplace l'element1.
     * @return true si le remplacement a eu lieu, false sinon.
     * @throws NullPointerException si element1 ou element2 est null.
     */
    @Override
    public boolean remplacer(T element1, T element2) {
        if (element1 == null || element2 == null) {
            throw new NullPointerException();
        }

        if (elementExiste(element1)) {
            if (element1.getId() == element2.getId()) {
                final int posGroup1 = trouveGroupeIndex(element1.getId());
                getElements().get(posGroup1).set(trouveElementIdex(element1), element2);
                return true;
            }
        }
        return false;
    }

    // Méthodes ajoutés qui ne font pas parties de l'interface ===============================================================

    /**
     * Recherche de l'index du groupe dans la liste
     *
     * @param idGroupe Le id du groupe pour lequel on cherche la position
     * @return L'index si le groupe est trouvé, -1 sinon.
     */
    private int trouveGroupeIndex(int idGroupe) {

        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).get(0).getId() == idGroupe) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * Recherche de l'index d'un élément dans un liste de groupe
     *
     * @param element L'élément recherché
     * @return L'index de l'élément s'il est trouvé, -1 sinon
     */
    private int trouveElementIdex(T element) {

        final int groupePos = trouveGroupeIndex(element.getId());

        int i = 0;
        for (T groupeElement : getElements().get(groupePos)) {
            if (groupeElement.equals(element)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    // Getters et Setters ====================================================================================================

    private ArrayList<ArrayList<T>> getElements() {
        return elements;
    }

    private void setElements(ArrayList<ArrayList<T>> elements) {
        this.elements = elements;
    }

    private int getNbrElements() {
        return nbrElements;
    }

    private void setNbrElements(int nbrElements) {
        this.nbrElements = nbrElements;
    }

}
