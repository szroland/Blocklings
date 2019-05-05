package com.blocklings.util.helpers;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.main.BlocklingsConfig;
import com.blocklings.render.RenderBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.parallel.mutable.ParArray;

import java.util.ArrayList;
import java.util.List;

public class EntityHelper
{
    public static final float BASE_SCALE = 0.75f;
    public static final float BASE_SCALE_FOR_HITBOX = BASE_SCALE * 0.95f;

    public static final int SKILL_POINT_INTERVAL = 5;

    public static final float SWING_SPEED_COEF = 0.75f;

    public static double XP_MULTIPLIER = 1.0;

    public static List<Integer> biomes = new ArrayList<>();
    static {
        biomes.add(Biome.getIdForBiome(Biomes.PLAINS));
        biomes.add(Biome.getIdForBiome(Biomes.FOREST));
        biomes.add(Biome.getIdForBiome(Biomes.BIRCH_FOREST));
        biomes.add(Biome.getIdForBiome(Biomes.FOREST_HILLS));
        biomes.add(Biome.getIdForBiome(Biomes.BIRCH_FOREST_HILLS));
        biomes.add(Biome.getIdForBiome(Biomes.REDWOOD_TAIGA));
        biomes.add(Biome.getIdForBiome(Biomes.REDWOOD_TAIGA_HILLS));
        biomes.add(Biome.getIdForBiome(Biomes.ROOFED_FOREST));
        biomes.add(Biome.getIdForBiome(Biomes.TAIGA));
        biomes.add(Biome.getIdForBiome(Biomes.TAIGA_HILLS));
        biomes.add(Biome.getIdForBiome(Biomes.EXTREME_HILLS));
        biomes.add(Biome.getIdForBiome(Biomes.EXTREME_HILLS_EDGE));
        biomes.add(Biome.getIdForBiome(Biomes.EXTREME_HILLS_WITH_TREES));
        biomes.add(Biome.getIdForBiome(Biomes.JUNGLE));
        biomes.add(Biome.getIdForBiome(Biomes.JUNGLE_EDGE));
        biomes.add(Biome.getIdForBiome(Biomes.JUNGLE_HILLS));
    }
    public static void registerEntities()
    {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocationBlocklings("entity_blockling"), EntityBlockling.class, "blockling", id++, Blocklings.instance, 64, 3, true);
        for (int biome : biomes)
        {
            EntityRegistry.addSpawn(EntityBlockling.class, BlocklingsConfig.SPAWN_RATE, 1, 2, EnumCreatureType.CREATURE, Biome.getBiome(biome, Biomes.PLAINS));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBlockling.class, RenderBlockling.FACTORY);
    }

    public static int getXpUntilNextLevel(int level)
    {
        return (int) (Math.exp(level / 25.0) * 40) - 30;
    }

    public static List<Class<?>> entityWhitelistHunt = new ArrayList<>();
    static
    {
        entityWhitelistHunt.add(EntityChicken.class);
        entityWhitelistHunt.add(EntityCow.class);
        entityWhitelistHunt.add(EntityGiantZombie.class);
        entityWhitelistHunt.add(EntityPig.class);
        entityWhitelistHunt.add(EntitySheep.class);
        entityWhitelistHunt.add(EntityEndermite.class);
        entityWhitelistHunt.add(EntityEvoker.class);
        entityWhitelistHunt.add(EntityHusk.class);
        entityWhitelistHunt.add(EntityIllusionIllager.class);
        entityWhitelistHunt.add(EntityMagmaCube.class);
        entityWhitelistHunt.add(EntitySlime.class);
        entityWhitelistHunt.add(EntityStray.class);
        entityWhitelistHunt.add(EntityWitch.class);
        entityWhitelistHunt.add(EntityWitherSkeleton.class);
        entityWhitelistHunt.add(EntityZombie.class);
        entityWhitelistHunt.add(EntityCaveSpider.class);
        entityWhitelistHunt.add(EntityEnderman.class);
        entityWhitelistHunt.add(EntitySpider.class);
    }
    public static boolean isEntityHuntable(EntityLivingBase entitylivingbase)
    {
        for (Class<?> entityType : entityWhitelistHunt)
        {
            if (entityType.isInstance(entitylivingbase))
            {
                return true;
            }
        }

        return false;
    }

    public static List<Class<?>> entityBlacklistAttack = new ArrayList<>();
    static
    {
        entityBlacklistAttack.add(EntityPlayer.class);
        entityBlacklistAttack.add(EntityFlying.class);
        entityBlacklistAttack.add(EntityVillager.class);
        entityBlacklistAttack.add(EntityCreeper.class);
    }
    public static boolean isEntityAttackable(EntityLivingBase entitylivingbase)
    {
        for (Class<?> entityType : entityBlacklistAttack)
        {
            if (entityType.isInstance(entitylivingbase))
            {
                return false;
            }
        }

        return true;
    }
}
