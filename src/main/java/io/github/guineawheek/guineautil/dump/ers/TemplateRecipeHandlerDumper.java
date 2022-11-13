package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import io.github.guineawheek.guineautil.GuineaUtil;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public abstract class TemplateRecipeHandlerDumper implements IRecipeDumper {
    /*
     * Dumps vanilla recipes.
     * Can also dump other handlers that also use TemplateRecipeHandler,
     * which quite a few mods like IC2, AE2, and Avaritia do.
     */

    @Override
    public String getDumperId() {
        return "shaped_crafting";
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        TemplateRecipeHandler h = (TemplateRecipeHandler) handler;
        h.loadCraftingRecipes(h.getOverlayIdentifier()); // needed to load recipes
        JSONArray allRecipes = new JSONArray();

        for (TemplateRecipeHandler.CachedRecipe recipe : h.arecipes) {
            JSONObject out = new JSONObject();
            out.put("outputs", JSONUtil.encodeItemStackArray(recipe.getResult().items));


            JSONArray ingArray = new JSONArray();
            List<PositionedStack> stacks = recipe.getIngredients();
            boolean isOreDict = false;

            for (PositionedStack stack: stacks) {
                if (stack.items.length > 1)
                    isOreDict = true;

                JSONObject ingStack = new JSONObject().put("stack", JSONUtil.encodeItemStackArray(stack.items));
                if (h instanceof ShapedRecipeHandler) {
                    ingStack.put("relx", stack.relx).put("rely", stack.rely);
                }
                ingArray.put(ingStack);
            }
            out.put("inputs", ingArray);
            out.put("isOreDict", isOreDict);

            if (GuineaUtil.DEBUG)
                GuineaUtil.info(out.toString());
            allRecipes.put(out);
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}