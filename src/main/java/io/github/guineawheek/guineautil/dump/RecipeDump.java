package io.github.guineawheek.guineautil.dump;

import cpw.mods.fml.common.Loader;
import io.github.guineawheek.guineautil.Config;
import net.minecraft.command.ICommandSender;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import io.github.guineawheek.guineautil.GuineaUtil;
import io.github.guineawheek.guineautil.dump.ers.*;
import net.minecraft.util.ChatComponentText;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecipeDump {
    public List<IRecipeDumper> dumpers;
    public RecipeDump() {
        dumpers = new ArrayList<>();
        GuineaUtil.info("load vanilla dumpers");
        dumpers.add(new VanillaCraftingDumper());
        dumpers.add(new VanillaSmeltingDumper());
        dumpers.add(new VanillaBrewingDumper());
        if (Loader.isModLoaded("gregtech")) {
            GuineaUtil.info("load gregtech dumpers");
            dumpers.add(new GregTechDumper());
            dumpers.add(new GregTechAsslineDumper());
        }
        if (Loader.isModLoaded("miscutils")) {
            GuineaUtil.info("load gt++ dumpers");
            dumpers.add(new GTPPSpargingDumper());
            dumpers.add(new GTPPDecayableDumper());
        }
        if (Loader.isModLoaded("thaumcraftneiplugin")) {
            GuineaUtil.info("load tc4 dumpers");
            dumpers.add(new ThaumcraftShapedWorktableDumper());
            dumpers.add(new ThaumcraftShapelessWorktableDumper());
            dumpers.add(new ThaumcraftCrucibleDumper());
            dumpers.add(new ThaumcraftInfusionDumper());
        } else if (Loader.isModLoaded("Thaumcraft")) {
            // strictly speaking this isn't true but that would require some rework
            GuineaUtil.info("in order for the tc4 dumpers to load, the thaumcraft nei plugin must be installed!");
        }
        if (Loader.isModLoaded("Avaritia")) {
            GuineaUtil.info("load avaritia dumpers");
            dumpers.add(new AvaritiaXtremeCraftingDumper());
            dumpers.add(new AvaritiaCompressorDumper());
        }
        if (Loader.isModLoaded("IC2")) {
            GuineaUtil.info("load ic2 dumpers");
            dumpers.add(new IC2CraftingDumper());
        }
        if (Loader.isModLoaded("appliedenergistics2")) {
            GuineaUtil.info("load ae2 dumpers");
            dumpers.add(new AE2CraftingDumper());
        }

    }
    public void dump(ICommandSender ics) {
        /*
        Shaped Crafting,codechicken.nei.recipe.ShapedRecipeHandler,crafting,Minecraft,1xtile.workbench@0
        Shapeless Crafting,codechicken.nei.recipe.ShapelessRecipeHandler,crafting,Minecraft,1xtile.workbench@0
        Fireworks,codechicken.nei.recipe.FireworkRecipeHandler,crafting,Minecraft,1xitem.fireworks@0
        Smelting,codechicken.nei.recipe.FurnaceRecipeHandler,smelting,Minecraft,1xtile.furnace@0
        Brewing,codechicken.nei.recipe.BrewingRecipeHandler,brewing,Minecraft,1xitem.brewingStand@0
        Fuel,codechicken.nei.recipe.FuelRecipeHandler,fuel,Minecraft,Unknown
        Usage Profiling,codechicken.nei.recipe.ProfilerRecipeHandler,null,Unknown,Unknown

         */

        List<ICraftingHandler> handlers = GuiCraftingRecipe.craftinghandlers;
        //List<ICraftingHandler> serialHandlers = GuiCraftingRecipe.serialCraftingHandlers;

        GuineaUtil.info("NEI crafting handlers: ");
        for (ICraftingHandler handler : handlers)  {

            boolean dumped = false;
            for (IRecipeDumper dumper : dumpers) {
                if (dumper.claim(handler)) {
                    GuineaUtil.info("Dumping: " + handler.getHandlerId() + " " + handler.getRecipeName());
                    JSONObject data = dumper.dump(handler);
                    dumped = true;

                    try {
                        Files.createDirectories(Paths.get("./gutil"));
                        Writer file = new FileWriter("./gutil/" + (handler.getClass().getSimpleName() + "_" + handler.getRecipeName()).replaceAll("[^a-zA-Z0-9]", "_") + ".json");
                        data.write(file, Config.indentLevel, 0);
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ics.addChatMessage(new ChatComponentText("successfully dumped " + handler.getHandlerId() + " (" + handler.getRecipeName() + ")"));
                }
            }

            if (!dumped) GuineaUtil.info("Unclaimed: " + handler.getHandlerId() + " " + handler.getRecipeName());

        }


    }
}
