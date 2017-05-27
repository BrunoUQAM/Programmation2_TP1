/**
 *
 *
 * Created by bruno on 2017-05-26.
 */
public class testeurListe {

    public static void main(String [] arg) {
        //test1();
        test2();
        //test3();
    }

    private static void test1() {
        AbonneRevue kim = new AbonneRevue(3, "kim");
        AbonneRevue max = new AbonneRevue(3, "max");
        AbonneRevue bob = new AbonneRevue(3, "bob");
        AbonneRevue joe = new AbonneRevue(3, "joe");

        IListeGroupes<AbonneRevue> liste = new ListeGroupesArrayList<>();

        liste.ajouter(kim);
        liste.ajouter(max);
        liste.ajouter(bob);
        liste.ajouter(bob);

        System.out.println("Taille de la liste : " + liste.taille());
        System.out.println("Max existe ? : " + liste.elementExiste(bob));
    }

    private static void test2() {
        AbonneRevue e1      = new AbonneRevue(4, "e1");
        AbonneRevue e2      = new AbonneRevue(1, "e2");
        AbonneRevue e3      = new AbonneRevue(7, "e3");
        AbonneRevue e4      = new AbonneRevue(4, "e4");
        AbonneRevue e5      = new AbonneRevue(3, "e5");
        AbonneRevue e1_2    = new AbonneRevue(3, "e1");
        AbonneRevue e6      = new AbonneRevue(7, "e6");
        AbonneRevue e7      = new AbonneRevue(3, "e7");
        AbonneRevue e4_2    = new AbonneRevue(4, "e4");
        AbonneRevue e8      = new AbonneRevue(3, "e8");

        IListeGroupes<AbonneRevue> liste = new ListeGroupesArrayList<>();

        liste.ajouter(e1);
        liste.ajouter(e2);
        liste.ajouter(e3);
        liste.ajouter(e4);
        liste.ajouter(e5);
        liste.ajouter(e1_2);
        liste.ajouter(e6);
        liste.ajouter(e7);
        liste.ajouter(e4_2);
        liste.ajouter(e8);

        //liste.supprimerElement(e3);
        //liste.supprimerElement(e6);

        //liste.supprimerGroupe(7);
        //liste.supprimerGroupe(12);

        liste.transferer(3, 7);
    }

    private static void test3() {
        AbonneRevue kim = new AbonneRevue(3, "kim");
        AbonneRevue max = new AbonneRevue(3, "max");

        IListeGroupes<AbonneRevue> liste = new ListeGroupesArrayList<>();

        liste.ajouter(kim);
        liste.ajouter(max);
        System.out.println("Taille de la liste : " + liste.taille());
        System.out.println("Est-ce que max existe : " + liste.elementExiste(max));

        try {
            liste.ajouter(null);
            System.out.println("ERREUR");
        } catch (NullPointerException e) {
            System.out.println("OK");
        }
    }
}
