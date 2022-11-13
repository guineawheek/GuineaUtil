# avaritia recipes

## Xtreme crafting recipes
identical to the normal crafting format as detailed in [vanilla.md](vanilla.md). there's just more items.

## Neutronium Compressor

```json5
{
  "recipes": [
    {
      "output": { <ItemStack> }, // crafted item
      "input": [{ <ItemStack> }, { <ItemStack> } ], // accepted oredict items
      "cost": 12800, // the actual number of items needed to be inputted -- ignore the itemstack value
    }
  ],
  "handlerID": "fox.spiteful.avaritia.compat.nei.CompressionHandler",
  "type": "Neutronium Compressor"
}
```
