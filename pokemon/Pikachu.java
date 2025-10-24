package pokemon;

public class Pikachu extends Pokemon {
    private int specialMoveDamage;

    public Pikachu(String name, int health, int specialMoveDamage) {
        super(name, health);
        this.specialMoveDamage = specialMoveDamage;
    }
    public int getSpecialMoveDamage() { return specialMoveDamage; }
    @Override
    public String getType() { return "Electric"; }
}

