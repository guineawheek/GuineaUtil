package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import com.djgiannuzz.thaumcraftneiplugin.nei.recipehandler.ArcaneShapedRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

import java.util.ArrayList;

public class ThaumcraftShapedWorktableDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "tc_shaped";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof ArcaneShapedRecipeHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        // we're not using the handler, we're only using it to prompt pulling from the TC4 api

        JSONArray allRecipes = new JSONArray();
        for (Object o : ThaumcraftApi.getCraftingRecipes()) {
            if (o instanceof ShapedArcaneRecipe) {
                ShapedArcaneRecipe recipe = (ShapedArcaneRecipe) o;
                JSONObject jsonRecipe = new JSONObject()
                    .put("research", recipe.getResearch())
                    .put("output", JSONUtil.encodeItemStack(recipe.getRecipeOutput()))
                    .put("aspects", JSONUtil.encodeAspectList(recipe.getAspects()))
                    .put("width", recipe.width)
                    .put("height", recipe.height);
                if (recipe.getRecipeOutput() == null || recipe.getInput() == null || recipe.getInput().length < 1) {
                    continue;
                }
                /*
                JSONArray jsonInputs = new JSONArray();
                for (int i = 0; i < recipe.getInput().length; i++) {
                    Object ri = recipe.getInput()[i];
                    JSONObject jsonInput = new JSONObject().put("idx", i);
                    if (ri instanceof ItemStack) {
                        jsonInputs.put(jsonInput.put("stack", new JSONArray().put(JSONUtil.encodeItemStack((ItemStack) ri))));
                    } else if (ri instanceof ArrayList) {
                        jsonInputs.put(jsonInput.put("stack", new JSONArray().put(JSONUtil.encodeItemStackList((ArrayList<ItemStack>) ri))));
                    }
                }*/

                jsonRecipe.put("inputs", JSONUtil.encodeShapedCraftingList(recipe.getInput()));
                allRecipes.put(jsonRecipe);
            }
        }


        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}
