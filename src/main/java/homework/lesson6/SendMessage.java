package homework.lesson6;

import homework.lesson6.Runnable.InputMessage;
import homework.lesson6.Runnable.OutputMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMessage {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread threadIn;
    private Thread threadOut;

    public SendMessage() {
        this.socket = new Socket();
    }

    public SendMessage(String addresServer, int portServer) throws IOException {
        this.socket = new Socket(addresServer, portServer);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void start() throws InterruptedException, IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        threadIn = new Thread(new InputMessage(in));
        threadOut = new Thread(new OutputMessage(out));
        threadIn.start();
        threadOut.start();

        do {
            Thread.sleep(1);
        } while (threadIn.isAlive() || threadOut.isAlive());
    }

    private void sockedClose() throws IOException {
        this.socket.close();
    }

}