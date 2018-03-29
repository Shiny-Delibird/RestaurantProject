IMPORTANT NOTE: When running the project, ensure that the root folder opened in IntelliJ is the "restaurantProject" folder,
                    in the phase2 directory
                Otherwise, the program may crash when trying to open its files as they are all referenced via that root
                If the program crashes with a file not found error, try ensuring the root folder opened is "restaurantProject"

==============GUI=================
The GUI is opened by running the main() method in src\Interface\GUIInitializer.java

It opens the Manager's GUI to start with, and because this is not yet running on tablets,
   servers and cooks can be opened from there

Each Cook and Server has their own window, representing what they would see personal tablet

===========LOGGING================
The log is stored in configs/log.txt

This is also where payments made today can be seen

==============MENU================
The menu is generated when the program runs from the file called menu.txt

It is in the format:
price | Name of food | ingredients needed to make food

The ingredients needed to make a food are written as "amount x ingredientName"

So for example, a sample menu item might look like:
10 | Spaghetti | 1 x tomato, 1 x noodles, 1 x ground beef

Also, if the menu has a food which is not currently in the inventory,
it is added to the inventory file with am amount of 0 to stop the program from crashing

=============MINIMUMS===========
The minimums file determines what the minimum amount of each ingredient should be before reordering

The file is structured in the format ingredient | quantity

If the file minimums.txt does not exist, it generates the file with default minimums of 10 for each item in the
inventory (this is also the case with an incomplete minimums file with missing entries)

NOTE: the minimums can be changed directly by altering the minimums.txt file. PLEASE ONLY INSERT NON-NEGATIVE INTEGERS

================REORDERING============
Requests for more ingredients are auto-generated and stored in the requests.txt file as ingredients are used.

Each line is structured in the format ingredient x quantity. WARNING: the default quantity ordered is 20 and it is up to
the user to change it if the default is insufficient.

When a new shipment of ingredients is processed, the requests.txt file is wiped and any ingredients still under the
minimum threshold are then added back to requests.txt