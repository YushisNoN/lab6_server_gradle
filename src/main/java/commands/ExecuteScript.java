package commands;

import commands.exceptions.ReccursionFoundException;
import commands.exceptions.WrongArgumentsAmountException;
import models.IncorrectIntegerValueException;
import models.IncorrectStringValueException;
import utils.console.ConsoleHandler;
import utils.files.FileReader;
import utils.kernel.Kernel;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ExecuteScript extends Command {
    private Kernel kernel;
    private Set<String> finishedScripts = new HashSet<>();

    public ExecuteScript(Kernel kernel) {
        super();
        this.isNeedArguments = true;
        this.commandArguments = 1;
        this.kernel = kernel;
    }

    @Override
    public void execute() throws WrongArgumentsAmountException {
        throw new WrongArgumentsAmountException();
    }

    @Override
    public void execute(String[] arguments)
            throws WrongArgumentsAmountException, IncorrectStringValueException, IncorrectIntegerValueException {
        if (arguments.length != this.commandArguments) {
            throw new WrongArgumentsAmountException();
        }
        if (arguments[arguments.length - 1].matches("^-?\\d+$") == true) {
            throw new IncorrectStringValueException();
        }
        try {
            FileReader fileReader = new FileReader();
            List<String> commandsList = fileReader.read(arguments[arguments.length - 1]);
            if (this.finishedScripts.contains(arguments[arguments.length - 1])) {
                throw new ReccursionFoundException();
            }
            String input = String.join("\n", commandsList) + "\n";
            InputStream scriptInput = new ByteArrayInputStream(input.getBytes());
            System.out.println(scriptInput);
            InputStream originalInput = System.in;
            System.setIn(scriptInput);
            this.kernel.consoleManager = new ConsoleHandler();
            this.kernel.runProgram();
            System.setIn(originalInput);
            this.finishedScripts.add(arguments[arguments.length - 1]);

        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (ReccursionFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

}
