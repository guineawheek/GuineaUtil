package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.IStillRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.nei.NEIHandlerSqueezer;
import forestry.factory.recipes.nei.NEIHandlerStill;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import org.json.JSONArray;
import org.json.JSONObject;

public class StillDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerStill;
    }
    @Override
    public JSONObject dump(ICraftingHandler handler) {

        // container recipes skipped
        JSONArray allRecipes = new JSONArray();
        for (IStillRecipe recipe : RecipeManagers.stillManager.recipes()) {
            JSONObject jsonRecipe = new JSONObject()
                .put("cyclesPerUnit", recipe.getCyclesPerUnit())
                .put("input", JSONUtil.encodeFluidStack(recipe.getInput()))
                .put("output", JSONUtil.encodeFluidStack(recipe.getOutput()));
            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }

}
