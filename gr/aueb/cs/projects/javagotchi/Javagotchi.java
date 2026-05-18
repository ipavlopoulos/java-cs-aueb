package net.neoforged.neoforge.mcreator; // Δηλώνει το πακέτο στο οποίο ανήκει το αρχείο

// Εισαγωγή απαραίτητων βιβλιοθηκών του Minecraft και του NeoForge
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

@Mod(Javagotchi.MODID) // Λέει στο NeoForge ότι αυτή η κλάση είναι το κύριο Mod
public class Javagotchi {
    public static final String MODID = "javagotchi"; // Το μοναδικό αναγνωριστικό του Mod σου

    // Δημιουργία λιστών (Registers) για την καταγραφή οντοτήτων, αντικειμένων και tabs
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Καταγραφή του Pet Entity στο παιχνίδι
    public static final DeferredHolder<EntityType<?>, EntityType<PetEntity>> PET = ENTITIES.register("pet",
            () -> EntityType.Builder.of(PetEntity::new, MobCategory.CREATURE) // Ορίζεται ως πλάσμα (Creature)
                    .sized(0.6f, 1.0f) // Το μέγεθος του (πλάτος, ύψος)
                    .build("pet")); // Κλείσιμο του builder με το όνομα "pet"

    // Δημιουργία του Spawn Egg (Αυγό) για να εμφανίζουμε το Pet
    public static final DeferredHolder<Item, Item> PET_SPAWN_EGG = ITEMS.register("pet_spawn_egg",
            () -> new DeferredSpawnEggItem(PET, 0xFFFFFF, 0x00FF00, new Item.Properties())); // Χρώματα: Λευκό και Πράσινο

    // Δημιουργία δικής μας καρτέλας (Tab) στο Creative Menu
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register("tab",
            () -> CreativeModeTab.builder()
                    .title(net.minecraft.network.chat.Component.translatable("item_group." + MODID)) // Τίτλος καρτέλας
                    .icon(() -> new ItemStack(PET_SPAWN_EGG.get())) // Εικονίδιο καρτέλας το αυγό
                    .displayItems((params, output) -> {
                        output.accept(PET_SPAWN_EGG.get()); // Προσθήκη του αυγού μέσα στην καρτέλα
                    })
                    .build()); // Ολοκλήρωση κατασκευής του Tab

    // Ο Constructor του Mod που συνδέει τους Registers με το Bus του παιχνιδιού
    public Javagotchi(IEventBus modEventBus) {
        ENTITIES.register(modEventBus); // Ενεργοποίηση καταγραφής οντοτήτων
        ITEMS.register(modEventBus); // Ενεργοποίηση καταγραφής αντικειμένων
        TABS.register(modEventBus); // Ενεργοποίηση καταγραφής tabs
    }
}