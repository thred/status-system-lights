package io.github.thred.statussystemlights.lights;

import io.github.thred.statussystemlights.serial.SerialInterface;
import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.ArgumentException;
import io.github.thred.tinyconsole.Process;

public class TrafficLightCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public TrafficLightCommand(SerialInterface serialInterface)
    {
        super("trafficLight", "tl");

        this.serialInterface = serialInterface;
    }

    @Override
    public String getFormat()
    {
        return "<ID> [-b] [r|y|g]";
    }

    @Override
    public String getDescription()
    {
        return "Updates one traffic light.";
    }

    @Override
    public int getOrdinal()
    {
        return 15;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        Long idValue = process.args.consumeLong();

        if (idValue == null)
        {
            throw new ArgumentException("<ID> is missing");
        }

        int id = idValue.intValue();

        if ((id < 0) || (id > 3))
        {
            throw new ArgumentException("Invalid <ID> (0 - 3)");
        }

        boolean blink = process.args.consumeFlag("-b");

        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        builder.redOff(id).yellowOff(id).greenOff(id);

        if (process.args.consumeFlag("r"))
        {
            if (blink)
            {
                builder.redBlink(id);
            }
            else
            {
                builder.redOn(id);
            }
        }

        if (process.args.consumeFlag("g"))
        {
            if (blink)
            {
                builder.greenBlink(id);
            }
            else
            {
                builder.greenOn(id);
            }
        }

        if (process.args.consumeFlag("y"))
        {
            if (blink)
            {
                builder.yellowBlink(id);
            }
            else
            {
                builder.yellowOn(id);
            }
        }

        String arg = process.args.consumeString();

        if (arg != null)
        {
            throw new ArgumentException("Invalid argument: " + arg);
        }

        process.out.println("Sending " + builder.toString());

        serialInterface.send(builder.toString());
        serialInterface.flush();

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return "Updates one traffic light. If no color is given, the traffic light will be turned off.\n\n" //
            + "<ID>     The id of the traffic light (0 - 3)\n" //
            + "[-b]     Blinking\n" //
            + "[r|y|g]  The color\n";
    }

}
