package jonst;

public class Notes {


    //IMPORTANT! REVISE A LOT OF THINGS so objects use "ID" instead of names, in case we want to use similar names for things.
    //No, that's bad.

    //Revise "Legitimate Noun" list so it's only present things?



    //I probably don't need to create world objects (locations, creatures, etc) separately and then add  them;
    //just create them as they're added!



    //Save function!

    //Store world data in separate file, so you can load and save?


    //Keep a dictionary of bools to track which ponies you're talked to, so they say something special the first time.


    //On moving around: Since locations are "proper nouns", the parser can take a location. If I write "Go to Sweet Apple Acres", it can see that SAA is a proper noun, check if SAA is adjacent, and go there if possible.
    //Should also be able to "Go west".


    //      Add "spells" as an new class, make list. Can use "cast" command. Trixie may know some spells, and learn others.

    //      Make sure that ALL commands that change things in the world, call on methods in the world object so changes happen THERE.


    //https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
    //https://www.w3schools.com/java/java_files.asp

    //Restore parser to its more complex form, I needed that.

    //Remember: GameFlags is a hashmap, get values through GameFlags.get("keyword");

    //Check so that random dialog is actually random!
}
