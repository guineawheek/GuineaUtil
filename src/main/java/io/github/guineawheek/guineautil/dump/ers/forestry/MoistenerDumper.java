package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import forestry.api.fuels.FuelManager;
import forestry.api.fuels.MoistenerFuel;
import forestry.api.recipes.IMoistenerRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.nei.NEIHandlerMoistener;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import org.json.JSONArray;
import org.json.JSONObject;

public class MoistenerDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerMoistener;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {

        // recipes and fuels are separated.
        // 10 buckets of water are always used.
        JSONArray allRecipes = new JSONArray();
        JSONArray allFuels = new JSONArray();
        for (MoistenerFuel fuel : FuelManager.moistenerResource.values()) {
            JSONObject jsonFuel = new JSONObject()
                    .put("fuelInput", JSONUtil.encodeItemStack(fuel.item))
                    .put("fuelOutput", JSONUtil.encodeItemStack(fuel.product))
                    .put("moistenerValue", fuel.moistenerValue)
                    .put("stage", fuel.stage);
            allFuels.put(jsonFuel);
        }
        for (IMoistenerRecipe recipe : RecipeManagers.moistenerManager.recipes()) {
            JSONObject jsonRecipe = new JSONObject()
                    .put("timePerItem", recipe.getTimePerItem())
                    .put("input", JSONUtil.encodeItemStack(recipe.getResource()))
                    .put("output", JSONUtil.encodeItemStack(recipe.getProduct()));
            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes)
                .put("fuels", allFuels);
    }
}
