package io.github.thred.statussystemlights;

import io.github.thred.statussystemlights.console.AbstractCommand;
import io.github.thred.statussystemlights.console.ConsoleAdapter;
import io.github.thred.statussystemlights.console.Process;

public class SendCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public SendCommand(ConsoleAdapter consoleAdapter, SerialInterface serialInterface)
    {
        super(consoleAdapter, "send", "s");

        this.serialInterface = serialInterface;
    }

    @Override
    public String getFormat()
    {
        return "{value}";
    }

    @Override
    public String getDescription()
    {
        return "Sends integer values.";
    }

    @Override
    public int getOrdinal()
    {
        return 12;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        Long value = process.args.consumeLong();

        while (value != null)
        {
            serialInterface.sendInt(value.intValue());

            value = process.args.consumeLong();
        }

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
