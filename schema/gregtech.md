# Gregtech recipes

when `<ItemStack>`, `<FluidStack>`, or `<Aspect>` is referenced in the schemas, it is formatted as detailed in [item_stacks.md](item_stacks.md)


## 99% of the recipes

(applies to both assline and non-assline)

```json5
{
  "recipes": [ // list of all recipes
    {
      "duration": 40, // base recipe duration in ticks
      "EU/t": 7, // base EU/t
      "specialValue": 0, // special value, see note below
      "hidden": false, // whether or not the recipe is hidden from NEI
      "fake": false, // whether or not the recipe exists to be displayed in NEI and doesn't actually craft
      "inputs": [
        // array of item inputs
        // each entry is an array of accepted oredict alternatives for an input
        [
          { <ItemStack> }, // this could be one type of sulfur
          { <ItemStack> } // this could be another
        ],
        [
          { <ItemStack> } // some other ingredient, for which there is only one item
        ]
      ],
      "outputs": [ // array of item outputs
        {
          <ItemStack>,
          "chance": 10000, // the chance of an output, in hundredths of a percent. 10000 == 100%.
          // this field is tacked onto the ItemStack JSON object.
        }
      ],
      "fluidInputs": [ // array of fluid inputs
        { <FluidStack> },
        { <FluidStack> }
      ],
      "fluidOutputs": [ // array of fluid outputs -- this field is omitted on assline recipes
        { <FluidStack> },
        { <FluidStack> }
      ],
      // this field isn't guarenteed to exist, but Bartworks uses this as a catalyst for certain machines
      "specialItem": { <ItemStack> }
    }
  ],
  "handlerID": "gregtech.nei.GT_NEI_DefaultHandler",
  "type": "the title of the NEI tab"
}
```

### General notes

If the ItemStack `size` field is 0 for an input, it means that input is not consumed (it's a programmed circuit or some catalyst)

`specialItem` is used primarily in Bartworks machines:

* for Circuit Assembly Line to specify the imprint
* for Bacterial Vat to specify the bacterial culture
* for Bio Lab for modules
* it's also used in vanilla GT for Scanner and Printer for data sticks.

`specialValue` has various meanings based on the machine. It is a 32-bit signed integer.

By default, the following applies:
| specialValue | condition |
| --- | --- |
| -100 | Needs Low Gravity |
| -200 | Needs Cleanroom |
| -201 | Scan for Assembly Line |
| -300 | Needs Cleanroom and Low Gravity |
| -400 | Deprecated Recipe |

For coiled machines such as EBFs/DTPFs/whatever, it's the minimum heat value.

Bartworks and GoodGenerator both have machines with more complex usages of `specialValue` -- you
may need to read their sourcecode to figure out how it applies to the bacvat or the neutron accelerator


## GT++ Decayable Recipes

These are recipes for the lead-lined box.

```json5
{
  "recipes": [
    {
      "input": { <ItemStack> },
      "output": { <ItemStack> },
      "time": 10000 // in ticks
    }
  ],
  "handlerID": "gtPlusPlus.nei.DecayableRecipeHandler",
  "type": "Decayables"
}
```

## GT++ Sparging Tower
pretty similar to the normal recipe handler, just without items

```json5
{
  "recipes": [
    {
      "duration": 5000,
      // in ticks
      "fluidInputs": [
        { <FluidStack> },
        { <FluidStack> }
      ],
      "EU/t": 7680,
      "fluidOutputs": [
        {
          <FluidStack>,
          "maxOutput": 10000 // stapled onto the FluidStack object
        }
      ]
    }
  ],
  "handlerID": "gtPlusPlus.nei.GT_NEI_LFTR_Sparging",
  "type": "LFTR Gas Sparging"
}
```
