package models.creators;

import color.Color;
import country.Country;
import models.*;
import utils.console.ConsoleHandler;

public class PersonCreator {
    private ConsoleHandler consoleManager = new ConsoleHandler();
    private Person.PersonBuilder person;

    public PersonCreator() {
        this.person = new Person.PersonBuilder();
    }

    public void askName() {
        boolean passFlag = false;
        String currentInput = null;
        do {
            try {
                System.out.print("Введите имя владельца (не может быть пустой строкой)\n-> ");
                currentInput = this.consoleManager.getInputString().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                if (currentInput.matches(".*\\d.*")) {
                    throw new IncorrectStringValueException();
                }
                this.person.setName(currentInput);
                passFlag = true;
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectStringValueException e) {
                System.out.println(e.getMessage());
            } catch (NullValueException e) {
                System.out.println(e.getMessage());
            }
        } while (false == passFlag);
    }

    public void askHeight() {
        boolean passFlag = false;
        String currentInput = null;
        do {
            try {
                System.out.print("Введите рост в см (рост не может быть меньше 0)\n-> ");
                currentInput = this.consoleManager.getInputString().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                if (currentInput.matches("^-?\\d+(\\.\\d+)?$") == false) {
                    throw new IncorrectIntegerValueException();
                }
                this.person.setHeight(Float.parseFloat(currentInput));
                passFlag = true;
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectIntegerValueException e) {
                System.out.println(e.getMessage());
            } catch (NegativeValueException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: число слишком большое для данного типа");
            }
        } while (false == passFlag);
    }

    public void askNationality() {
        boolean passFlag = false;
        String currentInput = null;
        do {
            try {
                System.out.print("Выберите национальность\n[1]: Француз\n[2]: Индус\n[3]: Японец\n-> ");
                currentInput = this.consoleManager.getInputString().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                if (currentInput.matches("^\\d+$") == false) {
                    throw new IncorrectIntegerValueException();
                }
                switch (Integer.parseInt(currentInput)) {
                    case 1:
                        this.person.setNationality(Country.FRANCE);
                        passFlag = true;
                        break;
                    case 2:
                        this.person.setNationality(Country.INDIA);
                        passFlag = true;
                        break;
                    case 3:
                        this.person.setNationality(Country.JAPAN);
                        passFlag = true;
                        break;
                }
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectIntegerValueException e) {
                System.out.println(e.getMessage());
            } catch (NullValueException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: число слишком большое для данного типа");
            }
        } while (false == passFlag);
    }

    public void askEyeColor() {
        boolean passFlag = false;
        String currentInput = null;
        do {
            try {
                System.out.print("Выберите цвет глаз\n[1]: Карие\n[2]: Голубые\n-> ");
                currentInput = this.consoleManager.getInputString().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                if (currentInput.matches("^\\d+$") == false) {
                    throw new IncorrectIntegerValueException();
                }
                switch (Integer.parseInt(currentInput)) {
                    case 1:
                        this.person.setEyeColor(Color.BLACK);
                        passFlag = true;
                        break;
                    case 2:
                        this.person.setEyeColor(Color.BLUE);
                        passFlag = true;
                        break;
                }
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectIntegerValueException e) {
                System.out.println(e.getMessage());
            } catch (NullValueException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: число слишком большое для данного типа");
            }
        } while (false == passFlag);
    }

    public void askHairColor() {
        boolean passFlag = false;
        String currentInput = null;
        do {
            try {
                System.out.print("Выберите цвет волос\n[1]: Русые\n[2]: Болотные\n[3]: Красные\n[4]: Седые\n-> ");
                currentInput = this.consoleManager.getInputString().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                if (currentInput.matches("^\\d+$") == false) {
                    throw new IncorrectIntegerValueException();
                }
                switch (Integer.parseInt(currentInput)) {
                    case 1:
                        this.person.setHairColor(Color.ORANGE);
                        passFlag = true;
                        break;
                    case 2:
                        this.person.setHairColor(Color.GREEN);
                        passFlag = true;
                        break;
                    case 3:
                        this.person.setHairColor(Color.RED);
                        passFlag = true;
                        break;
                    case 4:
                        this.person.setHairColor(Color.WHITE);
                        passFlag = true;
                        break;
                }
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectIntegerValueException e) {
                System.out.println(e.getMessage());
            } catch (NullValueException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: число слишком большое для данного типа");
            }
        } while (false == passFlag);
    }

    public void askLocation() {
        boolean passFlag = false;
        do {
            try {
                Location location = new Location();
                LocationCreator locationCreator = new LocationCreator();
                locationCreator.askWidth();
                locationCreator.askHeight();
                locationCreator.askDepth();
                location = locationCreator.getLocation();
                this.person.setLocation(location);
                passFlag = true;
            } catch (NullValueException e) {
                System.out.println(e.getMessage());
            }
        } while (false == passFlag);
    }

    public Person getPerson() {
        return this.person.buildPerson();
    }
}
