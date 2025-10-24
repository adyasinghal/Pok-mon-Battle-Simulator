package oak;

import java.util.Scanner;
import trainer.Trainer;
import pokemon.*;

public class OakJournal {
    private Trainer[] trainers;

    public OakJournal() {
        trainers = new Trainer[10];
    }

    // Automatically add trainer to first free slot, print assigned position
    public boolean addTrainer(Trainer t) {
        for (int i = 0; i < trainers.length; i++) {
            if (trainers[i] == null) {
                trainers[i] = t;
                System.out.println("Trainer " + t.getName() + " added at index " + i);
                return true;
            }
        }
        System.out.println("No empty slot available for trainer.");
        return false;
    }

    public Trainer getTrainer(int index) {
        if (index >= 0 && index < trainers.length) {
            return trainers[index];
        }
        return null;
    }

    public void runShowdown(Trainer t1, Trainer t2) {
        System.out.printf("Showdown: %s vs. %s%n", t1.getName(), t2.getName());
        int t1Wins = 0, t2Wins = 0;

        for (int i = 0; i < 6; i++) {
            Pokemon p1 = t1.getPokemons()[i];
            Pokemon p2 = t2.getPokemons()[i];

            if (p1 == null && p2 != null) {
                System.out.printf("Round %d: %s has no Pokemon, %s wins!%n", i + 1, t1.getName(), t2.getName());
                t2Wins++;
                continue;
            } else if (p2 == null && p1 != null) {
                System.out.printf("Round %d: %s has no Pokemon, %s wins!%n", i + 1, t2.getName(), t1.getName());
                t1Wins++;
                continue;
            } else if (p1 == null && p2 == null) {
                System.out.printf("Round %d: Both trainers missing Pokemon.%n", i + 1);
                // Neither wins, skip this round or count as draw
                continue;
            }


            String winner = battle(p1, t1, p2, t2);

            if (winner.equals(t1.getName())) {
                t1Wins++;
            } else if (winner.equals(t2.getName())) {
                t2Wins++;
            }

            System.out.printf("Round %d (%s vs. %s): ", i + 1, p1.getName(), p2.getName());

            if (winner.equals("draw")) {
                System.out.println("It's a draw!");
            } else {
                System.out.printf("Winner is %s!%n", winner);
            }
        }


        if (t1Wins > t2Wins) {
            System.out.printf("Final Winner: %s%n", t1.getName());
            t1.incrementBadgeCount();
            t2.decrementBadgeCount();
        } else if (t2Wins > t1Wins) {
            System.out.printf("Final Winner: %s%n", t2.getName());
            t2.incrementBadgeCount();
            t1.decrementBadgeCount();
        } else {
            System.out.println("Final Result: It's a draw!");
        }
    }

    private String battle(Pokemon p1, Trainer t1, Pokemon p2, Trainer t2) {

        String type1 = p1.getType();
        String type2 = p2.getType();

        // Type advantages
        if (type1.equals("Fire") && type2.equals("Grass")) {
            return t1.getName();
        } else if (type1.equals("Grass") && type2.equals("Water")) {
            return t1.getName();
        } else if (type1.equals("Water") && type2.equals("Fire")) {
            return t1.getName();
        } else if (type2.equals("Fire") && type1.equals("Grass")) {
            return t2.getName();
        } else if (type2.equals("Grass") && type1.equals("Water")) {
            return t2.getName();
        } else if (type2.equals("Water") && type1.equals("Fire")) {
            return t2.getName();
        }

        // No type advantage - compare health
        if (p1.getHealth() > p2.getHealth()) {
            return t1.getName();
        } else if (p2.getHealth() > p1.getHealth()) {
            return t2.getName();
        } else {
            return "draw";
        }
    }


