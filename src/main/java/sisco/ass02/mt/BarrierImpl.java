package sisco.ass02.mt;

/**
 * La stessa barriera viene usata più volte, quindi teniamo il numero del
 * ciclo, ogni thread aspetta che finisca il giro in cui è
 * arrivato. Così un thread veloce che rientra subito nella barriera non
 * si confonde con quelli del giro precedente.
 */
public class BarrierImpl implements Barrier {

	private final int nParticipants;   // quanti thread devono arrivare
	private int nArrived;              // quanti sono arrivati in questo giro
	private int generation;            // numero del giro corrente

	public BarrierImpl(int nParticipants) {
		this.nParticipants = nParticipants;
		this.nArrived = 0;
		this.generation = 0;
	}

	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {
		int myGen = generation;
		nArrived++;
		if (nArrived == nParticipants) {
			// sono l'ultimo: azzero, passo al giro dopo e sveglio tutti
			nArrived = 0;
			generation++;
			notifyAll();
		} else {
			// aspetto che il mio giro finisca.
			while (myGen == generation) {
				wait();
			}
		}
	}
}
