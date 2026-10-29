package net.neoforged.neoforge.mcreator; // Δηλώνει το πακέτο

// Εισαγωγή βιβλιοθηκών για γραφικά, μοντέλα και events
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

// Λέει στο NeoForge ότι αυτό το αρχείο τρέχει μόνο στον Client (στον υπολογιστή του παίκτη)
@EventBusSubscriber(modid = Javagotchi.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    // Ορίζει τη θέση του Layer για το μοντέλο
    public static final ModelLayerLocation PET_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Javagotchi.MODID, "pet"), "main");

    // Συνδέει τα Attributes (ζωή/ταχύτητα) με το Entity
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(Javagotchi.PET.get(), PetEntity.createAttributes().build());
    }

    // Καταχωρεί το σχήμα του σώματος (Model Definition)
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PET_LAYER, PetModel::createBodyLayer);
    }

    // Καταχωρεί τον Renderer (αυτόν που "ζωγραφίζει" το entity)
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Javagotchi.PET.get(), PetRenderer::new);
    }

    // Καταχωρεί το HUD overlay στην οθόνη του παίκτη
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath(Javagotchi.MODID, "pet_hud"), 
                PetHudOverlay.HUD
        );
    }

    // --- RENDERER: Διαχειρίζεται το Texture και το Scale ---
    public static class PetRenderer extends MobRenderer<PetEntity, PetModel<PetEntity>> {
        // Η διαδρομή για το αρχείο εικόνας (.png) του Pet
        private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Javagotchi.MODID, "textures/entity/pet.png");

        public PetRenderer(EntityRendererProvider.Context context) {
            super(context, new PetModel<>(context.bakeLayer(PET_LAYER)), 0.5f); // 0.5f είναι το μέγεθος της σκιάς
        }

        @Override
        public ResourceLocation getTextureLocation(PetEntity entity) {
            return TEXTURE; // Επιστρέφει την εικόνα που θα "ντύσει" το μοντέλο
        }

        @Override
        protected void scale(PetEntity entity, PoseStack poseStack, float partialTickTime) {
            poseStack.scale(1.2F, 1.2F, 1.2F); // Μεγαλώνει το μοντέλο κατά 20%
        }
    }

    // --- MODEL: Κατασκευάζει το 3D σώμα του Pet ---
    public static class PetModel<T extends PetEntity> extends EntityModel<T> {
        private final ModelPart root; // Το κύριο σημείο του μοντέλου
        private final ModelPart head; // Το κεφάλι
        private final ModelPart leg0, leg1, leg2, leg3; // Τα 4 πόδια

        public PetModel(ModelPart root) {
            this.root = root;
            this.head = root.getChild("head");
            this.leg0 = root.getChild("leg0");
            this.leg1 = root.getChild("leg1");
            this.leg2 = root.getChild("leg2");
            this.leg3 = root.getChild("leg3");
        }

        // Σχεδιασμός των κύβων που αποτελούν το σώμα
        public static LayerDefinition createBodyLayer() {
            MeshDefinition mesh = new MeshDefinition();
            PartDefinition part = mesh.getRoot();

            // Δημιουργία Κεφαλιού
            PartDefinition head = part.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-4.0F, -6.0F, -6.0F, 8.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 18.0F, -4.0F));
            
            // Προσθήκη Αυτιών στο Κεφάλι
            head.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(32, 0)
                    .addBox(-3.0F, -10.0F, -3.0F, 2.0F, 4.0F, 1.0F), PartPose.ZERO);
            head.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(32, 0)
                    .addBox(1.0F, -10.0F, -3.0F, 2.0F, 4.0F, 1.0F), PartPose.ZERO);

            // Δημιουργία Σώματος (με περιστροφή 90 μοιρών)
            part.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20)
                    .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 12.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

            // Δημιουργία Ποδιών
            CubeListBuilder legs = CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F);
            part.addOrReplaceChild("leg0", legs, PartPose.offset(-2.0F, 22.0F, 4.0F));
            part.addOrReplaceChild("leg1", legs, PartPose.offset(2.0F, 22.0F, 4.0F));
            part.addOrReplaceChild("leg2", legs, PartPose.offset(-2.0F, 22.0F, -3.0F));
            part.addOrReplaceChild("leg3", legs, PartPose.offset(2.0F, 22.0F, -3.0F));

            return LayerDefinition.create(mesh, 64, 64); // Επιστρέφει το τελικό μοντέλο
        }

        // Διαχειρίζεται την κίνηση (Animation)
        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            // Κίνηση ποδιών μπρος-πίσω βασισμένη στο περπάτημα
            this.leg0.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg1.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            // Κίνηση κεφαλιού ανάλογα με το πού κοιτάζει το Pet
            this.head.xRot = headPitch * ((float)Math.PI / 180F);
            this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        }

        @Override
        public void renderToBuffer(PoseStack pose, VertexConsumer vertex, int light, int overlay, int color) {
            root.render(pose, vertex, light, overlay, color); // Εμφανίζει το μοντέλο στην οθόνη
        }
    }
}