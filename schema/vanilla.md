# Vanilla recipes

when `<ItemStack>` is referenced in the schemas, it is formatted as detailed in [item_stacks.md](item_stacks.md)

## Crafting table recipes/TemplateRecipeHandler
Applies to both shaped and shapeless crafting.

If you enable the generic TemplateRecipeHandler dumper in the config, they will also use this format.

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
          "rely": 0, // relative y coordinate in nei display
          "relx": 0, // relative x coordinate in nei display
        },
        { // the next item
          "stack": [
            { <ItemStack> },
          ],
          "rely": 42,
          "relx": 69,
        }
        // ...
      ],
      "outputs": [ // array of output stacks
        { <ItemStack> },
        { <ItemStack> },
        // ...
      ]
    }
  ],
  "handlerID": "the id of the recipe handler",
  "type": "the title of the NEI tab"
}
```

## Smelting recipes

```json5
{
  "recipes": [ // list of all recipes
    {
      "output": { <ItemStack> }, // thing that is smelted
      "input": { <ItemStack> } // thing to be smelted
    }
  ],
  "handlerID": "the id of the recipe handler",
  "type": "the title of the NEI tab"
}
```

## Brewing recipes

```json5
{
  "recipes": [ // list of all recipes
    {
      "output": { <ItemStack> }, // result potion
      "input": { <ItemStack> } // thing used to create the next potion
      "precursor": { <ItemStack> } // precursor potion
    }
  ],
  "handlerID": "the id of the recipe handler",
  "type": "the title of the NEI tab"
}
```
