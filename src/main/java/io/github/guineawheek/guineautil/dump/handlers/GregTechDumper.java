package io.github.guineawheek.guineautil.dump.handlers;

import codechicken.nei.recipe.ICraftingHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.nei.GT_NEI_DefaultHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;
import org.json.JSONArray;
import org.json.JSONObject;
import scala.util.parsing.json.JSON;

import java.util.Map;

public class GregTechDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "shapedCrafting";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof GT_NEI_DefaultHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        GT_NEI_DefaultHandler gtHandler = (GT_NEI_DefaultHandler) handler;

        JSONArray allRecipes = new JSONArray();
        for (GT_NEI_DefaultHandler.CachedDefaultRecipe cdrecipe : gtHandler.getCache()) {
            JSONObject jsonRecipe = new JSONObject();
            GT_Recipe recipe = cdrecipe.mRecipe;
            jsonRecipe.put("EU/t", recipe.mEUt); // EU/t
            jsonRecipe.put("duration", recipe.mDuration); // base duration
            jsonRecipe.put("fake", recipe.mFakeRecipe); // whether the recipe is just for NEI display
            jsonRecipe.put("hidden", recipe.mHidden); // recipe hidden from NEI

            /*
            for most processing machines, this is needs cleanroom/low gravity/both
            for fusion, this determines tier
            for coiled machines (EBF/DTPF/digester), this is the minimum heat value
            bw biovat somehow encodes cleanroom but also things like sievert, using their own custom function

            basically, if the machine uses one or more other special numbers as a recipe req, chances
            are it's encoded in the specialValue somehow.

            you will have to read source code to figure out how to decode this value
             */
            jsonRecipe.put("specialValue", recipe.mSpecialValue);

            if (recipe.mSpecialItems instanceof ItemStack) {
                // this is used in fake recipes for some sort of catalyst...?
                // it's mentioned as used in Printer
                jsonRecipe.put("specialItem", JSONUtil.encodeItemStack((ItemStack) recipe.mSpecialItems));
            }


            JSONArray jsonInputs = new JSONArray();
            for (int i = 0; i < recipe.mInputs.length; i++) {
                ItemStack input = recipe.mInputs[i];
                if (input == null) continue;
                jsonInputs.put(new JSONArray(JSONUtil.encodeItemStackList(GT_OreDictUnificator.getNonUnifiedStacks(input))));
            }
            jsonRecipe.put("inputs", jsonInputs);

            JSONArray jsonOutputs = new JSONArray();
            for (int i = 0; i < recipe.mOutputs.length; i++) {
                ItemStack output = recipe.mOutputs[i];
                if (output == null) continue;
                // scale is hundredths of a percent.
                // 10000 is 100% chance
                jsonOutputs.put(
                    JSONUtil.encodeItemStack(output)
                        .put("chance", recipe.getOutputChance(i))
                );
            }
            jsonRecipe.put("outputs", jsonOutputs);

            JSONArray jsonFluidInputs = new JSONArray();
            for (FluidStack inputFluid : recipe.mFluidInputs) {
                if (inputFluid == null || inputFluid.getFluid() == null) break;
                jsonFluidInputs.put(JSONUtil.encodeFluidStack(inputFluid));
            }
            jsonRecipe.put("fluidInputs", jsonFluidInputs);

            JSONArray jsonFluidOutputs = new JSONArray();
            for (FluidStack outputFluid : recipe.mFluidOutputs) {
                if (outputFluid == null || outputFluid.getFluid() == null) break;
                jsonFluidOutputs.put(JSONUtil.encodeFluidStack(outputFluid));
            }
            jsonRecipe.put("fluidOutputs", jsonFluidOutputs);

            allRecipes.put(jsonRecipe);
        }


        return new JSONObject()
            .put("type", handler.getRecipeName())
            .put("handlerID", handler.getHandlerId())
            .put("recipes", allRecipes);
    }
}
