//
// Name:    Murillo, Clarissa
// Project: 5
// Due:     5/12/23
// Course:  cs-2400-03-sp23
//
// Description:
//           Implementation of the graph ADT for a practical application. Input: two files- one for airport information
//           and the other for connection data
//


// to say theres no path, you can set the edge to zero since we dont have a removeEdge method
// check if the weight is zero

import java.util.*;
import java.io.*;

public class AirportApp
{
    public static void main(String[] args)
    {
        // do I need to use the minheap?
        GraphInterface<String> routes = new DirectedGraph<>();
        DictionaryInterface<String, String> airport = new HashedDictionary<>();
        String[] data = new String[0];
        System.out.println("Airports v0.1 by C.Murillo\n");

        try
        {
            Scanner reader = new Scanner(new File("airports.csv"));
            while (reader.hasNextLine())
            {
                String row = reader.nextLine();
                data = row.split(",");
                routes.addVertex(data[0].trim()); // is this what he wrote the code for(on notepad)?
                airport.add(data[0].trim(), data[1].trim()); //WILL THIS BE CHANGED TO ADD???
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Scanner reader = new Scanner(new File("distances.csv"));
            while (reader.hasNextLine())
            {
                String row = reader.nextLine();
                data = row.split(",");
                routes.addEdge(data[0].trim(), data[1].trim(), Double.parseDouble(data[2]));
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        // the commands work but why doesn't it take in files??? FIX
        // reads in data from the user
        Scanner input = new Scanner(System.in);
        boolean done = false;
        while (!done)
        {
            System.out.print("Command? ");
            String userInput = input.nextLine();
            switch (userInput) // different menu options the user can choose from
            {
                case "Q": // gives the airport information
                    System.out.print("Airport code: ");
                    userInput = input.nextLine();
                    if (airport.contains(userInput))
                        System.out.println(airport.getValue(userInput));
                    else
                        System.out.println("Airport code doesn't exist.");
                    break;
                case "D": // finds the minimum distance between two airports
                    System.out.print("Airport codes: ");
                    userInput = input.nextLine();
                    data = userInput.split(" ");
                    if (data.length == 2)
                    {
                        String begin = data[0];
                        String end = data[1];
                        if (airport.contains(begin) && airport.contains(end))
                        {
                            try
                            {
                                StackInterface<String> path = new LinkedStack<>();
                                int distance = (int)routes.getCheapestPath(begin, end, path); // implement the getCheapestPath method here
                                System.out.println("The minimum distance between " + airport.getValue(begin) + " and " + airport.getValue(end) + " is " + distance + " through the route:");
                                while (!path.isEmpty())
                                    System.out.println(airport.getValue(path.pop()));
                            }
                            catch (RuntimeException e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                        else
                            System.out.println("Airport code doesn't exist.");
                    }
                    else
                        System.out.println("Invalid input. Enter two airport codes.");
                    break;
                case "I": // fins the connection between two airport codes and distance
                    System.out.print("Airport codes and distance: ");
                    userInput = input.nextLine();
                    data = userInput.split(" ");
                    if (data.length == 3)
                    {
                        try
                        {
                            String begin = data[0];
                            String end = data[1];
                            double distance = Double.parseDouble(data[2]);
                            if (routes.hasEdge(begin, end))
                                System.out.println("Connection already exists.");
                            else {
                                if (routes.addEdge(begin, end, distance))
                                    // if I dont put (int) in front of distance, it returns the number as a double
                                    System.out.println("You have inserted a connection from " + airport.getValue(begin) + " to " + airport.getValue(end) + " with a distance of " + (int)distance + ".");
                                else
                                    System.out.println("Airport code/codes doesn't exist.");
                            }
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Invalid distance number.");
                        }
                    }
                    else
                        System.out.println("Invalid input. Enter two airport codes and a distance.");
                    break;
                case "R": // removes the existing connection by entering two airport codes
                    System.out.print("Airport codes: ");
                    userInput = input.nextLine();
                    data = userInput.split(" ");
                    if (data.length == 2)
                    {
                        String begin = data[0];
                        String end = data[1];
                        if (airport.contains(begin) && airport.contains(end))
                        {
                            if (routes.removeEdge(begin, end)) // changed this, REMOVE LATER
                                System.out.println("The connection from " + airport.getValue(begin) + " and " + airport.getValue(end) + " removed.");
                            else
                                System.out.println("Airports not connected.");
                        }
                        else
                        {
                            System.out.println("Airport code/codes doesn't exist.");
                        }
                    }
                    else
                        System.out.println("Invalid input. Enter two airport codes.");
                    break;
                case "H": // displays the menu
                    System.out.println("Q Query the airport information by entering the airport code.");
                    System.out.println("D Find the minimum distance between two airports.");
                    System.out.println("I Insert a connection by entering two airport codes and distance.");
                    System.out.println("R Remove an existing connection by entering two airport codes.");
                    System.out.println("H Display this message.");
                    System.out.println("E Exit.");
                    break;
                case "E": //exits the program
                    done = true;
                    System.out.println("Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}
