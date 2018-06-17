package gregtech.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.FluidMaterial.MatFlags;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class MetaFluids {

    private static final Set<ResourceLocation> fluidSprites = new HashSet<>();

    public static final ResourceLocation AUTO_GENERATED_FLUID_TEXTURE = new ResourceLocation(
        GTValues.MODID, "blocks/fluids/fluid.molten.autogenerated");

    public static final Fluid DISTILLED_WATER = new Fluid("distilled_water",
        new ResourceLocation("blocks/water_still"),
        new ResourceLocation("blocks/water_flow"))
        .setBlock(Blocks.WATER);

    private static final BiMap<ResourceLocation, Fluid> potionFluidMap = HashBiMap.create();

    public static Fluid getFluidForPotion(Potion potion) {
        return potionFluidMap.get(potion.getRegistryName());
    }

    public static Potion getPotionForFluid(Fluid potionFluid) {
        ResourceLocation registryName = potionFluidMap.inverse().get(potionFluid);
        return registryName == null ? null : ForgeRegistries.POTIONS.getValue(registryName);
    }

    public enum FluidType {
        LIQUID,
        GAS,
        PLASMA
    }
    
    public static void registerSprites(TextureMap textureMap) {
        for(ResourceLocation spriteLocation : fluidSprites) {
            textureMap.registerSprite(spriteLocation);
        }
    }

    public static void init() {
        Materials.Water.setMaterialFluid(FluidRegistry.WATER);
        Materials.Lava.setMaterialFluid(FluidRegistry.LAVA);

        FluidRegistry.registerFluid(DISTILLED_WATER);
        Materials.DistilledWater.setMaterialFluid(DISTILLED_WATER);
        fluidSprites.add(AUTO_GENERATED_FLUID_TEXTURE);

        //TODO TWEAK VALUES
        registerFluid(Materials.Air, FluidType.GAS, 295, true);
        registerFluid(Materials.Oxygen, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Hydrogen, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Deuterium, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Tritium, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Helium, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Argon, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Radon, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Fluorine, FluidType.LIQUID, 53, true);
        registerFluid(Materials.TitaniumTetrachloride, FluidType.LIQUID, 2200, true);
        registerFluid(Materials.Helium3, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Methane, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Nitrogen, FluidType.LIQUID, 295, true);
        registerFluid(Materials.NitrogenDioxide, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Steam, FluidType.GAS, 295, true);

        registerFluid(Materials.OilHeavy, FluidType.LIQUID, 295, true);
        registerFluid(Materials.OilMedium, FluidType.LIQUID, 295, true);
        registerFluid(Materials.OilLight, FluidType.LIQUID, 295, true);

        registerFluid(Materials.HydrogenSulfide, FluidType.GAS, 295, true);
        registerFluid(Materials.SulfuricGas, FluidType.GAS, 295, true);
        registerFluid(Materials.Gas, FluidType.GAS, 295, true);

        registerFluid(Materials.SulfuricNaphtha, FluidType.LIQUID, 295, true);
        registerFluid(Materials.SulfuricLightFuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.SulfuricHeavyFuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Naphtha, FluidType.LIQUID, 295, true);
        registerFluid(Materials.LightFuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.HeavyFuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.LPG, FluidType.LIQUID, 295, true);
        registerFluid(Materials.CrackedLightFuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.CrackedHeavyFuel, FluidType.LIQUID, 295, true);

        registerFluid(Materials.UUAmplifier, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Chlorine, FluidType.GAS, 295, true);
        registerFluid(Materials.Mercury, FluidType.LIQUID, 295, true);
        registerFluid(Materials.NitroFuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.SodiumPersulfate, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Glyceryl, FluidType.LIQUID, 295, true);

        registerFluid(Materials.Lubricant, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Creosote, FluidType.LIQUID, 295, true);
        registerFluid(Materials.SeedOil, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Oil, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Fuel, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Honey, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Biomass, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Ethanol, FluidType.LIQUID, 295, true);
        registerFluid(Materials.SulfuricAcid, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Milk, FluidType.LIQUID, 290, true);
        registerFluid(Materials.McGuffium239, FluidType.LIQUID, 295, true);
        registerFluid(Materials.Glue, FluidType.LIQUID, 295, true);

        for (Material material : Material.MATERIAL_REGISTRY) {
            if (!(material instanceof FluidMaterial)) continue;
            FluidMaterial fluidMaterial = (FluidMaterial) material;
            if (fluidMaterial.shouldGenerateFluid() &&
                fluidMaterial.getMaterialFluid() == null) {
                int temperature = fluidMaterial.getFluidTemperature();
                FluidType fluidType = fluidMaterial.hasFlag(MatFlags.STATE_GAS) ? FluidType.GAS : FluidType.LIQUID;
                registerFluid(fluidMaterial, fluidType, temperature, false);
            }
            if (fluidMaterial.shouldGeneratePlasma() &&
                fluidMaterial.getMaterialPlasma() == null) {
                registerFluid(fluidMaterial, FluidType.PLASMA, 30000, false);
            }
        }
    }

    public static void initPotionFluids() {
        for(ResourceLocation registryName : ForgeRegistries.POTIONS.getKeys()) {
            Potion potion = ForgeRegistries.POTIONS.getValue(registryName);
            String fluidName = String.format("potion.%s.%s", registryName.getResourceDomain(), registryName.getResourcePath());
            Fluid potionFluid = new Fluid(fluidName, AUTO_GENERATED_FLUID_TEXTURE, AUTO_GENERATED_FLUID_TEXTURE);
            potionFluid.setColor(potion.getLiquidColor());
            potionFluid.setUnlocalizedName(potion.getName());
            BlockFluidBase fluidBlock = new BlockFluidFinite(potionFluid, net.minecraft.block.material.Material.WATER);
            fluidBlock.setRegistryName("fluid." + fluidName);
            MetaBlocks.FLUID_BLOCKS.add(fluidBlock);
        }
    }

    public static Fluid registerFluid(FluidMaterial material, FluidType type, int temp, boolean setCustomTexture) {
        String materialName = material.toString();
        String typeName = type.name().toLowerCase();
        ResourceLocation textureLocation;
        if(setCustomTexture) {
            textureLocation = new ResourceLocation(GTValues.MODID, "blocks/fluids/fluid." + materialName);
            fluidSprites.add(textureLocation);
        } else {
            textureLocation = AUTO_GENERATED_FLUID_TEXTURE;
        }
        Fluid fluid = new Fluid(typeName + "." + materialName, textureLocation, textureLocation) {
            @Override
            public String getUnlocalizedName() {
                return material.getUnlocalizedName();
            }
        };
        fluid.setTemperature(temp);
        fluid.setColor(GTUtility.convertRGBtoOpaqueRGBA_MC(material.materialRGB));
        setFluidProperties(fluid, type, material);
        FluidRegistry.registerFluid(fluid);

        if(material.hasFlag(MatFlags.GENERATE_FLUID_BLOCK)) {
            BlockFluidBase fluidBlock = new BlockFluidClassic(fluid, net.minecraft.block.material.Material.WATER);
            fluidBlock.setRegistryName("fluid." + materialName);
            MetaBlocks.FLUID_BLOCKS.add(fluidBlock);
        }
        return fluid;
    }

    private static void setFluidProperties(Fluid fluid, FluidType type, FluidMaterial material) {
        switch(type) {
            case LIQUID:
                fluid.setGaseous(false);
                fluid.setViscosity(1000);
                material.setMaterialFluid(fluid);
                break;
            case GAS:
                fluid.setGaseous(true);
                fluid.setDensity(-100);
                fluid.setViscosity(200);
                material.setMaterialFluid(fluid);
                break;
            case PLASMA:
                fluid.setGaseous(true);
                fluid.setDensity(55536);
                fluid.setViscosity(10);
                fluid.setLuminosity(15);
                material.setMaterialPlasma(fluid);
                break;
        }
    }

}