    public static void main(String[] args) {
        OakJournal oakJournal = new OakJournal();
        Scanner scanner = new Scanner(System.in);

        // Hardcoded Cases
        Trainer demoTrainer1 = new Trainer("Trainer1");
        Trainer demoTrainer2 = new Trainer("Trainer2");

        demoTrainer1.addPokemon(new pokemon.Charmander("Charmander", 50, 20), 0);
        demoTrainer1.addPokemon(new pokemon.Vulpix("Vulpix", 46, 15), 1);
        demoTrainer1.addPokemon(new pokemon.Squirtle("Squirtle1", 54, 10), 2);
        demoTrainer1.addPokemon(new pokemon.Psyduck("Psyduck1", 39, 12), 3);
        demoTrainer1.addPokemon(new pokemon.Bulbasaur("Bulbasaur1", 59, 14), 4);
        demoTrainer1.addPokemon(new pokemon.Pikachu("Pikachu1", 51, 25), 5);

        demoTrainer2.addPokemon(new pokemon.Vulpix("Vulpix", 48, 16), 0);
        demoTrainer2.addPokemon(new pokemon.Chikorita("Chikorita", 52, 17), 1);
        demoTrainer2.addPokemon(new pokemon.Squirtle("Squirtle", 53, 11), 2);
        demoTrainer2.addPokemon(new pokemon.Psyduck("Psyduck", 44, 10), 3);
        demoTrainer2.addPokemon(new pokemon.Bulbasaur("Bulbasaur", 60, 13), 4);
        demoTrainer2.addPokemon(new pokemon.Pikachu("Pikachu", 47, 24), 5);

        oakJournal.addTrainer(demoTrainer1);
        oakJournal.addTrainer(demoTrainer2);

        boolean running = true;

        while (running) {
            System.out.println("\nSelect operation (1-Add Trainer, 2-Add Pokemon, 3-Run Battle, 4-Exit):");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Trainer name: ");
                    String tname = scanner.nextLine();
                    Trainer trainer = new Trainer(tname);
                    boolean added = oakJournal.addTrainer(trainer);
                    if (!added) {
                        System.out.println("Trainer list full.");
                    }
                    break;

                case 2:
                    System.out.println("Current Trainers:");
                    for (int i = 0; i < oakJournal.trainers.length; i++) {
                        Trainer temp = oakJournal.getTrainer(i);
                        if (temp != null) {
                            System.out.println("Index " + i + ": " + temp.getName());
                        }
                    }
                    System.out.print("Enter Trainer index (0-9): ");
                    int tIndex = scanner.nextInt();
                    scanner.nextLine();
                    Trainer tr = oakJournal.getTrainer(tIndex);
                    if (tr == null) {
                        System.out.println("Invalid trainer index.");
                        break;
                    }
                    System.out.print("Enter Pokemon type (Charmander, Vulpix, Squirtle, Psyduck, Bulbasaur, Chikorita, Pikachu): ");
                    String ptype = scanner.nextLine();
                    System.out.print("Enter Pokemon name: ");
                    String pname = scanner.nextLine();
                    System.out.print("Enter Pokemon health (int): ");
                    int phealth = scanner.nextInt();
                    System.out.print("Enter Pokemon special move damage (int): ");
                    int pdmg = scanner.nextInt();
                    scanner.nextLine();

                    Pokemon poke = null;
                    switch(ptype) {
                        case "Charmander":
                            poke = new Charmander(pname, phealth, pdmg);
                            break;
                        case "Vulpix":
                            poke = new Vulpix(pname, phealth, pdmg);
                            break;
                        case "Squirtle":
                            poke = new Squirtle(pname, phealth, pdmg);
                            break;
                        case "Psyduck":
                            poke = new Psyduck(pname, phealth, pdmg);
                            break;
                        case "Bulbasaur":
                            poke = new Bulbasaur(pname, phealth, pdmg);
                            break;
                        case "Chikorita":
                            poke = new Chikorita(pname, phealth, pdmg);
                            break;
                        case "Pikachu":
                            poke = new Pikachu(pname, phealth, pdmg);
                            break;
                        default:
                            System.out.println("Unknown Pokemon type.");
                            break;
                    }
                    if (poke != null) {
                        System.out.print("Enter Pokemon slot (0-5): ");
                        int pslot = scanner.nextInt();
                        scanner.nextLine();
                        tr.addPokemon(poke, pslot);
                        System.out.println("Pokemon added to trainer " + tr.getName() + " in slot " + pslot);
                    }
                    break;

                case 3:
                    System.out.println("Current Trainers:");
                    for (int i = 0; i < oakJournal.trainers.length; i++) {
                        Trainer temp = oakJournal.getTrainer(i);
                        if (temp != null) {
                            System.out.println("Index " + i + ": " + temp.getName());
                        }
                    }
                    System.out.print("Enter first Trainer index (0-9): ");
                    int b1 = scanner.nextInt();
                    System.out.print("Enter second Trainer index (0-9): ");
                    int b2 = scanner.nextInt();
                    scanner.nextLine();
                    Trainer bt1 = oakJournal.getTrainer(b1);
                    Trainer bt2 = oakJournal.getTrainer(b2);
                    if (bt1 == null || bt2 == null) {
                        System.out.println("One or both trainers invalid.");
                    } else {
                        oakJournal.runShowdown(bt1, bt2);
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}
