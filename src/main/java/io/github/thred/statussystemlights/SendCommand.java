package io.github.thred.statussystemlights;

import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.Process;

public class SendCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public SendCommand(SerialInterface serialInterface)
    {
        super("send", "s");

        this.serialInterface = serialInterface;
    }

    @Override
    public String getFormat()
    {
        return "[-n] [-f] {value}";
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
        boolean newLine = process.args.consumeFlag("-n");
        boolean flush = process.args.consumeFlag("-n");

        while (!process.args.isEmpty())
        {
            builder.append(process.args.consumeString());
            builder.append(" ");
        }

        int count = serialInterface.send(builder.toString());

        if (newLine)
        {
            serialInterface.send((byte) '\n');
        }

        if (flush)
        {
            count = serialInterface.flush();

            process.out.printf("Flushed %d bytes.\n", count);
        }
        else
        {
            process.out.printf("Buffered %d bytes.\n", count);
        }

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return "Sends integer values.\n\n" + "-n    Terminates the string with \\n\n" + "-f    Flushes the output";
    }

}
