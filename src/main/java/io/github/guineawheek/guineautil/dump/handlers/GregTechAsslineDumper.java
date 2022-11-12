package io.github.guineawheek.guineautil.dump.handlers;

import codechicken.nei.recipe.ICraftingHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.nei.GT_NEI_AssLineHandler;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.json.JSONArray;
import org.json.JSONObject;

public class GregTechAsslineDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "shapedCrafting";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof GT_NEI_AssLineHandler;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        GT_NEI_AssLineHandler gtHandler = (GT_NEI_AssLineHandler) handler;

        JSONArray allRecipes = new JSONArray();
        for (GT_Recipe recipe : gtHandler.getSortedRecipes()) {
            JSONObject jsonRecipe = new JSONObject();
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
                Object input;
                if (recipe instanceof GT_Recipe.GT_Recipe_WithAlt) {
                    input = ((GT_Recipe.GT_Recipe_WithAlt) recipe).getAltRepresentativeInput(i);
                } else {
                    input = recipe.mInputs[i];
                }
                if (input == null) continue;
                if (input instanceof ItemStack[]) {
                    jsonInputs.put(JSONUtil.encodeItemStackArray((ItemStack[]) input));
                } else {
                    jsonInputs.put(JSONUtil.encodeItemStack((ItemStack) input));
                }
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
            for (FluidStack outputFluid : recipe.mFluidInputs) {
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
