package src.domain;

public class NormalEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        return true;
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        return false;
    }

    @Override
    public String getName() {
        return "normal";
    }

}
