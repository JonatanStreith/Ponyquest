package jonst;

public class Notes {


    //IMPORTANT! REVISE A LOT OF THINGS so objects use "ID" instead of names, in case we want to use similar names for things.
    //No, that's bad.



    //Keep a dictionary of bools to track which ponies you're talked to, so they say something special the first time.


    //      Add "spells" as an new class, make list. Can use "cast" command. Trixie may know some spells, and learn others.



    //Remember: GameFlags is a hashmap, get values through GameFlags.get("keyword");


    //IMPORTANT! Have a check of all objects to ensure that their "location" is valid, otherwise dump them into a special cleanup location.


    //In order to allow a better system for organizing, have everything use IDs instead. When trying to interact with things (looking, going to, talking to, etc),
    //First check if that thing is within range. I.e. if you type "Go to kitchen", check if any possible exits match that description; otherwise, refuse to go.



    //It's possible to have creatures move around based on time parameters! Just have the location dependent on a time parameter; when asking for someone's location, just provide the time
    //and return a certain location matching that one! Could use a hashmap with time marks as keys!

    //Note: It is permissible to have secondary inputs. If a command needs more info, you can prompt for more lines. Good idea!


    //have returnFullName return list of all things matching the shortname. If multiple returns, ask "which one?" Since it returns all names...
    //Example: (assume Celestia and Luna are in the same room) talk to princess -> "Which princess? Princess Celestia or Princess Luna?"


    //Definitely make a unique ID system.

    //Todo: Make function that checks attributes for inconsistencies and contradictions - i.e. "open" and "closed". Could be done with simple rules.

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

//  Have text-content stored somewhere else. Load it with some function.
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


 */

//  Set ResponseScript so it stores an arraylist of strings with each key - that way, we can have multiple scripts with each key!

//  TODO: Convert to ID for choosing where to go with "go to"! Probably a good idea to do so for all things.

    //May just want to have one class that handles spells. String list of available spells that dictate which you can actually cast.

//  Rebuild enter() so it only runs to defaultEnter if it's no-args. Otherwise, it has an option to take SObjects and similar. So you can "enter magic mirror".

    //Subclasses of StationaryObject: Vehicle, Producer. Vehicles can be used to travel. Producers can be used to produce an endless supply of things. (I.e. apple trees)

//  RunRespnseScript now returns boolean! So you can check that anything happened.

//  Command activate: Since that seems to vary from thing to thing, just have it connected to a responsescript.

/*

Figure out some way to have overriding descriptions and additive descriptions.

"Tablet of Transmigration".

Figure out how to "cast" from items in your inventory. List of legit nouns needs to be updated accordingly.

Set up some kind of "owned" script, so if you try to pick up an item that is owned by someone who is present, they interfere. Could work!

*/

//Set up nullchecks in jsonbuilder!

//Make a script that lets you move all followers. And one that lets you change their races.

    //May need "DefaultRace" and "DefaultLocation" and similar, if the game needs to restore things to normal. I.e. if a follower moves back through the mirror.

    //Spell: "Summon friends".
    //Creatures could have a "friend list" tracking which ones are their true friends. Because FiM.

    //Fill command: "fill bucket with apples".


    //Dialog trees!

    /*

    Basically, have a list of Dialog entries that lead into each other. (Could have scripts attached.) May need its own class.
    When a conversation is initiated, look for the Creature's initDialog ref: this is what will load when you talk to them.
    Display the line, then list response choices. When player makes choice, load that dialog. Repeat until you hit something that looks like an end.
    (Possibly have an End Conversation option, just in case. And make null checks just in case a line doesn't load.)

    You could also have certain lines change their initDialog ref, so when you come back they talk differently. Like if you insulted them, they'll be mad.

    */


    //Figure out a way to prevent wearing multiple identicals (i.e. capes, for instance). Wearing a cape could give you the attribute "wears cape",
    //but it needs to check what kind of item it is, so... tricky. Should work fairly flexibly, with a try.
}
