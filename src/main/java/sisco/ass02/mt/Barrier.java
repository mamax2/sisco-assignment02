package sisco.ass02.mt;

/**
 * Barriera per chi chiama hitAndWaitAll() si blocca finché non sono arrivati
 * alla barriera tutti i partecipanti.
 */
public interface Barrier {

	void hitAndWaitAll() throws InterruptedException;

}
