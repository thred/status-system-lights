package io.github.thred.statussystemlights.lights;

import io.github.thred.statussystemlights.serial.SerialInterface;
import io.github.thred.statussystemlights.stasy.StaSyStatus;
import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.ArgumentException;
import io.github.thred.tinyconsole.Process;

public class TrafficLightStatusCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;
    private final StaSyStatus staSyStatus;

    public TrafficLightStatusCommand(SerialInterface serialInterface, StaSyStatus staSyStatus)
    {
        super("trafficLightStatus", "tls");

        this.serialInterface = serialInterface;
        this.staSyStatus = staSyStatus;
    }

    @Override
    public String getFormat()
    {
        return "id name";
    }

    @Override
    public String getDescription()
    {
        return "Updates one traffic light.";
    }

    @Override
    public int getOrdinal()
    {
        return 8010;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        Long idValue = process.args.consumeLong();

        if (idValue == null)
        {
            throw new ArgumentException("Id is missing");
        }

        int id = idValue.intValue();

        if ((id < 0) || (id > 3))
        {
            throw new ArgumentException("Invalid id (0 - 3)");
        }

        String name = process.args.consumeString();
        
        if (name == null) {
            throw new ArgumentException("Name is missing");
        }
        
        Color color = staSyStatus.get(name);
        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        builder.redOff(id).yellowOff(id).greenOff(id);

        switch (color)
        {
            case NONE:
                builder.yellowBlink(id);
                break;
                
            case GREEN:
                builder.greenOn(id);
                break;
                
            case RED:
                builder.redOn(id);
                break;
                
            case YELLOW:
                builder.yellowOn(id);
                break;
        }
        
        process.out.println("Sending " + builder.toString());

        serialInterface.send(builder.toString());

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
