# BelovedBlocks

Development builds available for each commit [on our Jenkins server](http://jenkins.carrade.eu/job/BelovedBlocks/).

This plugin will be soon avaible on BukkitDev.
Please note, this README reflects the last development version. Some features listed here may not be in the version published on BukkitDev.


1. [Features](#features)
   1. [New items](#items)
   2. [Stonecutter](#stonecutter)
1. [Commands](#commands)
2. [Configuration](#configuration)
3. [License](#license)


## Features

The 1.8 version of minecraft has removed the items that corresponds to some unobtainables blocks in survival. Although their corresponding items doesn't exist anymore, the blocks still exist. You can place them by doing a  `/setblock` command with some metadata arguments.

This plugin instances new items that transforms themselves into the desired special block when placed by a player.

![](http://amaury.carrade.eu/files/banner.png)

*The three seamless blocks featured by the plugin*

### Items

Each seamless block is represented by a similar item with a glowing enchantement effect (that can be disabled for each block in the [configuration](#configuration)). They also have a custom name (also configurable). The crafts are the same for the three block:

![](http://amaury.carrade.eu//files/output_K6fg1T.gif)

The crafts can be disabled (no permission yet).

### Stonecutter

The stonecutter is a tool that can smooth any of the three double slabs blocks. To use it, simply right click with this tool on the wished block and it will permute to a smooth block. Each use will decrease the item's durability by 1. The unbreaking enchantement does work when applied. The stonecutter can be crafted like shears in exceptions that the iron is replaced by diamonds:

![](http://amaury.carrade.eu//files/BB_shears.png)


The craft can be disabled (no permission yet).

The basic double slabs block → the seamless version of this block:

![](http://amaury.carrade.eu//files/blocks_transforms_small.png)

The process can be reverted (using the tool on a smooth block will turn it back into the original block).

## Commands

No commands avaible yet.

## Configuration

The plugin can be configured using the `config.yml` file (it can be found at `/plugins/BelovedBlocks/config.yml` in your server's folder or [here on git](https://github.com/AmauryCarrade/BelovedBlocks/blob/master/src/main/resources/config.yml)).

## License

GPLv3. See `LICENSE` file for more details.
