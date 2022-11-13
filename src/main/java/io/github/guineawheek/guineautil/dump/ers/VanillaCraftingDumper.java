package io.github.guineawheek.guineautil.dump.ers;

import codechicken.nei.recipe.ICraftingHandler;

public class VanillaCraftingDumper extends TemplateRecipeHandlerDumper {
    /*
     * Dumps vanilla recipes.
     */

    @Override
    public String getDumperId() {
        return "shaped_crafting";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        return handler.getHandlerId().equals("codechicken.nei.recipe.ShapedRecipeHandler")
            || handler.getHandlerId().equals("codechicken.nei.recipe.ShapelessRecipeHandler");
    }
}
