This is a simple utility that will allow you to generate human readable pseudocode from a bunch of DiamondFire code templates.
To use the utility:
1. Make sure the required dependency is installed (org.json) in some way.
2. Obtain the templates you want to convert
3. Put them into a shulker box, then break it to get it as an item
4. Hold the shulker and type `/i nbt`.
5. Click the "Copy NBT" text.
6. Paste it as a line in "templatedata.txt" (make sure to remove the placeholder text).
7. When your shulkers are ready in the file, simply run the `main` function in `TemplateToCode.java`.
8. This will generate a bunch of `.df` files in a new directory, `gendir`.
9. That's it.
