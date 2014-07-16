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
        StringBuilder builder = new StringBuilder();

        while (!process.args.isEmpty())
        {
            builder.append(process.args.consumeString());

            if (!process.args.isEmpty())
            {
                builder.append(" ");
            }
        }

        serialInterface.send(builder.toString());

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
