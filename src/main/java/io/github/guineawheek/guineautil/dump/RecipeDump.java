package io.github.guineawheek.guineautil.dump;

import net.minecraft.command.ICommandSender;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import io.github.guineawheek.guineautil.GuineaUtil;
import io.github.guineawheek.guineautil.dump.handlers.*;
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
    List<IRecipeDumper> dumpers;
    public RecipeDump() {
        dumpers = new ArrayList<>();
        dumpers.add(new VanillaCraftingDumper());
        dumpers.add(new VanillaSmeltingDumper());
        dumpers.add(new VanillaBrewingDumper());
        dumpers.add(new GregTechDumper());
        dumpers.add(new GregTechAsslineDumper());
        dumpers.add(new GTPPSpargingDumper());
        dumpers.add(new GTPPDecayableDumper());
        dumpers.add(new ThaumcraftShapedWorktableDumper());
        dumpers.add(new ThaumcraftShapelessWorktableDumper());
        dumpers.add(new ThaumcraftCrucibleDumper());
        dumpers.add(new ThaumcraftInfusionDumper());

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
                        data.write(file, 2, 0);
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
