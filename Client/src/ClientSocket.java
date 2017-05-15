import api.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author lampa
 */
public class ClientSocket implements Runnable{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeInt(0);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            boolean resp = in.readBoolean();

            while (true) {
                Message msg = (Message) in.readObject();
                switch (msg.getMessageType()) {
                    case ACTION_RESULT:
                        List<Integer> qtaResourcesList = msg.getQtaList();
                        break;
                    case INFORMATION:
                        System.out.println(msg.getContent());
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
