package trainer;

import pokemon.Pokemon;

public class Trainer {
    private String name;
    private Pokemon[] pokemons;
    private int badgeCount;
    private int losses;

    public Trainer(String name) {
        this.name = name;
        pokemons = new Pokemon[6];
        badgeCount = 0;
        losses = 0;
    }

    public String getName() { return name; }
    public Pokemon[] getPokemons() { return pokemons; }
    public int getBadgeCount() { return badgeCount; }

    public void addPokemon(Pokemon p, int slot) {
        if (slot >= 0 && slot < pokemons.length) {
            pokemons[slot] = p;
        }
    }

    public void incrementBadgeCount() { badgeCount++; }
    public void decrementBadgeCount() { badgeCount = Math.max(0, badgeCount - 1); losses++; }

    public boolean isEliminated() { return losses >= 3; }
}

