package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import com.djgiannuzz.thaumcraftneiplugin.nei.recipehandler.CrucibleRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.CrucibleRecipe;

public class ThaumcraftCrucibleDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "tc_crucible";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof CrucibleRecipeHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        // we're not using the handler, we're only using it to prompt pulling from the TC4 api

        JSONArray allRecipes = new JSONArray();
        for (Object o : ThaumcraftApi.getCraftingRecipes()) {
            if (o instanceof CrucibleRecipe) {
                CrucibleRecipe recipe = (CrucibleRecipe) o;
                JSONObject jsonRecipe = new JSONObject()
                        .put("research", recipe.key)
                        .put(
                                "output",
                                JSONUtil.encodeItemStack(recipe.getRecipeOutput())
                                        .put("aspects", JSONUtil.encodeAspectList(recipe.aspects)));
                if (recipe.getRecipeOutput() == null || recipe.catalyst == null) {
                    continue;
                }
                JSONArray jsonInputs = null;
                if (recipe.catalyst instanceof ItemStack) {
                    jsonInputs = new JSONArray().put(JSONUtil.encodeItemStack((ItemStack) recipe.catalyst));
                } else if (recipe.catalyst instanceof ArrayList) {
                    jsonInputs =
                            new JSONArray().put(JSONUtil.encodeItemStackList((ArrayList<ItemStack>) recipe.catalyst));
                }

                if (jsonInputs == null || jsonInputs.length() < 1) continue;
                jsonRecipe.put("input", jsonInputs);
                allRecipes.put(jsonRecipe);
            }
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes);
    }
}
