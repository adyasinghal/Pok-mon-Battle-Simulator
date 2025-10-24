package pokemon;

public class Psyduck extends Water {
    private int specialMoveDamage;

    public Psyduck(String name, int health, int specialMoveDamage) {
        super(name, health);
        this.specialMoveDamage = specialMoveDamage;
    }
    public int getSpecialMoveDamage() { return specialMoveDamage; }
}

