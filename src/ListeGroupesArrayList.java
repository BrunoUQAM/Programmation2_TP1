import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Travail pratique #1
 *
 * Implémeantation de l'interface {@link IListeGroupes} qui est fourni dans l'énoucé du TP.
 *
 * @author Bruno Lavigne
 * Code permanent : LAVB19108108
 * Courriel : lavigne.bruno.2@courrier.uqam.ca
 * Cours : INF2120-31
 * @version 2017-06-04
 */
public class ListeGroupesArrayList<T extends IGroupe> implements IListeGroupes<T> {

    private ArrayList<ArrayList<T>> elements;
    private int nbrElements;

    public ListeGroupesArrayList() {
        setElements(new ArrayList<ArrayList<T>>());
    }

    @Override
    public boolean ajouter(T element) {
        boolean isAjoute = false;

        if (element == null) {
            throw new NullPointerException();
        }

        if (groupeExiste(element.getId())) {

            final int groupePosition = trouveGroupeIndex(element.getId());

            if (!elementExiste(element)) {
                getElements().get(groupePosition).add(element);
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

    @Override
    public int supprimerGroupe(int idGroupe) {

        int nbGroupElement = 0;

        if (groupeExiste(idGroupe)) {
            final int positionGroupe = trouveGroupeIndex(idGroupe);

            nbGroupElement = getElements().get(positionGroupe).size();
            setNbrElements(getNbrElements() - nbGroupElement);
            getElements().remove(positionGroupe);
        }

        return nbGroupElement;
    }

    @Override
    public boolean supprimerElement(T element) {
        boolean elementSupprimer = false;

        if (element == null) {
            throw new NullPointerException();
        }

        if (groupeExiste(element.getId())) {
            if (elementExiste(element)) {
                final int groupePosition = trouveGroupeIndex(element.getId());
                final int elementPosition = trouveElementIdex(element);
                getElements().get(groupePosition).remove(elementPosition);
                setNbrElements(getNbrElements() -1);

                if (getElements().get(groupePosition).isEmpty()) {
                    getElements().remove(groupePosition);
                }

                elementSupprimer = true;

            }
        }

        return elementSupprimer;
    }

    @Override
    public int obtenirIdGroupe(int position) {

        if (position < 0 || position > nbrGroupes() - 1) {
            throw new NoSuchElementException();
        }

        return getElements().get(position).get(0).getId();
    }

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

    @Override
    public int taille() {
        int nbElements = 0;

        for (int i = 0; i < getElements().size(); i++) {
            for (int j = 0; j < getElements().get(i).size(); j++) {
                nbElements += 1;
            }
        }

        return nbElements;
    }

    @Override
    public int taille(int idGroupe) {
        int taille = -1;

        if (groupeExiste(idGroupe)) {
            taille = getElements().get(idGroupe).size();
        }

        return taille;
    }

    /**
     * Permet d'obtenir le nombre de groupes dans cette listeGroupes.
     *
     * @return le nombre de groupes dans cette listeGroupes.
     */
    @Override
    public int nbrGroupes() {
        return getElements().size();
    }

    /**
     * Teste si le groupe ayant l'idGroupe donne existe dans cette listeGroupes.
     *
     * @param idGroupe le groupe dont on teste l'existence.
     * @return true si le groupe ayant l'idGroupe donne existe dans cette listeGroupes, false sinon.
     */
    @Override
    public boolean groupeExiste(int idGroupe) {

        boolean groupeExiste = false;

        final Iterator<ArrayList<T>> iterator = getElements().iterator();
        while(iterator.hasNext() && !groupeExiste) {
            ArrayList<T> group = iterator.next();
            if (group.get(0).getId() == idGroupe) {
                groupeExiste = true;
            }
        }

        return groupeExiste;
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
        boolean elementExiste = false;

        if (element == null) {
            throw new NullPointerException();
        }

        if (groupeExiste(element.getId())) {
            final int groupeIndex = trouveGroupeIndex(element.getId());
            Iterator<T> iterator = getElements().get(groupeIndex).iterator();

            while (iterator.hasNext() && !elementExiste) {
                T groupe = iterator.next();
                if (groupe.equals(element)) {
                    elementExiste = true;
                }
            }
        }

        return elementExiste;
    }

    @Override
    public int transferer(int idGroupe1, int idGroupe2) {

        if (groupeExiste(idGroupe1) && groupeExiste(idGroupe2)) {
            final int posGroup1 = trouveGroupeIndex(idGroupe1);
            final int posGroup2 = trouveGroupeIndex(idGroupe2);

            ArrayList<T> listeTemporaire = new ArrayList<>();

            for (T elementGroup2 : getElements().get(posGroup2)) {
                if (!elementExistDansGroup(idGroupe1, elementGroup2)) {
                    elementGroup2.setId(idGroupe1);
                    listeTemporaire.add(elementGroup2);
                }
            }

            getElements().get(posGroup1).addAll(listeTemporaire);
            getElements().remove(posGroup2);

            return listeTemporaire.size();
        }

        return 0;
    }

    @Override
    public boolean remplacer(T element1, T element2) {
        boolean remplacer = false;

        if (element1 == null || element2 == null) {
            throw new NullPointerException();
        }

        if (elementExiste(element1)) {
            if (element1.getId() == element2.getId()) {
                final int posGroup1 = trouveGroupeIndex(element1.getId());
                getElements().get(posGroup1).set(trouveElementIdex(element1), element2);
                remplacer = true;
            }
        }
        return remplacer;
    }

    private boolean elementExistDansGroup(int idGroup, T element) {
        boolean elementExist = false;

        if (groupeExiste(idGroup)) {
            final int groupeIndex = trouveGroupeIndex(idGroup);
            final Iterator<T> iterator = getElements().get(groupeIndex).iterator();

            while(iterator.hasNext() && !elementExist) {
                T groupe = iterator.next();
                if (groupe.equals(element)) {
                    elementExist = true;
                }
            }
        }

        return elementExist;
    }

    /**
     * Recherche de l'index du groupe dans la liste
     *
     * @param idGroupe Le id du groupe pour lequel on cherche la position
     * @return L'index si le groupe est trouvé, -1 sinon.
     */
    private int trouveGroupeIndex(int idGroupe) {
        int groupeIndex = -1;
        boolean groupeTrouve = false;

        Iterator<ArrayList<T>> iterator = getElements().iterator();
        int i = 0;

        while (iterator.hasNext() && !groupeTrouve) {
            ArrayList<T> groups = iterator.next();
            if (groups.get(0).getId() == idGroupe) {
                groupeTrouve = true;
                groupeIndex = i;
            }
            i++;
        }

        return groupeIndex;
    }

    /**
     *
     * Recherche de l'index d'un élément dans un liste de groupe
     *
     * @param element L'élément recherché
     * @return L'index de l'élément s'il est trouvé, -1 sinon
     */
    private int trouveElementIdex(T element) {
        int elementIndex = -1;
        boolean groupeTrouve = false;
        final int groupePos = trouveGroupeIndex(element.getId());

        int i = 0;
        final Iterator<T> iterator = getElements().get(groupePos).iterator();
        while (iterator.hasNext() && !groupeTrouve) {
            T groupeElement = iterator.next();
            if (groupeElement.equals(element)) {
                groupeTrouve = true;
                elementIndex = i;
            }
            i++;
        }

        return elementIndex;
    }

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
