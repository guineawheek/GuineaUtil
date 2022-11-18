package io.github.guineawheek.guineautil.dump.ers.template;

import codechicken.nei.recipe.ICraftingHandler;
import ic2.neiIntegration.core.recipehandler.AdvRecipeHandler;
import ic2.neiIntegration.core.recipehandler.AdvShapelessRecipeHandler;
import io.github.guineawheek.guineautil.dump.ers.TemplateRecipeHandlerDumper;

public class IC2CraftingDumper extends TemplateRecipeHandlerDumper {
    @Override
    public String getDumperId() {
        return "shaped_ic2_crafting";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        // logic should be identical
        return (handler instanceof AdvRecipeHandler) || (handler instanceof AdvShapelessRecipeHandler);
    }
}
