package Mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import proxies.CommonProxy;

import commands.AddNightVisionCommand;
import commands.AddTwelveHoursCommand;
import commands.AddUnderWaterBreathingCommand;
import commands.AxeCommand;
import commands.BedCommand;
import commands.BedrockCommand;
import commands.CactiiCommand;
import commands.CarrotsCommand;
import commands.ClearActivePotionsCommand;
import commands.CreeperRingCommand;
import commands.DestroyVillageDoorsCommand;
import commands.DiamondsCommand;
import commands.DirtCommand;
import commands.EnableFlyingCommand;
import commands.FencesCommand;
import commands.GetNumberOfVillagesCommand;
import commands.GhastSpawnerEggsCommand;
import commands.GiantZombieCommand;
import commands.GoldCommand;
import commands.GravelCommand;
import commands.HealCommand;
import commands.NoMobsCommand;
import commands.NoRainCommand;
import commands.NoVillagersCommand;
import commands.PickAxeCommand;
import commands.PlanksCommand;
import commands.RedstoneCommand;
import commands.StepForwardCommand;
import commands.TeleportToHighPointCommand;
import commands.TeleportToRoomBeneathCommand;
import commands.TorchesCommand;
import commands.VillagerSpawnerEggsCommand;

@Mod(modid = ExtraCommandsMod.modid, version = ExtraCommandsMod.version, name = "ExtraCommandsMod")
public class ExtraCommandsMod
{
    @SidedProxy(clientSide = "proxies.CommonProxy", serverSide = "proxies.CommonProxy")
    public static CommonProxy proxy;

    public static final String modid = "extracommandsmod";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new AddNightVisionCommand());
        event.registerServerCommand(new AddTwelveHoursCommand());
        event.registerServerCommand(new AddUnderWaterBreathingCommand());
        event.registerServerCommand(new AxeCommand());
        event.registerServerCommand(new BedCommand());
        event.registerServerCommand(new BedrockCommand());
        event.registerServerCommand(new CactiiCommand());
        event.registerServerCommand(new CarrotsCommand());
        event.registerServerCommand(new CreeperRingCommand());
        event.registerServerCommand(new ClearActivePotionsCommand());
        event.registerServerCommand(new DestroyVillageDoorsCommand());
        event.registerServerCommand(new DiamondsCommand());
        event.registerServerCommand(new DirtCommand());
        event.registerServerCommand(new EnableFlyingCommand());
        event.registerServerCommand(new FencesCommand());
        event.registerServerCommand(new GetNumberOfVillagesCommand());
        event.registerServerCommand(new GhastSpawnerEggsCommand());
        event.registerServerCommand(new GiantZombieCommand());
        event.registerServerCommand(new GoldCommand());
        event.registerServerCommand(new GravelCommand());
        event.registerServerCommand(new HealCommand());
        event.registerServerCommand(new NoMobsCommand());
        event.registerServerCommand(new NoRainCommand());
        event.registerServerCommand(new NoVillagersCommand());
        event.registerServerCommand(new PickAxeCommand());
        event.registerServerCommand(new PlanksCommand());
        event.registerServerCommand(new RedstoneCommand());
        event.registerServerCommand(new StepForwardCommand());
        event.registerServerCommand(new TeleportToHighPointCommand());
        event.registerServerCommand(new TeleportToRoomBeneathCommand());
        event.registerServerCommand(new TorchesCommand());
        event.registerServerCommand(new VillagerSpawnerEggsCommand());
    }    
}
