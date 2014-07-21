package io.github.thred.statussystemlights.lights;

import java.util.ArrayList;
import java.util.List;

public class TrafficLightCommandBuilder
{

    public static final int GREEN = 0;
    public static final int YELLOW = 1;
    public static final int RED = 2;

    private final List<String> commands = new ArrayList<>();

    public TrafficLightCommandBuilder()
    {
        super();
    }

    public TrafficLightCommandBuilder greenOn(int trafficLightId)
    {
        return on(trafficLightId, GREEN);
    }

    public TrafficLightCommandBuilder greenOff(int trafficLightId)
    {
        return off(trafficLightId, GREEN);
    }

    public TrafficLightCommandBuilder greenBlink(int trafficLightId)
    {
        return blink(trafficLightId, GREEN);
    }

    public TrafficLightCommandBuilder yellowOn(int trafficLightId)
    {
        return on(trafficLightId, YELLOW);
    }

    public TrafficLightCommandBuilder yellowOff(int trafficLightId)
    {
        return off(trafficLightId, YELLOW);
    }

    public TrafficLightCommandBuilder yellowBlink(int trafficLightId)
    {
        return blink(trafficLightId, YELLOW);
    }

    public TrafficLightCommandBuilder redOn(int trafficLightId)
    {
        return on(trafficLightId, RED);
    }

    public TrafficLightCommandBuilder redOff(int trafficLightId)
    {
        return off(trafficLightId, RED);
    }

    public TrafficLightCommandBuilder redBlink(int trafficLightId)
    {
        return blink(trafficLightId, RED);
    }

    public TrafficLightCommandBuilder on(int trafficLightId, int color)
    {
        return set(trafficLightId, color, 1, 0, 0);
    }

    public TrafficLightCommandBuilder off(int trafficLightId, int color)
    {
        return set(trafficLightId, color, 0, 0, 0);
    }

    public TrafficLightCommandBuilder blink(int trafficLightId, int color)
    {
        return set(trafficLightId, color, 0, 35, 70);
    }

    public TrafficLightCommandBuilder set(int trafficLightId, int color, int state, int swtch, int repeat)
    {
        return set(toLedId(trafficLightId, color), state, swtch, repeat);
    }

    public TrafficLightCommandBuilder set(int ledId, int state, int swtch, int repeat)
    {
        commands.add(String.format("u %d %d %d %d", ledId, state, swtch, repeat));

        return this;
    }

    protected int toLedId(int trafficLightId, int color)
    {
        return (trafficLightId * 3) + 2 + color;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();

        for (String command : commands)
        {
            result.append(command).append(" ");
        }

        return result.toString();
    }
}
