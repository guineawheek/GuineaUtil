package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gtPlusPlus.nei.DecayableRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class GTPPDecayableDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "gtpp_decay";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof DecayableRecipeHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        DecayableRecipeHandler gtppHandler = (DecayableRecipeHandler) handler;

        gtppHandler.loadCraftingRecipes(gtppHandler.getOverlayIdentifier());

        JSONArray allRecipes = new JSONArray();
        for (TemplateRecipeHandler.CachedRecipe crecipe : gtppHandler.arecipes) {
            DecayableRecipeHandler.DecayableRecipeNEI recipe = (DecayableRecipeHandler.DecayableRecipeNEI) crecipe;
            allRecipes.put(new JSONObject()
                .put("input", JSONUtil.encodeItemStack(recipe.getIngredient().item))
                .put("output", JSONUtil.encodeItemStack(recipe.getResult().item))
                .put("time", recipe.time) // in ticks
            );
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}
