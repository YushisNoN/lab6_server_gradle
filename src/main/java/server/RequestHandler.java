package server;

import models.Product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class RequestHandler {
    private int buffer_size;
    private SocketAddress clientAdress;
    private SelectionKey key;
    public RequestHandler(Server server) {
        this.buffer_size = server.getBufferSize();
    }
    public Object readRequestCommand(SelectionKey key) throws IOException, ClassNotFoundException {
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(65536);
        this.key = key;
        this.clientAdress = datagramChannel.receive(buffer);
        if(clientAdress != null) {
            buffer.flip();
            byte[] dataBytes = new byte[buffer.remaining()];
            buffer.get(dataBytes);
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object receivedObject = objectInputStream.readObject();

                if(receivedObject instanceof Product) {
                    return receivedObject;
                }
            } catch (IOException | ClassNotFoundException e) {
                return new String(dataBytes);
            }
        }
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return null;
    }

    public SelectionKey getKey() {
        return this.key;
    }

    public int getBufferSize() {
        return this.buffer_size;
    }
    public SocketAddress getClientAdress() {
        return this.clientAdress;
    }
}
