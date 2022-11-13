package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class VanillaSmeltingDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "smelting";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler.getHandlerId().equals("codechicken.nei.recipe.FurnaceRecipeHandler");
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {

        JSONArray allRecipes = new JSONArray();

        Map<ItemStack, ItemStack> recipes =
            (Map<ItemStack, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();

        for (ItemStack inputItem : recipes.keySet()) {
            ItemStack outputItem = recipes.get(inputItem);

            allRecipes.put(new JSONObject()
                .put("input", JSONUtil.encodeItemStack(inputItem))
                .put("output", JSONUtil.encodeItemStack(outputItem)));
        }


        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}
