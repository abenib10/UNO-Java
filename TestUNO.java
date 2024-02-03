import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestUNO {

    @Test
    public void testRemplirMelangerPioche() {
        ArrayList<Integer> pioche = new ArrayList<>();
        UNO.remplirMelangerPioche(pioche);

        // Vérifie que la pioche est correctement remplie et mélangée
        assertEquals(64, pioche.size());
    }

    @Test
    public void testDistributionCartes() {
        ArrayList<Integer> cartesJ1 = new ArrayList<>();
        ArrayList<Integer> cartesJ2 = new ArrayList<>();
        ArrayList<Integer> pioche = new ArrayList<>();

        UNO.remplirMelangerPioche(pioche);
        UNO.distributionCartes(cartesJ1, cartesJ2, pioche);

        // Vérifie que chaque joueur a 7 cartes
        assertEquals(7, cartesJ1.size());
        assertEquals(7, cartesJ2.size());

        // Vérifie que la pioche a 14 cartes en moins (7 cartes pour chaque joueur)
        assertEquals(50, pioche.size());
    }

   @Test
    public void testPeutPoser() {
        // Vérifie la méthode peutPoser
        assertTrue(UNO.peutPoser(16, 1, "bleu"), "Carte posable"); // Ici un 1 - bleu peut se poser sur un 1 - rouge

        assertFalse(UNO.peutPoser(36, 16, "bleu"), "Carte non posable"); // Ici un 6 - vert ne peut pas se poser sur un 1 - bleu
    }

    @Test
    public void testTraitementCarteSpecial() {
        // Test avec une carte normale, ici un 5 jaune
        assertEquals(0, UNO.traitementCarteSpecial(5));

        // Test avec une carte "+2"
        assertEquals(2, UNO.traitementCarteSpecial(14));

        // Test avec une carte "+4"
        assertEquals(4, UNO.traitementCarteSpecial(64));

        // Test avec une carte "Bonus"
        assertEquals(3, UNO.traitementCarteSpecial(61));

        // Test avec une carte "Changement de sens"
        assertEquals(1, UNO.traitementCarteSpecial(12));

        // Test avec une carte "Passer le tour"
        assertEquals(1, UNO.traitementCarteSpecial(11));
    }
}
