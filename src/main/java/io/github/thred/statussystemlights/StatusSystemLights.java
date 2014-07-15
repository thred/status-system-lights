package io.github.thred.statussystemlights;

import io.github.thred.statussystemlights.console.Console;
import io.github.thred.statussystemlights.service.GlobalServiceResolver;


public class StatusSystemLights
{
    public static void main(String[] args)
    {
        GlobalServiceResolver serviceResolver = GlobalServiceResolver.INSTANCE;

        Console console = Console.INSTANCE;

        console.initialize(serviceResolver);
        console.start(false);
        
        System.out.println("Type: help");
    }
    
}
