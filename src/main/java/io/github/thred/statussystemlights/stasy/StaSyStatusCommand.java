package io.github.thred.statussystemlights.stasy;

import java.util.Iterator;
import java.util.Map.Entry;

import io.github.thred.statussystemlights.lights.Color;
import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.ArgumentException;
import io.github.thred.tinyconsole.Process;

public class StaSyStatusCommand extends AbstractCommand
{

    private final StaSyStatus staSyStatus;

    public StaSyStatusCommand(StaSyStatus staSyStatus)
    {
        super("stasystatus", "stasy");

        this.staSyStatus = staSyStatus;
    }

    @Override
    public String getFormat()
    {
        return "[update url] [list]";
    }

    @Override
    public String getDescription()
    {
        return "Grabs the status from StaSy (/rest interface)";
    }

    @Override
    public int getOrdinal()
    {
        return 8000;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        String command = process.args.consumeString();

        if (("update".equalsIgnoreCase(command)) || "u".equalsIgnoreCase(command))
        {
            String url = process.args.consumeString();

            if (url == null)
            {
                throw new ArgumentException("URL missing");
            }

            boolean success = staSyStatus.update(url);
            
            if (success) {
                process.out.println("Update successful.");
                
                return 0;
            }
            
            process.err.println("Update failed.");
            
            return -1;
        }

        if ((command == null) || ("list".equalsIgnoreCase(command)) || ("l".equalsIgnoreCase(command)))
        {
            process.out.println("StaSy Status:");
            process.out.println();

            Iterator<Entry<String, Color>> iterator = staSyStatus.iterator();

            if (!iterator.hasNext())
            {
                process.out.println("No states defined.");
            }

            while (iterator.hasNext())
            {
                Entry<String, Color> entry = iterator.next();

                process.out.println(String.format("%40s: %s", entry.getKey(), entry.getValue()));
            }

            return 0;
        }

        throw new ArgumentException("Invalid command: " + command);
    }

    @Override
    protected String getHelpDescription()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
