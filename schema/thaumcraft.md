# thaumcraft recipes

when `<ItemStack>`, `<FluidStack>`, or `<Aspect>` is referenced in the schemas, it is formatted as detailed in [item_stacks.md](item_stacks.md)

## arcane crafting: shaped

The inputs array preserves the underlying recipe array index and recipe size.
By pieceing together the blanks, one could reconstruct the original table layout.

```json5
{
  "recipes": [ // list of all recipes
    {
      "inputs": [
        // array of input stacks
        {
          "stack": [ // array of accepted oredicted stacks
            { <ItemStack> },
            { <ItemStack> }
          ],
          "idx": 0 // index in the recipe array of the item
        },
        {
          "stack": [
            { <ItemStack> },
          ],
          "idx": 2
        },
        // ...
      ],
      "aspects": [ // consumed vis per craft
        { <Aspect> },
        { <Aspect> },
      ],
      "output": { <ItemStack> }, // crafted item
      "research" : "RESEARCH_KEY", // required research key
      "width": 3, // recipe width
      "height": 3 // recipe height
    }
  ],
  "handlerID": "ru.timeconqueror.tcneiadditions.nei.arcaneworkbench.ArcaneCraftingShapedHandler",
  "type": "Shaped A.Worktable"
}
```

## arcane crafting: shapeless
```json5
{
  "recipes": [
    {
      "output": { <ItemStack> }, // crafted item
      "inputs": [
        [{ <ItemStack> }, { <ItemStack> } ], // oredict items for input 1
        [{ <ItemStack> }] // oredict items for input 2
      ],
      "aspects": [ // vis consumed
        { <Aspect> },
        { <Aspect> }
      ],
      "research": "BASICARTIFACE" // research key
    },
  ],
  "handlerID": "ru.timeconqueror.tcneiadditions.nei.arcaneworkbench.ArcaneCraftingShapelessHandler",
  "type": "Shapeless A.Worktable"
}
```

## crucible recipes

```json5
{
  "recipes": [
    {
      "output": { <ItemStack> }, // crafted item
      "input": [{ <ItemStack> }, { <ItemStack> } ], // accepted oredict items
      "aspects": [ // essentia consumed
        { <Aspect> },
        { <Aspect> }
      ],
      "research": "CRUCIBLE", // research key
    }
  ],
  "handlerID": "ru.timeconqueror.tcneiadditions.nei.TCNACrucibleRecipeHandler", // depends on if you have tcneiaddons
  "type": "Crucible"
}
```

## infusion recipes
```json5
{
  "recipes": [
    {
      "output": { <ItemStack> }, // crafted item
      "input": { <ItemStack> }, // center pedestal item
      "components": [{ <ItemStack> }, { <ItemStack> }, ...], // items placed on surrounding pedestals
      "aspects": [ // essentia consumed
        { <Aspect> },
        { <Aspect> }
      ],
      "research": "CRUCIBLE", // research key
      "instability": 8, // infusion instability value
    }
  ],
  "handlerID": "ru.timeconqueror.tcneiadditions.nei.TCNAInfusionRecipeHandler",
  "type": "Arcane Infusion"
}

```
