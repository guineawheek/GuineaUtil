package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;
import gregtech.api.util.GasSpargingRecipe;
import gtPlusPlus.nei.GT_NEI_LFTR_Sparging;
import io.github.guineawheek.guineautil.dump.JSONUtil;
import net.minecraftforge.fluids.FluidStack;
import org.json.JSONArray;
import org.json.JSONObject;

public class GTPPSpargingDumper implements IRecipeDumper {

    @Override
    public String getDumperId() {
        return "gtpp_sparge";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler instanceof GT_NEI_LFTR_Sparging;
    }

    @Override
    public JSONObject dump(ICraftingHandler handler) {
        GT_NEI_LFTR_Sparging gtHandler = (GT_NEI_LFTR_Sparging) handler;

        JSONArray allRecipes = new JSONArray();

        for (GT_NEI_LFTR_Sparging.GasSpargingRecipeNEI recipeNEI : gtHandler.getCache()) {
            GasSpargingRecipe recipe = recipeNEI.mRecipe;
            JSONObject jsonRecipe = new JSONObject();
            jsonRecipe.put("EU/t", recipe.mEUt); // EU/t
            jsonRecipe.put("duration", recipe.mDuration); // base duration

            JSONArray jsonFluidOutputs = new JSONArray();
            for (int i = 0; i < recipe.mFluidOutputs.length; i++) {
                FluidStack outputFluid = recipe.mFluidOutputs[i];
                if (outputFluid == null || outputFluid.getFluid() == null) break;
                jsonFluidOutputs.put(JSONUtil.encodeFluidStack(outputFluid).put("maxOutput", recipe.getMaxOutput(i)));
            }
            jsonRecipe.put("fluidOutputs", jsonFluidOutputs);

            JSONArray jsonFluidInputs = new JSONArray();
            for (int i = 0; i < recipe.mFluidInputs.length; i++) {
                FluidStack inputFluid = recipe.mFluidInputs[i];
                if (inputFluid == null || inputFluid.getFluid() == null) break;
                jsonFluidInputs.put(JSONUtil.encodeFluidStack(inputFluid));
            }
            jsonRecipe.put("fluidInputs", jsonFluidInputs);

            allRecipes.put(jsonRecipe);
        }

        return new JSONObject()
                .put("type", handler.getRecipeName())
                .put("handlerID", handler.getHandlerId())
                .put("recipes", allRecipes);
    }
}
