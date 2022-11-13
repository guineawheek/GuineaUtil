# GuineaUtil

random utility mod mostly for gtnh

## description

you can dump recipes with `/gutil dump` and it will freeze your game and then make json recipes
for supported NEI handlers in `.minecraft/gutil`

this mod aims for completeness in its dumps as well as utility

## features

 - oredict wherever it's relevant
 - all to json, including item nbt

## recipe mod support

| recipe type            | support                                                                              |
|------------------------|--------------------------------------------------------------------------------------|
| vanilla crafting       | shaped, shapeless, oredict                                                           |
| vanilla smelting       | yes                                                                                  |
| vanilla brewing        | yes                                                                                  |
| gregtech 5u            | yes, with oredict, includes assline and addons that subclass `GT_NEI_DefaultHandler` |
| gt++                   | yes, including lead-lined box and sparging tower                                     |
| thaumcraft 4 worktable | yes, shaped, shapeless, oredict                                                      |
| thaumcraft 4 crucible  | yes                                                                                  |
| thaumcraft 4 worktable | yes                                                                                  |
| forestry               | TODO                                                                                 |
| enderio                | TODO                                                                                 |

for specifics on the dumps, see the `schema` folder of the repository to see how the json files
are formatted.
