package io.github.thred.statussystemlights.serial;

import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.Process;

public class FlushCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public FlushCommand(SerialInterface serialInterface)
    {
        super("flush", "f");

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
        return "Flushes the cached data.";
    }

    @Override
    public int getOrdinal()
    {
        return 13;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        int count = serialInterface.flush();

        process.out.printf("Flushed %d bytes.\n", count);

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
