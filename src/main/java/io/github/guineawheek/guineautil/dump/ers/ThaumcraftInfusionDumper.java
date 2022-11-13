package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import com.djgiannuzz.thaumcraftneiplugin.nei.recipehandler.InfusionRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import org.json.JSONArray;
import org.json.JSONObject;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionRecipe;

public class ThaumcraftInfusionDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "tc_infusion";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof InfusionRecipeHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        // we're not using the handler, we're only using it to prompt pulling from the TC4 api

        JSONArray allRecipes = new JSONArray();
        for (Object o : ThaumcraftApi.getCraftingRecipes()) {
            if (o instanceof InfusionRecipe) {
                InfusionRecipe recipe = (InfusionRecipe) o;

                if (recipe.getRecipeOutput() == null || recipe.getRecipeInput() == null) {
                    continue;
                }
                JSONObject jsonRecipe = new JSONObject()
                    .put("research", recipe.getResearch())
                    .put("aspects", JSONUtil.encodeAspectList(recipe.getAspects(recipe.getRecipeInput())))
                    .put("instability", recipe.getInstability(recipe.getRecipeInput()))
                    .put("input", JSONUtil.encodeItemStack(recipe.getRecipeInput()))
                    .put("components", JSONUtil.encodeItemStackArray(recipe.getComponents()));

                Object output = recipe.getRecipeOutput(recipe.getRecipeInput());
                if (output instanceof ItemStack) {
                    jsonRecipe.put("output", JSONUtil.encodeItemStack((ItemStack) output));
                } else if (output instanceof Object[]) {
                    try {
                        Object[] outputArr = (Object[]) output;
                        ItemStack res = recipe.getRecipeInput().copy();
                        res.setTagInfo((String) outputArr[0], (NBTBase) outputArr[1]);
                        jsonRecipe.put("output", JSONUtil.encodeItemStack(res));
                    } catch (ClassCastException e) {
                        // clearly whatever is going on is somehow wrong
                        jsonRecipe.put("output", recipe.getRecipeOutput().toString());
                    }
                } else {
                    // no idea what's goinng on so let's just list this down
                    jsonRecipe.put("output", recipe.getRecipeOutput().toString());
                }

                allRecipes.put(jsonRecipe);
            }
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}
