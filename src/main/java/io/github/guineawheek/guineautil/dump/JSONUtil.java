package io.github.guineawheek.guineautil.dump;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import io.github.guineawheek.guineautil.GuineaUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.json.JSONArray;
import org.json.JSONObject;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class JSONUtil {
    /*
     * Helper class for item stack and nbt stuff
     *
     * thank you opencomputers for a sane example
     * special unthank you to 2014-era mcp for incompleteness
     */

    /**
     * Encodes an item stack into a json object, nbt and all
     * @param stack item stack in question
     * @return JSONObject
     */
    public static JSONObject encodeItemStack(ItemStack stack) {
        String name = Item.itemRegistry.getNameForObject(stack.getItem());
        JSONObject jsonObject = new JSONObject()
                .put("name", name)
                .put("damage", stack.getItemDamage())
                .put("maxDamage", stack.getMaxDamage())
                .put("size", stack.stackSize)
                .put("maxSize", stack.getMaxStackSize())
                .put("oreNames", getOreNames(stack))
                .put("tag", (stack.hasTagCompound()) ? encodeTagCompound(stack.getTagCompound()) : null);

        try {
            jsonObject.put("label", stack.getDisplayName());
        } catch (Exception e) {
            // thanks forestry. fuck you.
            GuineaUtil.error("=== JANK-ASS MOD ALERT! Could not dump display name for item " + name);
            GuineaUtil.error(getStackTrace(e));
            jsonObject.put("label", name);
        }

        return jsonObject;
    }

    private static List<String> getOreNames(ItemStack stack) {
        List<String> ret = new ArrayList<>();
        for (int id : OreDictionary.getOreIDs(stack)) {
            ret.add(OreDictionary.getOreName(id));
        }
        return ret;
    }

    public static JSONArray encodeItemStackArray(ItemStack[] stacks) {
        JSONArray out = new JSONArray();
        for (ItemStack stack : stacks) out.put(encodeItemStack(stack));
        return out;
    }

    public static JSONArray encodeItemStackList(List<ItemStack> stacks) {
        JSONArray out = new JSONArray();
        for (ItemStack stack : stacks) out.put(encodeItemStack(stack));
        return out;
    }

    public static JSONObject encodeFluidStack(FluidStack stack) {
        return new JSONObject()
                .put("name", stack.getFluid().getName())
                .put("label", stack.getFluid().getLocalizedName(null))
                .put("amount", stack.amount);
    }

    public static Object encodeNullable(Object obj) {
        if (obj == null) {
            return JSONObject.NULL;
        } else if (obj instanceof ItemStack) {
            return encodeItemStack((ItemStack) obj);
        } else if (obj instanceof ItemStack[]) {
            return encodeItemStackArray((ItemStack[]) obj);
        } else if (obj instanceof List) {
            List oList = (List) obj;
            if (oList.isEmpty() || !(oList.get(0) instanceof ItemStack)) return new JSONArray();
            return encodeItemStackList(oList);
        } else if (obj instanceof FluidStack) {
            return encodeFluidStack((FluidStack) obj);
        }
        return obj.toString();
    }

    /**
     * encodes any NBTBase into either a native java value or a JSON object
     * the point is that you can call toString on the returned values sanely
     *
     * a lot of the logic is returning the underlying java data structure under the nbt wrapper object
     *
     * @param tag any NBT object
     * @return something toStringable sanely in json
     */
    public static Object encodeNBT(NBTBase tag) {
        if (tag instanceof NBTTagByte) return ((NBTTagByte) tag).func_150290_f();
        else if (tag instanceof NBTTagShort) return ((NBTTagShort) tag).func_150289_e();
        else if (tag instanceof NBTTagInt) return ((NBTTagInt) tag).func_150287_d();
        else if (tag instanceof NBTTagLong) return ((NBTTagLong) tag).func_150291_c();
        else if (tag instanceof NBTTagFloat) return ((NBTTagFloat) tag).func_150288_h();
        else if (tag instanceof NBTTagDouble) return ((NBTTagDouble) tag).func_150286_g();
        else if (tag instanceof NBTTagByteArray) return ((NBTTagByteArray) tag).func_150292_c();
        else if (tag instanceof NBTTagIntArray) return ((NBTTagIntArray) tag).func_150302_c();
        else if (tag instanceof NBTTagString) return ((NBTTagString) tag).func_150285_a_();
        else if (tag instanceof NBTTagList) return encodeTagList((NBTTagList) tag);
        else if (tag instanceof NBTTagCompound) return encodeTagCompound((NBTTagCompound) tag);
        return tag.toString();
    }

    public static JSONObject encodeTagCompound(NBTTagCompound tag) {
        JSONObject out = new JSONObject();
        for (Object key : tag.func_150296_c()) out.put(key.toString(), encodeNBT(tag.getTag(key.toString())));
        return out;
    }

    public static JSONArray encodeTagList(NBTTagList tagList) {
        JSONArray out = new JSONArray();
        NBTTagList copy = (NBTTagList) tagList.copy();
        for (int i = 0; i < tagList.tagCount(); i++) out.put(copy.removeTag(0));
        return out;
    }

    public static JSONArray encodeAspectList(AspectList aspectList) {
        JSONArray out = new JSONArray();
        for (Aspect aspect : aspectList.aspects.keySet()) {
            out.put(new JSONObject().put("aspect", aspect.getTag()).put("amount", aspectList.getAmount(aspect)));
        }
        return out;
    }

    public static JSONArray encodeShapedCraftingList(Object[] inputStacks) {
        JSONArray out = new JSONArray();
        for (int i = 0; i < inputStacks.length; i++) {
            JSONObject stack = new JSONObject().put("idx", i);
            Object obj = inputStacks[i];
            if (obj instanceof ItemStack) {
                stack.put("stack", new JSONArray().put(encodeItemStack((ItemStack) obj)));
            } else if (obj instanceof ItemStack[]) {
                stack.put("stack", encodeItemStackArray((ItemStack[]) obj));
            } else if (obj instanceof List) {
                stack.put("stack", encodeItemStackList((List<ItemStack>) obj));
            } else {
                continue;
            }
            out.put(stack);
        }
        return out;
    }
}
