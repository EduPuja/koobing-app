
package edu.pujadas.koobing_admin.Utilities;
import edu.pujadas.koobing_admin.Models.Treballador;

public class Session {

    private Treballador workerSession;

    public Session() {
    }

    public Session(Treballador treballador) {
        this.workerSession = treballador;
    }

    public Treballador getWorkerSession() {
        return workerSession;
    }

    public void setWorkerSession(Treballador workerSession) {
        this.workerSession = workerSession;
    }
}

