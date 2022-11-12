package io.github.guineawheek.guineautil.dump.handlers;

import codechicken.nei.recipe.BrewingRecipeHandler;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class VanillaBrewingDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "shapedCrafting";
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
                .put("input", recipe.recipe.ingredient.items[0])
                .put("precursor", recipe.recipe.precursorPotion.items[0])
                .put("output", recipe.recipe.result.items[0])
            );
        }



        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}