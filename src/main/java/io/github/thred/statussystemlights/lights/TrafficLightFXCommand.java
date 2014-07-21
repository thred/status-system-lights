package io.github.thred.statussystemlights.lights;

import io.github.thred.statussystemlights.serial.SerialInterface;
import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.ArgumentException;
import io.github.thred.tinyconsole.Process;
import io.github.thred.tinyconsole.util.Arguments;
import io.github.thred.tinyconsole.util.Utils;

public class TrafficLightFXCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public TrafficLightFXCommand(SerialInterface serialInterface)
    {
        super("trafficLightFX", "tlfx");

        this.serialInterface = serialInterface;
    }

    @Override
    public String getFormat()
    {
        //        sleep 1000
        //        tlfx rain
        //        sleep 5000
        //        tlfx steam
        //        sleep 5000
        //        tlfx fall
        //        sleep 5000
        //        tlfx raise
        //        sleep 5000
        //        tlfx row r
        //        sleep 2500
        //        tlfx row y
        //        sleep 2500
        //        tlfx row g
        //        sleep 2500
        //        tlfx blink
        //        sleep 5000
        //        tlfx random
        //        sleep 5000
        //        tl 0
        //        sleep 500
        //        tl 1
        //        sleep 500
        //        tl 2
        //        sleep 500
        //        tl 3

        return "(rain [<DURATION>]|steam [<DURATION>]|fall [<DURATION>]|raise [<DURATION>]|row (r|y|g) [<DURATION>]|blink [<DURATION>]|random)";
    }

    @Override
    public String getDescription()
    {
        return "Does some traffic light FX.";
    }

    @Override
    public int getOrdinal()
    {
        return 16;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        String name = process.args.consumeString();

        if (name == null)
        {
            throw new ArgumentException("Missing name.");
        }

        name = name.toLowerCase();

        String command = null;

        if ("rain".equals(name))
        {
            command = TrafficLightFX.rain(Utils.ensure(process.args.consumeLong(), 120l).intValue());
        }
        else if ("steam".equals(name))
        {
            command = TrafficLightFX.steam(Utils.ensure(process.args.consumeLong(), 120l).intValue());
        }
        else if ("fall".equals(name))
        {
            command = TrafficLightFX.fall(Utils.ensure(process.args.consumeLong(), 40l).intValue());
        }
        else if ("raise".equals(name))
        {
            command = TrafficLightFX.raise(Utils.ensure(process.args.consumeLong(), 40l).intValue());
        }
        else if ("row".equals(name))
        {
            command =
                TrafficLightFX
                    .row(consumeColor(process.args), Utils.ensure(process.args.consumeLong(), 40l).intValue());
        }
        else if ("blink".equals(name))
        {
            command = TrafficLightFX.blink(Utils.ensure(process.args.consumeLong(), 70l).intValue());
        }
        else if ("random".equals(name))
        {
            command = TrafficLightFX.random();
        }
        else
        {
            throw new ArgumentException("Invalid name: " + name);
        }

        process.out.println("Sending " + command);

        serialInterface.send(command);
        serialInterface.flush();

        return 0;
    }

    protected int consumeColor(Arguments args)
    {
        String color = args.consumeString();

        if (color == null)
        {
            throw new ArgumentException("Color is missing");
        }

        color = color.toLowerCase();

        if ("r".equals(color))
        {
            return TrafficLightCommandBuilder.RED;
        }
        else if ("y".equals(color))
        {
            return TrafficLightCommandBuilder.YELLOW;
        }
        else if ("g".equals(color))
        {
            return TrafficLightCommandBuilder.GREEN;
        }
        else
        {
            throw new ArgumentException("Invalid color: " + color);
        }
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
