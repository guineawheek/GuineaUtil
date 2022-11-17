# forestry recipes

when `<ItemStack>`, `<FluidStack>`, or `<Aspect>` is referenced in the schemas, it is formatted as detailed in [item_stacks.md](item_stacks.md)

good lord i hate this mod's recipe systems

## carpenter recipes
```json5
{
  "recipes": [
    {
      "output": { <ItemStack> },
      "packagingTime": 5, // time of recipe in ticks
      "liquid": { <FluidStack> }, //@Nullable: input fluid
      "box": { <ItemStack> } // @Nullable: the extra "box" input item
      "inputs": [ // the items in the crafting grid
        {
          "stack": [ { <ItemStack> } ], // list of accepted oredict stacks
          "idx": 0 // index of item in grid
        }
      ],
      "width": 1, // width of crafting grid
      "height": 1, // height of crafting grid
    },
  ],
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerCarpenter",
  "type": "Carpenter"
}
```

## centrifuge recipes
```json5
{
  "recipes": [
    {
      "outputs": [
        {
          "stack": { <ItemStack> }, // produced output stack
          "probability": 1 // value between 0 and 1
        },
        {
          "stack": { <ItemStack> },
          "probability": 0.5
        } // ...
      ],
      "input": { <ItemStack> }, // input item stack
      "processingTime": 40 // ticks taken to process
    }
  ],
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerCentrifuge",
  "type": "Centrifuge"
}
```

## thermionic fabricator recipes
```json5
{
  "recipes": [
    {
      "output": { <ItemStack> },
      "packagingTime": 5, // time of recipe in ticks
      "liquid": { <FluidStack> }, //@Nullable: input fluid
      "plan": { <ItemStack> } // @Nullable: the "recipe plan" input catalyst
      "inputs": [ // the items in the crafting grid
        {
          "stack": [ { <ItemStack> } ], // list of accepted oredict stacks
          "idx": 0 // index of item in grid
        }
      ],
      "width": 1, // width of crafting grid
      "height": 1, // height of crafting grid
    },
  ],
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerFabricator",
  "type": "Thermionic Fabricator"
}
```

## fermenter recipes

```json5
{
  "recipes": [
    {
      "output": { <FluidStack> }, // output fluid
      "inputFluid": { <FluidStack> }, // input fluid
      "inputItem": [ { <ItemStack> }, { <ItemStack> }], // array of acceptable oredicts
    },
    //...
  ],
  "fuels": [{ <ItemStack }, { <ItemStack> }], // valid input fuels
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerFermenter",
  "type": "Fermenter"
}
```
## moistener recipes

```json5
{
  "recipes": [
    {
      "output": { <ItemStack> }, // output item
      "input": { <ItemStack> }, // input item
      "timePerItem": 20000, // base moistener value of item
      "inputItem": [ { <ItemStack> }, { <ItemStack> }], // array of acceptable oredicts
    },
    //...
  ],
  "fuels": [
    {
      "fuelOutput": { <ItemStack> }, // output item
      "fuelInput": { <ItemStack> }, // input item
      "moistenerValue": 300, // amount of moistener value applied per moistener tick
      "stage": 0, // recipe prioritization. lower values consumed first.
    },
    //...
  ],
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerMoistener",
  "type": "Moistener",
}
```

## squeezer recipes
```json5
{
  "recipes": [
    {
      "fluidOutput": { <FluidStack> }, // @Nullable: output squeezed fluid.
      "inputs": [{ <ItemStack> }, { <ItemStack> }], // input items used in recipe
      "processingTime": 400, // processing time in ticks
      "remnants": { <ItemStack> }, // @Nullable: remnant item to obtain from squeezing.
      "remnantsChance": 0.2 // value between 0 and 1. This value is 0 for null remnants.
    },
    //...
  ],
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerSqueezer",
  "type": "Squeezer"

}
```

## still recipes
```json5
{
  "recipes": [
    {
      "output": { <FluidStack> },
      "input": { <FluidStack> },
      "cyclesPerUnit": 100
    }, //...
  ],
  "handlerID": "forestry.factory.recipes.nei.NEIHandlerStill",
  "type": "Still"
}
```
