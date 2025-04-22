package utils.kernel;


import commands.*;
import commands.exceptions.WrongArgumentsAmountException;
import commands.exceptions.WrongCommandFoundException;
import managers.CommandManager;
import managers.ProductManager;
import models.IdGenerator;
import models.Product;
import server.RequestHandler;
import server.ResponseHandler;
import server.Server;
import utils.IDreceive;
import utils.console.ConsoleHandler;
import utils.files.FileReader;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class Kernel {
    private Thread consoleInputThread;
    private Scanner consoleScanner;
    private volatile boolean consoleThreadRunning = true;
    private boolean exitProgram = false;
    public ConsoleHandler consoleManager = new ConsoleHandler();
    private CommandManager commandManager = new CommandManager();
    private ProductManager<Product> productManager = new ProductManager<Product>();
    private FileReader fileReader = new FileReader();


    private Server server = new Server();
    private RequestHandler requestHandler = new RequestHandler(this.server);
    private ResponseHandler responseHandler = new ResponseHandler(this.server);

    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    private SelectionKey key;
    public void exitProgram() {
        this.consoleThreadRunning = false;
        if (this.consoleInputThread != null) {
            this.consoleInputThread.interrupt();
        }
        if (this.consoleScanner != null) {
            this.consoleScanner.close();
        }

        this.server.stop();
        Executable currentCommand = this.commandManager.getCommandsList().get("save");
        try {
            currentCommand.execute();
        } catch (WrongArgumentsAmountException e) {
            System.out.println(e.getMessage());
        }
        this.exitProgram = true;
    }

    public Kernel() {
        try {
            this.fileReader.read(this.productManager);

        } catch (FileNotFoundException exception) {
            System.out.println("Файла не существует :(");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public void setCommands() {
        this.commandManager.addCommand(new Help(this.commandManager.getCommandsList()));
        this.commandManager.addCommand(new Add(this.productManager, this.responseHandler, this.requestHandler));
        this.commandManager.addCommand(new Info(this.productManager));
        this.commandManager.addCommand(new Show(this.productManager));
        this.commandManager.addCommand(new Clear(this.productManager));
        this.commandManager.addCommand(new UpdateID(this.productManager, this.responseHandler, this.requestHandler));
        this.commandManager.addCommand(new RemoveByID(this.productManager));
        this.commandManager.addCommand(new AddIfMax(this.productManager, this.responseHandler, this.requestHandler));
        this.commandManager.addCommand(new AddIfMin(this.productManager, this.responseHandler, this.requestHandler));
        this.commandManager.addCommand(new RemoveGreater(this.productManager, this.responseHandler, this.requestHandler));
        this.commandManager.addCommand(new CountLessThanPrice(this.productManager));
        this.commandManager.addCommand(new FilterContainsName(this.productManager));
        this.commandManager.addCommand(new RemoveAnyByPrice(this.productManager));
        this.commandManager.addCommand(new Save(this.productManager));
        this.commandManager.addCommand(new Exit(this));
        this.commandManager.addCommand(new ExecuteScript(this));
    }

    public void setOutputToByte() {
        this.originalOut = System.out;
        this.outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    public void setOutputOriginal() {
        System.setOut(this.originalOut);
    }

    public String getOutput() {
        return outputStream.toString();
    }

    public void runProgram() {
        try{
            this.server.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.consoleManager.printStringln("В ожидании запроса от клиента...");

        while (false == this.exitProgram) {
            if (this.exitProgram)
                break;
            try {
                if(this.server.getSelector().select(500) == 0) {
                    continue;
                }
                if (!this.server.getSelector().isOpen()) {
                    this.server.start();
                }
                Set<SelectionKey> selectionKeySet = this.server.getSelector().selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
                while(selectionKeyIterator.hasNext()) {
                    SelectionKey key = selectionKeyIterator.next();

                    if(key.isReadable()) {
                        Object request = this.requestHandler.readRequestCommand(key);
                        this.server.setClientAddress(this.requestHandler.getClientAdress());
                        this.responseHandler.setClientAddress(this.server.getClientAddress());
                        this.responseHandler.setKey(this.requestHandler.getKey());
                        if(request != null) {

                            if(request instanceof Product) {
                                Product product = (Product) request;
                                this.productManager.addProdut(product);
                                IDreceive iDreceive = new IDreceive(this.productManager);
                                iDreceive.updateID();
                            } else if (request instanceof String) {
                                System.out.println((String) request);
                                this.setOutputToByte();
                                this.executeCommand((String) request, key);
                            }
                        }
                    }
                    selectionKeyIterator.remove();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    }

    public void executeCommand(String currentInput, SelectionKey key) {
        String[] currentArguments = Arrays.stream(currentInput.replaceAll("\\s+", " ").trim().split(" "))
                .skip(1).toArray(String[]::new);
        Executable currentCommand = this.commandManager.getCommandsList().get(currentInput.split(" ")[0]);
        try {
            if (null == currentCommand) {
                throw new WrongCommandFoundException();
            } else {
                if (currentCommand.getNeededArguments() || currentArguments.length > 0) {
                    currentCommand.execute(currentArguments);
                } else {
                    currentCommand.execute();
                }
                this.responseHandler = new ResponseHandler(this.server);
            }
        } catch (Exception exception) {
            this.consoleManager.printStringln(exception.getMessage());
        }
        try {
            String response = this.getOutput();
            key.interestOps(SelectionKey.OP_READ);
            this.setOutputOriginal();
            this.responseHandler.sendResponse(key, response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeCommandsFromScript(List<String> commands) {
        for (String command : commands) {
            executeCommand(command, key);
        }
    }
}
