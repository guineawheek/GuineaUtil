package io.github.guineawheek.guineautil.dump;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    /*
     * Helper class for nbt stuff
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
        return new JSONObject()
            .put("name", Item.itemRegistry.getNameForObject(stack.getItem()))
            .put("label", stack.getDisplayName())
            .put("damage", stack.getItemDamage())
            .put("maxDamage", stack.getItemDamage())
            .put("size", stack.stackSize)
            .put("maxSize", stack.getMaxStackSize())
            .put("oreNames", getOreNames(stack))
            .put("tag", (stack.hasTagCompound()) ? encodeTagCompound(stack.getTagCompound()) : null);
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
        for (ItemStack stack : stacks)
            out.put(encodeItemStack(stack));
        return out;
    }

    public static JSONObject encodeFluidStack(FluidStack stack) {
        return new JSONObject()
            .put("name", stack.getFluid().getName())
            .put("label", stack.getFluid().getLocalizedName(null))
            .put("amount", stack.amount);
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
        for (Object key : tag.func_150296_c())
            out.put(key.toString(), encodeNBT(tag.getTag(key.toString())));
        return out;
    }

    public static JSONArray encodeTagList(NBTTagList tagList) {
        JSONArray out = new JSONArray();
        NBTTagList copy = (NBTTagList) tagList.copy();
        for (int i = 0; i < tagList.tagCount(); i++)
            out.put(copy.removeTag(0));
        return out;
    }
}
