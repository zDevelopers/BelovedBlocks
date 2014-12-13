# BelovedBlocks

Development builds available for each commit [on our Jenkins server](http://jenkins.carrade.eu/job/BelovedBlocks/).

This plugin will be soon avaible on BukkitDev.
Please note, this README reflects the last development version. Some features listed here may not be in the version published on BukkitDev.


1. [Features](#features)
   1. [New items](#items)
   2. [Stone Cutter](#stone-cutter)
1. [Commands](#commands)
2. [Configuration](#configuration)
3. [License](#license)


## Features

The 1.8 version of minecraft has removed the items that corresponds to some unobtainables blocks in survival. Although their corresponding items doesn't exist anymore, the blocks still exist. You can place them by doing a  `/setblock` command with some metadata arguments.

This plugin instances new items that transforms themselves into the desired special block when placed by a player.

![](http://amaury.carrade.eu/files/banner.png)

*The three seamless blocks featured by the plugin*

### Items

Each seamless block is represented by a similar item with a glowing enchantement effect (that can be disabled for each block in the [configuration](#configuration)). They also have a custom name (also configurable). The crafts are the same for the three block :

![](http://amaury.carrade.eu//files/BB_stone.png)

*Smooth Stone Slab*

![](http://amaury.carrade.eu//files/BB_sandstone.png)

*Smooth Sandstone Slab*

![](http://amaury.carrade.eu//files/BB_redsandstone.png)

*Smooth Red Sandstone Slab*

The crafts can be disabled (no permission yet).

### Stone Cutter


![](http://amaury.carrade.eu//files/BB_shears.png)

## Commands

No commands avaible yet.

## Configuration

The plugin can be configured using the `config.yml` file (it can be found here `/tree/master/src/main/resources`).

## License

GPLv3. See `LICENSE` file for more details.
