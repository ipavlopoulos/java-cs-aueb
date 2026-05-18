package net.neoforged.neoforge.mcreator; // Δηλώνει το πακέτο

// Εισαγωγή βιβλιοθηκών για το UI και την ανίχνευση οντοτήτων
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.phys.AABB;
import java.util.List;
import java.util.Comparator;

public class PetHudOverlay {
    // Ορισμός του Layer που θα σχεδιαστεί στην οθόνη
    public static final LayeredDraw.Layer HUD = (guiGraphics, deltaTracker) -> {
        Minecraft mc = Minecraft.getInstance(); // Παίρνει το instance του παιχνιδιού
        if (mc.player == null || mc.level == null) return; // Αν ο παίκτης δεν είναι σε κόσμο, σταματάει

        // Ανίχνευση Pet σε ακτίνα 3 blocks γύρω από τον παίκτη
        double range = 3.0D;
        AABB area = mc.player.getBoundingBox().inflate(range);
        List<PetEntity> pets = mc.level.getEntitiesOfClass(PetEntity.class, area);

        if (!pets.isEmpty()) {
            // Επιλέγει το Pet που είναι πιο κοντά στον παίκτη
            PetEntity pet = pets.stream()
                .min(Comparator.comparingDouble(p -> p.distanceToSqr(mc.player)))
                .orElse(pets.get(0));

            // Υπολογισμός θέσης (Κέντρο οθόνης οριζόντια, 10 pixels από πάνω)
            int x = mc.getWindow().getGuiScaledWidth() / 2;
            int y = 10;

            // Σχεδίαση κειμένων στην οθόνη με χρώματα (Hex Codes)
            guiGraphics.drawCenteredString(mc.font, "--- JAVAGOTCHI STATUS ---", x, y, 0x00FF00); // Πράσινο
            guiGraphics.drawString(mc.font, "Hunger: " + pet.getHunger() + "/10", x - 45, y + 15, 0xFFAA00); // Πορτοκαλί
            guiGraphics.drawString(mc.font, "Happiness: " + pet.getHappiness() + "/10", x - 45, y + 25, 0xFF55FF); // Ροζ
            guiGraphics.drawString(mc.font, "Energy: " + pet.getEnergy() + "/10", x - 45, y + 35, 0x55FFFF); // Γαλάζιο
            guiGraphics.drawString(mc.font, "Health: " + (int)pet.getHealth() + "/" + (int)pet.getMaxHealth(), x - 45, y + 45, 0xFF0000); // Κόκκινο
        }
    };
}