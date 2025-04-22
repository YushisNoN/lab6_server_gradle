package commands;

import commands.exceptions.WrongArgumentsAmountException;
import models.IncorrectIntegerValueException;
import models.IncorrectStringValueException;

public interface Executable {

    public String getDescription();

    public void execute() throws WrongArgumentsAmountException;

    public void execute(String[] arguments)
            throws WrongArgumentsAmountException, IncorrectStringValueException, IncorrectIntegerValueException, WrongArgumentsAmountException, IncorrectStringValueException;

    boolean getNeededArguments();
}
