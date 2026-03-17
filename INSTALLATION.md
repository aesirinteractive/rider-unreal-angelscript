# Install guide

## NodeJs
The language server requires NodeJs, so make sure that is installed

## Installing the plugin
1. Download `Angelscript-<version>.zip` from [releases](https://github.com/aesirinteractive/rider-unreal-angelscript/releases)
2. In Rider, open `Settings > Plugins > ⚙️ > Install Plugin from Disk` or run action `Install Plugin from Disk`
3. Select the downloaded `Angelscript-<version>.zip`


## Setup

All settings for the plugin can be found under `Tools > AngelScript`

### Debugging

Create a debug configuration for Angelscript. If the `Angelscript` type doesn't show up or is grayed out, try opening an AngelScript file to get Rider to recognize that AngelScript is enabled in the project, then you should be able to add the debug configuration

You don't need to configure anything in the debug configuration itself, just change the name to something like e.g. `Debug Angelscript` and save.

### Formatting

If you get an error when trying to format a file, remove the following lines if they exist inside your `.clang-format` file:

```
    CanonicalDelimiter: 
```

You can also create a separate `.clang-format` file for AngelScript. Call it `.clang-format-angelscript` and put it next to the regular `.clang-format` file and the plugin should automatically use that.

You might also want to change this setting to change how `public` etc is formatted:

```
AccessModifierOffset: 0
```
