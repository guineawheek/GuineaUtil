package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.nei.NEIHandlerSqueezer;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import org.json.JSONArray;
import org.json.JSONObject;

public class SqueezerDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerSqueezer;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {

        // container recipes skipped
        JSONArray allRecipes = new JSONArray();
        for (ISqueezerRecipe recipe : RecipeManagers.squeezerManager.recipes()) {
            JSONObject jsonRecipe = new JSONObject()
                    .put("processingTime", recipe.getProcessingTime())
                    .put("fluidOutput", JSONUtil.encodeNullable(recipe.getFluidOutput()))
                    .put("inputs", JSONUtil.encodeNullable(recipe.getResources()));

            if (recipe.getRemnants() != null) {
                jsonRecipe.put("remnants", JSONUtil.encodeItemStack(recipe.getRemnants()));
                jsonRecipe.put("remnantsChance", recipe.getRemnantsChance());
            } else {
                jsonRecipe.put("remnants", JSONObject.NULL);
                jsonRecipe.put("remnantsChance", 0);
            }

            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes);
    }
}
