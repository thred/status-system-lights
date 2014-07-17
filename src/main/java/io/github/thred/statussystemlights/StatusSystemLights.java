package io.github.thred.statussystemlights;

import io.github.thred.tinyconsole.TinyConsole;

public class StatusSystemLights
{
    public static void main(String[] args)
    {
        initConsole();

        System.out.println("Type: help");
    }

    private static void initConsole()
    {
        TinyConsole.start(false);
    }

}
