package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import forestry.api.fuels.FermenterFuel;
import forestry.api.fuels.FuelManager;
import forestry.factory.recipes.nei.NEIHandlerFermenter;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import org.json.JSONArray;
import org.json.JSONObject;

public class FermenterDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerFermenter;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {

        // we use the NEI handler because the raw recipes are Something
        JSONArray allRecipes = new JSONArray();
        ((NEIHandlerFermenter) handler).loadAllRecipes();

        for (TemplateRecipeHandler.CachedRecipe crecipe : ((NEIHandlerFermenter) handler).arecipes) {
            NEIHandlerFermenter.CachedFermenterRecipe recipe = (NEIHandlerFermenter.CachedFermenterRecipe) crecipe;
            JSONObject jsonRecipe = new JSONObject();
            jsonRecipe.put("inputItem", JSONUtil.encodeItemStackArray(recipe.inputItems.get(0).items));
            if (recipe.tanks.size() > 0) {
                jsonRecipe.put(
                        "inputFluid",
                        JSONUtil.encodeFluidStack(recipe.tanks.get(0).tank.getFluid()));
            }
            if (recipe.tanks.size() > 1) {
                jsonRecipe.put(
                        "output",
                        JSONUtil.encodeFluidStack(recipe.tanks.get(1).tank.getFluid()));
            }
            allRecipes.put(jsonRecipe);
        }

        JSONArray allFuels = new JSONArray();
        for (FermenterFuel fuel : FuelManager.fermenterFuel.values()) {
            allFuels.put(JSONUtil.encodeItemStack(fuel.item));
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes)
                .put("fuels", allFuels);
    }
}
