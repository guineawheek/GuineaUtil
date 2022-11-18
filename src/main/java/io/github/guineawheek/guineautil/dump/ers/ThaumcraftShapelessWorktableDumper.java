package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import com.djgiannuzz.thaumcraftneiplugin.nei.recipehandler.ArcaneShapelessRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;

public class ThaumcraftShapelessWorktableDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "tc_shapeless";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof ArcaneShapelessRecipeHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        // we're not using the handler, we're only using it to prompt pulling from the TC4 api

        JSONArray allRecipes = new JSONArray();
        for (Object o : ThaumcraftApi.getCraftingRecipes()) {
            if (o instanceof ShapelessArcaneRecipe) {
                ShapelessArcaneRecipe recipe = (ShapelessArcaneRecipe) o;
                JSONObject jsonRecipe = new JSONObject()
                        .put("research", recipe.getResearch())
                        .put("output", JSONUtil.encodeItemStack(recipe.getRecipeOutput()))
                        .put("aspects", JSONUtil.encodeAspectList(recipe.getAspects()));
                if (recipe.getRecipeOutput() == null
                        || recipe.getInput() == null
                        || recipe.getInput().size() < 1) {
                    continue;
                }
                JSONArray jsonInputs = new JSONArray();
                for (Object ri : recipe.getInput()) {
                    if (ri instanceof ItemStack) {
                        jsonInputs.put(new JSONArray().put(JSONUtil.encodeItemStack((ItemStack) ri)));
                    } else if (ri instanceof ArrayList) {
                        jsonInputs.put(JSONUtil.encodeItemStackList((ArrayList<ItemStack>) ri));
                    }
                }
                jsonRecipe.put("inputs", jsonInputs);
                allRecipes.put(jsonRecipe);
            }
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes);
    }
}
