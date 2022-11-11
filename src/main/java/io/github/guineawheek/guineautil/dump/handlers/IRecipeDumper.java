package io.github.guineawheek.guineautil.dump.handlers;

import codechicken.nei.recipe.ICraftingHandler;
import org.json.JSONObject;

public interface IRecipeDumper {
    boolean claim(ICraftingHandler handler);
    JSONObject dump(ICraftingHandler handler);

    String getDumperId();
}
