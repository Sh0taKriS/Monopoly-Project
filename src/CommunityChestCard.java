import java.util.function.Consumer;

public class CommunityChestCard {
    final private String description;
    final private Consumer<Player> effect;

    public CommunityChestCard(String description, Consumer<Player> effect) {
        this.description = description;
        this.effect = effect;
    }

    public void apply(Player player) {
        effect.accept(player);
    }

    public String getDescription() {
        return description;
    }
}