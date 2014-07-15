package io.github.thred.statussystemlights;

import io.github.thred.statussystemlights.console.AbstractCommand;
import io.github.thred.statussystemlights.console.ConsoleAdapter;
import io.github.thred.statussystemlights.console.Process;

public class CloseCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public CloseCommand(ConsoleAdapter consoleAdapter, SerialInterface serialInterface)
    {
        super(consoleAdapter, "close", "c");

        this.serialInterface = serialInterface;
    }

    @Override
    public String getFormat()
    {
        return "";
    }

    @Override
    public String getDescription()
    {
        return "Closes a serial port";
    }

    @Override
    public int getOrdinal()
    {
        return 13;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        serialInterface.close();

        process.out.println("Closed.");

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
