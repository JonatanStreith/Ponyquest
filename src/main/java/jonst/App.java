package jonst;

import java.io.Console;

/**
 * Hello world!
 *
 */
public class App 
{
    static void Main(String[] args)
    {

        //create locations, creatures, objects and items

        String gamePath = @"..\..\..\..\Textbased game\Textbased game";


        String defaultWorld = $@"{gamePath}\DefaultWorld";
        String reply;
        bool choiceMade = false;
        String filePath = defaultWorld;
        Console.WriteLine("Welcome to the game! Do you want to start a (N)ew game, or (L)oad a previous save?");  //Maybe list saves?

        do
        {
            reply = Console.ReadKey(true).KeyChar.ToString().ToLower();


            if (reply == "n")
            {
                filePath = defaultWorld;
                choiceMade = true;
                RollIntro();

            }

            else if (reply == "l")
            {
                Console.WriteLine("Available saves:");
                foreach (String dir in Directory.EnumerateDirectories($@"{gamePath}\Saves"))
                { Console.WriteLine(dir); } //List save files - clean up later

                Console.Write("Which save file do you want to load? ");

                String choice = Console.ReadLine();

                if (Directory.Exists($@"{gamePath}\Saves\{choice}"))
                {
                    filePath = $@"{gamePath}\Saves\{choice}";
                    Console.WriteLine($"Restoring from {choice}...");
                    choiceMade = true;

                }
                    else
                {
                    Console.WriteLine("That file doesn't exist.");
                }
                //Ask for one
            }
            else
            { Console.WriteLine("Sorry, what? Do you want to start a (N)ew game, or (L)oad a previous save?"); }

        } while (!choiceMade);


        World Equestria = new World(filePath);




        String input;
        String[] commandPhrase;         //A "Command Phrase" contains four elements: a command, a subject, a preposition, and a last argument. Example: "Throw", "rock", "at", "window".






        Console.WriteLine("Game begins!");

        Console.WriteLine("------------------------------------------------------------------------------------------------------------------------");






        Commands.LookAround(Equestria);




        while (true)                //Continously running play loop that parses instructions

        {


            Console.WriteLine();
            Console.Write("Please input command: ");
            input = Console.ReadLine().ToLower();
            commandPhrase = Parser(input, Equestria);


            Console.WriteLine(commandPhrase[0]);
            Console.WriteLine(commandPhrase[1]);
            Console.WriteLine(commandPhrase[2]);
            Console.WriteLine(commandPhrase[3]);


            RunCommand(commandPhrase, Equestria);


        }





    }


    public static void RunCommand(String[] command, World world)
    {

        switch (command[0])     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {
            case "brandish":
                //stuff
                break;

            case "cast":
                //stuff
                break;

            case "save":
                Commands.SaveGame(world);
                break;

            case "load":
                Commands.LoadGame(world);
                break;

            case "pick up":
                Commands.PickUp(command[1], world);
                break;

            case "drop":
                Commands.Drop(command[1], world);
                break;

            case "inventory":
                Commands.ShowInventory(world);
                break;

            case "nouns":
                Commands.ListNouns(world);
                break;

            case "help":
                Commands.Help();
                break;

            case "commands":
                Commands.ListCommands(world);
                break;


            case "quit":
                Commands.Quit();
                break;

            case "go to":
                Commands.GoTo(command[1], world);
                break;
            case "go":
                Commands.GoTo(command[1], world);
                break;


            case "talk to":
                Commands.TalkTo(command[1], world);
                break;

            case "look":
                Commands.LookAround(world);
                break;

            case "look around":
                Commands.LookAround(world);
                break;

            case "look at":
                Commands.LookAt(command[1], world);
                break;

            case "exits":
                Commands.GetExits(world);
                break;

            case "teleport":
                Commands.TeleportOther(command, world);
                break;

            case "teleport to":
                Commands.TeleportSelf(command, world);
                break;

            case "ask":
                Commands.Ask(command, world);
                break;

            default:
                Console.WriteLine("What do you mean?");
                break;
        }


    }






    public static String[] Parser(String command, World world)      //This runs a check on the input to ensure that it's a "proper" command
    {
        String[] cleanCommand = { "", "", "", "" };

        String[] remainder;


        foreach (String item in world.legitimateCommands)
        {
            if (command.StartsWith(item.ToLower()))
            {
                //Now separate the command

                remainder = command.Split(new String[] { item.ToLower() }, StringSplitOptions.None);

                cleanCommand[0] = item;

                if (remainder[1] != "")
                { command = remainder[1].Remove(0, 1); }



                break;
            }
        }

        foreach (String item in world.legitimateNouns)
        {
            if (command.StartsWith(item.ToLower()))
            {
                //Now separate the noun

                remainder = command.Split(new String[] { item.ToLower() }, StringSplitOptions.None);

                cleanCommand[1] = world.ReturnFullName(item);

                if (remainder[1] != "")
                { command = remainder[1].Remove(0, 1); }





                break;
            }
        }

        foreach (String item in world.legitimateConjunctions)
        {
            if (command.StartsWith(item.ToLower()))
            {
                //Now separate the conjunction

                remainder = command.Split(new String[] { item.ToLower() }, StringSplitOptions.None);

                cleanCommand[2] = item;

                if (remainder[1] != "")
                { command = remainder[1].Remove(0, 1); }


                break;
            }
        }


        cleanCommand[3] = command;      //Add the remainder


        return cleanCommand;
    }









    public static void RollIntro()
    {
        Console.Clear();
        Console.WriteLine("Once upon a time, in the magical land of Equestria...");
        Console.WriteLine();
        Console.WriteLine("A great and powerful magician went to Ponyville to awe and impress. That didn't end very well. Later, she returned for vengeance. That didn't quite work out either.");
        Console.WriteLine();
        Console.WriteLine("Then she returned again and made a great friend, and later helped save Equestria from the changeling menace, proving how all those neighsayers were foolish and wrong for doubting Trixie.");
        Console.WriteLine();
        Console.WriteLine("Now, Trixie has returned to Ponyville once again. What adventures await her this time?");

        Console.ReadKey();
        Console.Clear();

    }
}
