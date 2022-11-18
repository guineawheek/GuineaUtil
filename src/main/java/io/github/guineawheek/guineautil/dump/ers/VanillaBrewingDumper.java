package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.BrewingRecipeHandler;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class VanillaBrewingDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "brewing";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler.getHandlerId().equals("codechicken.nei.recipe.BrewingRecipeHandler");
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        BrewingRecipeHandler brh = (BrewingRecipeHandler) handler;
        brh.loadCraftingRecipes(brh.getOverlayIdentifier());

        JSONArray allRecipes = new JSONArray();

        for (TemplateRecipeHandler.CachedRecipe crecipe : brh.arecipes) {
            BrewingRecipeHandler.CachedBrewingRecipe recipe = (BrewingRecipeHandler.CachedBrewingRecipe) crecipe;
            allRecipes.put(new JSONObject()
                    .put("input", JSONUtil.encodeItemStack(recipe.recipe.ingredient.items[0]))
                    .put("precursor", JSONUtil.encodeItemStack(recipe.recipe.precursorPotion.items[0]))
                    .put("output", JSONUtil.encodeItemStack(recipe.recipe.result.items[0])));
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes);
    }
}
