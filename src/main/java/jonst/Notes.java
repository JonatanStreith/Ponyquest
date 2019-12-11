package jonst;

public class Notes {


    //IMPORTANT! REVISE A LOT OF THINGS so objects use "ID" instead of names, in case we want to use similar names for things.
    //No, that's bad.



    //Keep a dictionary of bools to track which ponies you're talked to, so they say something special the first time.


    //      Add "spells" as an new class, make list. Can use "cast" command. Trixie may know some spells, and learn others.


    //https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
    //https://www.w3schools.com/java/java_files.asp

    //Remember: GameFlags is a hashmap, get values through GameFlags.get("keyword");

    //When "using" something, call a method that takes relevant data and runs scripts?

    //IMPORTANT! Have a check of all objects to ensure that their "location" is valid, otherwise dump them into a special cleanup location.


    //In order to allow a better system for organizing, have everything use IDs instead. When trying to interact with things (looking, going to, talking to, etc),
    //First check if that thing is within range. I.e. if you type "Go to kitchen", check if any possible exits match that description; otherwise, refuse to go.


    //Add "useeffect" to objects? Line written when you try to use something. Work out way to advance this to persistent effects.

    //It's possible to have creatures move around based on time parameters! Just have the location dependent on a time parameter; when asking for someone's location, just provide the time
    //and return a certain location matching that one! Could use a hashmap with time marks as keys!


    //Should have "caster" object (class) that handles spellcasting. Stores spells, maybe.

    //Note: It is permissible to have secondary inputs. If a command needs more info, you can prompt for more lines. Good idea!


    //have returnFullName return list of all things matching the shortname. If multiple returns, ask "which one?" Since it returns all names...
    //Example: (assume Celestia and Luna are in the same room) talk to princess -> "Which princess? Princess Celestia or Princess Luna?"

    //Todo: MUST MAKE SURE PLAYER CAN ONLY, ONLY *ONLY* INTERACT WITH THINGS AT THE CURRENT LOCATION! Will ensure that you don't accidentally grab something that is somewhere else.
    //Could also allow for duplicates. If there are five apples at a location and you pick up one, who cares which one it is?

    //Items should have List<String> Qualities, which tracks what the item can be used for: wearable, activation, ligh source, food, etc.

    //Container object: Extends Item! Can hold other items. NOT ITSELF!

    //Add "gender" to Creature so we can use better pronouns.

    //Every creature has their own inventory! This cannot go wrong.

}
