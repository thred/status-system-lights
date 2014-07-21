package io.github.thred.statussystemlights.serial;

import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.Process;

public class CloseCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public CloseCommand(SerialInterface serialInterface)
    {
        super("close", "c");

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
        return 20;
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
