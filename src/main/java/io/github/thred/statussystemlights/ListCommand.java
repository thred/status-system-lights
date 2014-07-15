package io.github.thred.statussystemlights;

import io.github.thred.statussystemlights.console.AbstractCommand;
import io.github.thred.statussystemlights.console.ConsoleAdapter;
import io.github.thred.statussystemlights.console.Process;

import java.util.List;

public class ListCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public ListCommand(ConsoleAdapter consoleAdapter, SerialInterface serialInterface)
    {
        super(consoleAdapter, "list", "l");

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
        return "Lists a possible ports.";
    }

    @Override
    public int getOrdinal()
    {
        return 10;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        List<String> list = serialInterface.list();

        if (list.size() == 0)
        {
            process.out.println("No ports found.");

            return 0;
        }

        for (String entry : list)
        {
            process.out.printf("\t%s\n", entry);
        }

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
