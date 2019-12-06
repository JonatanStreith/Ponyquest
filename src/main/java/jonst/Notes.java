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

    //When "using" something, call a method that takes relevant data and runs scripts?


    //https://howtodoinjava.com/library/json-simple-read-write-json-examples/


    //IMPORTANT! Have a check of all objects to ensure that their "location" is valid, otherwise dump them into a special cleanup location.


    //In order to allow a better system for organizing, have everything use IDs instead. When trying to interact with things (looking, going to, talking to, etc),
    //First check if that thing is within range. I.e. if you type "Go to kitchen", check if any possible exits match that description; otherwise, refuse to go.


    //Add "useeffect" to objects? Line written when you try to use something. Work out way to advance this to persistent effects.

    //It's possible to have creatures move around based on time parameters! Just have the location dependent on a time parameter; when asking for someone's location, just provide the time
    //and return a certain location matching that one! Could use a hashmap with time marks as keys!


    //Should have "caster" object (class) that handles spellcasting. Stores spells, maybe.

    //Note: It is permissible to have secondary inputs. If a command needs more info, you can prompt for more lines. Good idea!

    //Turn shortName into a list of potential aliases? Work out how to get around multiple responses. Perhaps method that checks all LOCAL objects to match the alias?
    //And reply "Which one?" in the case of multiple matches!

}
