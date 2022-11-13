# Vanilla recipes

when `<ItemStack>` is referenced in the schemas, it is formatted as detailed in [item_stacks.md](item_stacks.md)

## Crafting table recipes
applies to both shaped and shapeless crafting.

also applies to a variety of other crafting too, like avaritia xtreme crafting.
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
