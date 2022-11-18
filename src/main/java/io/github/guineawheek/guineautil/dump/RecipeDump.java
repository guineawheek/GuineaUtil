package io.github.guineawheek.guineautil.dump;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Loader;
import io.github.guineawheek.guineautil.Config;
import io.github.guineawheek.guineautil.GuineaUtil;
import io.github.guineawheek.guineautil.dump.ers.*;
import io.github.guineawheek.guineautil.dump.ers.forestry.*;
import io.github.guineawheek.guineautil.dump.ers.template.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import org.json.JSONObject;

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
        if (Loader.isModLoaded("Forestry")) {
            GuineaUtil.info("load forestry dumpers");
            dumpers.add(new CarpenterDumper());
            dumpers.add(new CentrifugeDumper());
            dumpers.add(new FabricatorDumper());
            dumpers.add(new FermenterDumper());
            dumpers.add(new MoistenerDumper());
            dumpers.add(new SqueezerDumper());
            dumpers.add(new StillDumper());
        }

        if (Config.dumpAllTemplates) {
            GuineaUtil.info("load fallback template dumper");
            dumpers.add(new TemplateRecipeHandlerDumper() {
                @Override
                public boolean claim(ICraftingHandler handler) {
                    return handler instanceof TemplateRecipeHandler;
                }
            });
        }
    }

    public void dump(ICommandSender ics) {

        List<ICraftingHandler> handlers = GuiCraftingRecipe.craftinghandlers;
        // List<ICraftingHandler> serialHandlers = GuiCraftingRecipe.serialCraftingHandlers;

        GuineaUtil.info("NEI crafting handlers: ");
        for (ICraftingHandler handler : handlers) {

            boolean dumped = false;
            for (IRecipeDumper dumper : dumpers) {
                if (dumper.claim(handler)) {
                    GuineaUtil.info("Dumping: " + handler.getHandlerId() + " " + handler.getRecipeName());
                    JSONObject data;
                    try {
                        data = dumper.dump(handler);
                        dumped = true;
                    } catch (Exception e) {
                        GuineaUtil.error(" === Error trying to dump recipes for " + handler.getHandlerId() + " ("
                                + handler.getRecipeName() + ")");
                        GuineaUtil.error(getStackTrace(e));
                        break;
                    }

                    try {
                        Files.createDirectories(Paths.get("./gutil"));
                        Writer file = new FileWriter("./gutil/"
                                + (handler.getClass().getSimpleName() + "_" + handler.getRecipeName())
                                        .replaceAll("[^a-zA-Z0-9]", "_")
                                + ".json");
                        data.write(file, Config.indentLevel, 0);
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ics.addChatMessage(new ChatComponentText(
                            "successfully dumped " + handler.getHandlerId() + " (" + handler.getRecipeName() + ")"));
                    break;
                }
            }

            if (!dumped) GuineaUtil.info("Unclaimed: " + handler.getHandlerId() + " " + handler.getRecipeName());
        }

        GuineaUtil.info("All recipehandlers dumped.");
    }
}
