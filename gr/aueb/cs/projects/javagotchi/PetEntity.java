package net.neoforged.neoforge.mcreator; // Το πακέτο του mod

// Εισαγωγή βιβλιοθηκών για AI, κίνηση, παίκτες και δεδομένα
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.Nullable;

public class PetEntity extends Animal {
    // Ορισμός μεταβλητών που συγχρονίζονται μεταξύ server και παίκτη (Hunger, Happiness, Energy)
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(PetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HAPPINESS = SynchedEntityData.defineId(PetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENERGY = SynchedEntityData.defineId(PetEntity.class, EntityDataSerializers.INT);

    // Constructor: Αρχικοποίηση του Pet στον κόσμο
    protected PetEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    // Ορισμός αρχικών τιμών για τα στατιστικά (Hunger, Happiness, Energy)
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HUNGER, 10); // Ξεκινάει με 10 Hunger
        builder.define(HAPPINESS, 10); // Ξεκινάει με 10 Happiness
        builder.define(ENERGY, 10); // Ξεκινάει με 10 Energy
    }

    // AI Goals: Τι θα κάνει το Pet μόνο του
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Να επιπλέει στο νερό για να μην πνιγεί
        // Ακολουθεί τον παίκτη αν αυτός κρατάει μήλο
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(Items.APPLE), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D)); // Να περπατάει τυχαία
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F)); // Να κοιτάζει τον παίκτη
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this)); // Να κοιτάζει γύρω-γύρω
    }

    //Attributes: Ορισμός ζωής και ταχύτητας
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D) // 20 πόντοι ζωής (10 καρδιές)
                .add(Attributes.MOVEMENT_SPEED, 0.25D); // Ταχύτητα κίνησης
    }

    // interaction: Τι γίνεται όταν ο παίκτης κάνει δεξί κλικ στο Pet
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand); // Παίρνει το αντικείμενο που κρατάει ο παίκτης

        // Αν κρατάει Μήλο
        if (itemstack.is(Items.APPLE)) {
            if (getHunger() < 10 || this.getHealth() < this.getMaxHealth()) {
                this.consumeItem(player, itemstack); // Τρώει το μήλο
                this.entityData.set(HUNGER, Math.min(10, getHunger() + 3)); // Αυξάνει το Hunger κατά 3
                this.heal(4.0F); // Θεραπεύει 2 καρδιές
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } 
        // Αν κρατάει Καρότο
        else if (itemstack.is(Items.CARROT)) {
            if (getEnergy() < 10) {
                this.consumeItem(player, itemstack); // Τρώει το καρότο
                this.entityData.set(ENERGY, Math.min(10, getEnergy() + 4)); // Αυξάνει το Energy κατά 4
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } 
        // Αν κρατάει Stick (Παιχνίδι)
        else if (itemstack.is(Items.STICK)) {
            if (getEnergy() > 0) {
                this.entityData.set(HAPPINESS, Math.min(10, getHappiness() + 2)); // Αυξάνει Happiness
                this.entityData.set(ENERGY, Math.max(0, getEnergy() - 1)); // Μειώνει Energy (κουράζεται)
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        
        return super.mobInteract(player, hand);
    }

    // Μέθοδος που αφαιρεί το αντικείμενο από τον παίκτη (εκτός αν είναι σε Creative)
    private void consumeItem(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) stack.shrink(1);
    }

    // aiStep: Εκτελείται σε κάθε tick (κάθε στιγμή) του παιχνιδιού
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive()) { // Μόνο αν τρέχει στον server και είναι ζωντανό
            
            // Μείωση Hunger κάθε 60 δευτερόλεπτα
            if (this.tickCount % 1200 == 0) {
                this.entityData.set(HUNGER, Math.max(0, getHunger() - 1));
            }

            // Μείωση Happiness κάθε 40 δευτερόλεπτα
            if (this.tickCount % 800 == 0) {
                this.entityData.set(HAPPINESS, Math.max(0, getHappiness() - 1));
            }

            // Θάνατος αν Hunger ή Happiness φτάσουν στο 0
            if (getHunger() <= 0 || getHappiness() <= 0) {
                this.discard(); // Το Pet εξαφανίζεται
            }

            // Έλεγχος Energy: Αν είναι 0, το Pet σταματά να κινείται
            if (getEnergy() <= 0) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
            } else {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
            }
        }
    }

    // Getters για να παίρνουμε τις τιμές των στατιστικών
    public int getHunger() { return this.entityData.get(HUNGER); }
    public int getHappiness() { return this.entityData.get(HAPPINESS); }
    public int getEnergy() { return this.entityData.get(ENERGY); }

    @Override
    public boolean isFood(ItemStack stack) { 
        return stack.is(Items.APPLE) || stack.is(Items.CARROT); 
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) { 
        return null; // Το Pet δεν γεννάει παιδιά
    }
}