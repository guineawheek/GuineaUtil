package io.github.guineawheek.guineautil.dump.ers.template;

import codechicken.nei.recipe.ICraftingHandler;
import fox.spiteful.avaritia.compat.nei.ExtremeShapedRecipeHandler;
import fox.spiteful.avaritia.compat.nei.ExtremeShapelessRecipeHandler;
import io.github.guineawheek.guineautil.dump.ers.TemplateRecipeHandlerDumper;

public class AvaritiaXtremeCraftingDumper extends TemplateRecipeHandlerDumper {
    @Override
    public String getDumperId() {
        return "avaritia_xtreme";
    }
    @Override
    public boolean claim(ICraftingHandler handler) {
        // logic should be identical, as avaritia's NEI handler simply extends the vanilla one
        // it's just vanilla crafting with Moar Items
        return (handler instanceof ExtremeShapedRecipeHandler) || (handler instanceof ExtremeShapelessRecipeHandler);
    }

}
