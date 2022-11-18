package io.github.guineawheek.guineautil.dump.ers.template;

import appeng.integration.modules.NEIHelpers.NEIAEShapedRecipeHandler;
import appeng.integration.modules.NEIHelpers.NEIAEShapelessRecipeHandler;
import codechicken.nei.recipe.ICraftingHandler;
import io.github.guineawheek.guineautil.dump.ers.TemplateRecipeHandlerDumper;

public class AE2CraftingDumper extends TemplateRecipeHandlerDumper {
    @Override
    public String getDumperId() {
        return "ae2_crafting";
    }

    @Override
    public boolean claim(ICraftingHandler handler) {
        // logic should be identical
        return (handler instanceof NEIAEShapedRecipeHandler) || (handler instanceof NEIAEShapelessRecipeHandler);
    }
}
