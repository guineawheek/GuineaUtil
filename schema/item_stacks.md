# Item stacks
```json
{ <ItemStack> } =

{
  "damage": 0, // item damage
  "size": 1, // stack size
  "maxDamage": 0,
  "name": "minecraft:wheat",
  "maxSize": 64, // maximum stack size
  "label": "Wheat", // the item display name
  "oreNames": [ // oredict names
    "itemWheat",
    "cropWheat"
  ],
  "tag": {
    // item's NBT tag, as converted to JSON
  }
}
```

# Fluid stacks

```json
{ <FluidStack> } =
{
  "name": "fluorine", // fluid name
  "label": "Fluorine", // display name
  "amount": 1000 // amount, in millibuckets
}
```
