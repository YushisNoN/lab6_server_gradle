package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Base64;

public class ResponseHandler {
    private final int bufferSize;
    private final DatagramChannel datagramChannel;

    private SocketAddress clientAddress;

    private SelectionKey key;

    public ResponseHandler(Server server) {
        this.bufferSize = server.getBufferSize();
        this.datagramChannel = server.getDatagramChannel();
        this.clientAddress = server.getClientAddress();
    }
    public SelectionKey getKey() {
        return this.key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }
    public void setClientAddress(SocketAddress client) {
        this.clientAddress = client;
    }

    public void sendResponse(SelectionKey key, String response) throws IOException {
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();
        this.key = key;
        if (this.clientAddress != null) {
            byte[] objectBytes = response.getBytes();
            datagramChannel.send(ByteBuffer.wrap(objectBytes), clientAddress);
            System.out.println("Отправка клиенту ответа");
        }
    }
}
