package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Server {
    private static final int BUFFER_SIZE = 1024;
    private static final int PORT = 2205;
    private DatagramChannel datagramChannel;

    private SocketAddress clientAddress;
    private Selector selector;

    public Server() {

    }

    public int getBufferSize(){
        return BUFFER_SIZE;
    }

    public void start() throws IOException {
        this.datagramChannel = DatagramChannel.open();
        this.datagramChannel.configureBlocking(false);
        this.datagramChannel.bind(new InetSocketAddress(PORT));
        this.selector = Selector.open();
        this.datagramChannel.register(this.selector, SelectionKey.OP_READ);
        System.out.println("Сервер запущен на порту: " + PORT);
    }

    public void stop() {
        try {
            if(this.selector != null) {
                this.selector.close();
            }
            if(this.datagramChannel != null) {
                this.datagramChannel.close();
            }
            System.out.println("Сервер успешно остановлен");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public SocketAddress getClientAddress() {
        return this.clientAddress;
    }
    public DatagramChannel getDatagramChannel() {
        return this.datagramChannel;
    }

    public Selector getSelector() {
        return this.selector;
    }

    public void setClientAddress(SocketAddress addr) {
        this.clientAddress = addr;
    }
}
