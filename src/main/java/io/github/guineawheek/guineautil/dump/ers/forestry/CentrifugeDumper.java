package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import forestry.api.recipes.ICarpenterRecipe;
import forestry.api.recipes.ICentrifugeRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.nei.NEIHandlerBottler;
import forestry.factory.recipes.nei.NEIHandlerCentrifuge;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import net.minecraft.item.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

public class CentrifugeDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerCentrifuge;
    }
    @Override
    public JSONObject dump(ICraftingHandler handler) {

        JSONArray allRecipes = new JSONArray();

        for (ICentrifugeRecipe recipe : RecipeManagers.centrifugeManager.recipes()) {
            JSONObject jsonRecipe = new JSONObject()
                .put("input", JSONUtil.encodeItemStack(recipe.getInput()))
                .put("processingTime", recipe.getProcessingTime());
            JSONArray jsonOutputs = new JSONArray();
            for (ItemStack stack : recipe.getAllProducts().keySet()) {
                float prob = recipe.getAllProducts().get(stack);
                jsonOutputs.put(new JSONObject()
                    .put("stack", JSONUtil.encodeItemStack(stack))
                    .put("probability", prob)
                );
            }
            jsonRecipe.put("outputs", jsonOutputs);
            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }

}
