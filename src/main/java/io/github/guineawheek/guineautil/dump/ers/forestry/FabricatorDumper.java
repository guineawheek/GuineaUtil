package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import forestry.api.recipes.IFabricatorRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.nei.NEIHandlerFabricator;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import org.json.JSONArray;
import org.json.JSONObject;

public class FabricatorDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerFabricator;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {

        JSONArray allRecipes = new JSONArray();

        for (IFabricatorRecipe recipe : RecipeManagers.fabricatorManager.recipes()) {
            JSONObject jsonRecipe = new JSONObject()
                    .put("input", JSONUtil.encodeShapedCraftingList(recipe.getIngredients()))
                    .put("width", recipe.getWidth())
                    .put("height", recipe.getHeight())
                    .put("output", JSONUtil.encodeItemStack(recipe.getRecipeOutput()))
                    .put(
                            "plan",
                            (recipe.getPlan() == null) ? JSONObject.NULL : JSONUtil.encodeItemStack(recipe.getPlan()))
                    .put(
                            "liquid",
                            (recipe.getLiquid() == null)
                                    ? JSONObject.NULL
                                    : JSONUtil.encodeFluidStack(recipe.getLiquid()));
            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes);
    }
}
