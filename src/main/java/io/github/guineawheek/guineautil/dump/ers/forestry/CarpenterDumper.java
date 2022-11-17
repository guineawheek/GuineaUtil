package io.github.guineawheek.guineautil.dump.ers.forestry;

import codechicken.nei.recipe.ICraftingHandler;
import forestry.api.recipes.ICarpenterRecipe;
import forestry.api.recipes.IDescriptiveRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.factory.recipes.nei.NEIHandlerBottler;
import forestry.factory.recipes.nei.NEIHandlerCarpenter;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import io.github.guineawheek.guineautil.dump.ers.IRecipeDumper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CarpenterDumper implements IRecipeDumper {

    /*
    Carpenter - yes
    Centrifuge - yes
    Fabricator - yes
    Fermenter - yes
    Moistener - yes
    Squeezer - yes
    Still - yes
     */
    @Override
    public String getDumperId() {
        return "forestry_machine";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof NEIHandlerCarpenter;
    }
    @Override
    public JSONObject dump(ICraftingHandler handler) {

        JSONArray allRecipes = new JSONArray();
        for (ICarpenterRecipe recipe : RecipeManagers.carpenterManager.recipes()) {
            FluidStack fluid = recipe.getFluidResource();
            ItemStack box = recipe.getBox();
            IDescriptiveRecipe craftingRecipe = recipe.getCraftingGridRecipe();
            JSONObject jsonRecipe = new JSONObject()
                .put("packagingTime", recipe.getPackagingTime()) // time in ticks
                .put("liquid", (fluid != null) ? JSONUtil.encodeFluidStack(fluid) : JSONObject.NULL)
                .put("box", (box != null) ? JSONUtil.encodeItemStack(box) : JSONObject.NULL)
                .put("width", craftingRecipe.getWidth())
                .put("height", craftingRecipe.getHeight())
                .put("output", JSONUtil.encodeItemStack(craftingRecipe.getRecipeOutput()));

            allRecipes.put(jsonRecipe.put("inputs", JSONUtil.encodeShapedCraftingList(craftingRecipe.getIngredients())));
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }

}
