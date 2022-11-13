# GuineaUtil

random utility mod mostly for gtnh (1.7.10)

## description

you can dump recipes with `/gutil dump` and it will freeze your game and then make json recipes
for supported NEI handlers in `.minecraft/gutil`

(for this reason, this mod is best used on singleplayer installs and disabled after use)

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
| thaumcraft 4 infusion  | yes                                                                                  |
| forestry               | TODO                                                                                 |
| enderio                | TODO                                                                                 |
| avaritia               | TODO                                                                                 |

for specifics on the dumps, see the `schema` folder of the repository to see how the json files are formatted.

## user support

please report bugs/suggestions to the issue tracker or gtnh discord

"will there be a port to 1.x?" 1.7.10 is the latest version of minecraft
