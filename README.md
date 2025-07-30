# Glacyte Client

## Run arguments
* --local/-l: localhost connection
* --developer/-d: developer mode

## Developer mode
Developer modes give you the ability to have a cache placed inside the client directory at `/client/local_cache/`.
It also allows you to use developer commands (also enabled if you have the developer rank in-game) and prints out some warnings that otherwise aren't shown.

## Inventory hover menus
Item information and stat menus are loaded via `HoverManager.init()` during startup. The client enables these menus by default using a magenta highlight. You can disable them or choose a different color from the **Inventory Menu** dropdown in the settings tab.
Custom hover text works for any item ID defined in `HoverManager`, even if the
item isn't equippable (e.g. consumables or loot boxes).

## Item stats
Item bonuses shown in the Ctrl tooltip are read from `item_stats.json` in the cache
directory. If that file isn’t found the client falls back to the bundled
`item_stats.json` so the stats menu still appears. The JSON should match the
server format where each key is an item id and the `equipment` block holds the
bonus values (for example `astab`, `aslash`, `dstab`, `drange`, etc.). Only these
numbers are used by the client when displaying the stats.
The loader logs how many entries were loaded and whether the data came from the
cache or the bundled sample to make troubleshooting easier.
