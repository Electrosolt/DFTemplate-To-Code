# DF Template to Code
This is a simple utility that will allow you to generate human readable pseudocode from a bunch of DiamondFire code templates.

## Usage
### Building yourself
1. Clone the repository.
2. Obtain the templates you want to convert.
3. Put them into a shulker box, then break it to get it as an item.
4. Hold the shulker and type `/i nbt`.
5. Click on "Click to copy unformatted NBT".
6. Paste it as a line in the "templatedata.txt" file (make sure to remove the placeholder text).
7. When your shulkers are ready in the file, simply run the `main` function in `TemplateToCode.java`.
8. This will generate a bunch of `.df` files in a new directory, `gendir`.
### Using a prebuilt jar
1. Download the jar from the releases page or through other means.
2. Obtain the templates you want to convert.
3. Put them into a shulker box, then break it to get it as an item.
4. Hold the shulker and type `/i nbt`..
5. Click on "Click to copy unformatted NBT".
6. Paste it in a new file named "templatedata.txt" located in the same directory as the jar.
7. Run the jar file, either by double-clicking or running `java -jar <jarname>.jar`.
8. This will generate a bunch of `.df` files in a new directory, `gendir`.

## Directories/Categories
This utility also has support for function categories/directories.
To place a function or process into a folder, put in a string with the folder's name into the Function/Process chest. For instance, a string "hello" will put the Function in a folder called "hello".

There is also support for subdirectories. The string "hello/there" will make a folder "there" inside a folder "hello" and put the file there.

Make sure that the string is the LAST thing in the code chest (but before tags), otherwise it will interfere with icons and parameters.