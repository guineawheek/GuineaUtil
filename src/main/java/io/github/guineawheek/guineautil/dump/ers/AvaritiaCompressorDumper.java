package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import fox.spiteful.avaritia.compat.nei.CompressionHandler;
import fox.spiteful.avaritia.compat.nei.ExtremeShapedRecipeHandler;
import fox.spiteful.avaritia.compat.nei.ExtremeShapelessRecipeHandler;
import fox.spiteful.avaritia.crafting.CompressOreRecipe;
import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.CompressorRecipe;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class AvaritiaCompressorDumper implements IRecipeDumper {
    @Override
    public String getDumperId() {
        return "avaritia_compressor";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return (handler instanceof CompressionHandler);
    }
    @Override
    public JSONObject dump(ICraftingHandler handler) {

        JSONArray allRecipes = new JSONArray();
        for (CompressorRecipe recipe : CompressorManager.getRecipes()) {
            JSONObject jsonRecipe = new JSONObject()
                .put("cost", recipe.getCost())
                .put("output", JSONUtil.encodeItemStack(recipe.getOutput()));

            if (recipe instanceof CompressOreRecipe) {
                CompressOreRecipe oreRecipe = (CompressOreRecipe) recipe;
                jsonRecipe.put("input", JSONUtil.encodeItemStackList((List<ItemStack>) oreRecipe.getIngredient()));
            } else {
                jsonRecipe.put("input", new JSONArray().put(JSONUtil.encodeItemStack((ItemStack) recipe.getIngredient())));
            }
            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }

}
