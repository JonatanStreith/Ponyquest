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



    //Every creature has their own inventory! This cannot go wrong.

        //Move itemstorage to GenericObject so all things can hold items!

    //IDEA! Make a big HashMap that can parse inputted topics against Real Topics, i.e. AJ -> Applejack, etc. That way, we don't have to
    //give each creature five identical entries for the same, differently phrased topic.




    //Definitely make a unique ID system.

    //Find a way to look at/interact with things inside containers/carried.


    //Attributes: ClosedContainer = kan inte "se" innehållet.

    //Todo: Make function that checks attributes for inconsistencies and contradictions - i.e. "open" and "closed". Could be done with simple rules.

    //Add "(Carried by <>)" when looking at contained things!

    //Oh yeah! Can probably put in limitations on what you can put into containers based on size, using Attributes! If it has "huge container" it can house "huge" items, or have a number, or something...


//    Build in an internal clock! Every turn advances one minute. Use to track how creatures react to things at certain times. Also, have a "schedule" that makes creatures move to other locations at relevant times (i.e. at 3:00 Rarity and Spike go spelunking for jewels.)
//
//    Somehow make dialog dependent on location?
//
//    Have "failed to place" objects put in the Mail office lost & found room.
//
//
//
//    Have Exit as a GenericObject?
//

//
//    "Think about" command.
//
//    Add a tutorial - basically a multi-page guide to how to use commands. Can be displayed with "tutorial" or something.
//
//    Magic interface! Basically a command that leads to a continued loop (until you break it) where you can do in-depth magic commands. Possibly set up a magicParser() method.
//
//    Add a "text" field to items. the json doesn't need entries for things that aren't "readable" (attribute), so they'll become null. If the item isn't "readable", trying to read it will just return a "There's nothing to read."
//
//    Train station! The train is a Stationary, use or "board" it will check if you have a ticket, then take you to that place. Better: boarding will list which tickets you have, and you have to choose destination! Then go there! Yeah, that's cool. (Train/ticket pony: "All Aboard").
//
//    "Discard" command. ASK IF PLAYER WANTS TO DO IT. Yeah, hardcore oldschool.
//

/*
On the subject of 'use'. This is where scripts come into play.
Every object has a hashmap of scripts - a key and a string. When a more complex matter needs to be done, we can call:

{ obj.runScript(scriptkey);   }

Which then retrieves a commandline from its map of scripts and runs it through a scriptparser to do things.

For instance, if Trixie goes through the mirror to the human world, we might call the script "go to human world"; "transform into human"; etc.


Responsive actions! Have commands where a certain thing is interacted with send a respondScript(command); to the thing; If there's a script
with that key, they will run that script!

Hugging!
 */

//  Set ResponseScript so it stores an arraylist of strings with each key - that way, we can have multiple scripts with each key!

//  TODO: Convert to ID for choosing where to go with "go to"! Probably a good idea to do so for all things.

    //May just want to have one class that handles spells. String list of available spells that dictate which you can actually cast.

//  Have text-content stored somewhere else. Load it with some function.

//  Rebuild enter() so it only runs to defaultEnter if it's no-args. Otherwise, it has an option to take SObjects and similar. So you can "enter magic mirror".

    //Subclasses of StationaryObject: Vehicle, Producer. Vehicles can be used to travel. Producers can be used to produce an endless supply of things. (I.e. apple trees)

//  RunRespnseScript now returns boolean! o you can check that anything happened.

//  Command activate: Since that seems to vary from thing to thing, just have it connected to a responsescript.

/*

A way to make other NPCs do actions! Like "Applejack, go to Sugarcube Corner".

If the commandline begins with a legitimate noun, parse it differently. Find who you're instructing, set them as "actor",
then run the command as normal. Though if you command them, responses will need to be different. So send them to a whole new
set of command methods, just to keep things orderly. This could be big!

*/



}
