package model.State;

import lombok.AllArgsConstructor;
import model.Client;
@AllArgsConstructor
public class SuccesState implements State {
        private Client client ;
        private int index;

        public void doAction(Context context) {
                client.sendCommand("delLoc");
                client.sendCommand(String.valueOf(index));
                String s=client.readMessage();

        }
}
