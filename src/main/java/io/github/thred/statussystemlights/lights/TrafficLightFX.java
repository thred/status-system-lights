package io.github.thred.statussystemlights.lights;

import static io.github.thred.statussystemlights.lights.TrafficLightCommandBuilder.*;

public class TrafficLightFX
{

    public static String rain(int duration)
    {
        int delay = Math.max(duration / 12, 1);
        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 12; i++)
        {
            builder.set(i + 2, i * delay, delay, 12 * delay);
        }

        return builder.toString();
    }

    public static String steam(int duration)
    {
        int delay = Math.max(duration / 12, 1);
        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 12; i++)
        {
            builder.set(13 - i, i * delay, delay, 12 * delay);
        }

        return builder.toString();
    }

    public static String fall(int duration)
    {
        int delay = Math.max(duration / 4, 1);

        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 3; i++)
        {
            builder.set(i + 2, i * delay, delay, 4 * delay);
            builder.set(i + 2 + 3, i * delay, delay, 4 * delay);
            builder.set(i + 2 + 6, i * delay, delay, 4 * delay);
            builder.set(i + 2 + 9, i * delay, delay, 4 * delay);
        }

        return builder.toString();
    }

    public static String raise(int duration)
    {
        int delay = Math.max(duration / 4, 1);

        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 3; i++)
        {
            builder.set(4 - i, i * delay, delay, 4 * delay);
            builder.set((4 - i) + 3, i * delay, delay, 4 * delay);
            builder.set((4 - i) + 6, i * delay, delay, 4 * delay);
            builder.set((4 - i) + 9, i * delay, delay, 4 * delay);
        }

        return builder.toString();
    }

    public static String row(int color, int duration)
    {
        int delay = Math.max(duration / 4, 1);
        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 4; i++)
        {
            builder.set(3 - i, RED, 0, 0, 0);
            builder.set(3 - i, YELLOW, 0, 0, 0);
            builder.set(3 - i, GREEN, 0, 0, 0);
            builder.set(3 - i, color, i * delay, delay, 4 * delay);
        }

        return builder.toString();
    }

    public static String blink(int duration)
    {
        int delay = Math.max(duration / 2, 1);
        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 12; i++)
        {
            builder.set(i + 2, 0, delay, 2 * delay);
        }

        return builder.toString();
    }

    public static String random()
    {
        TrafficLightCommandBuilder builder = new TrafficLightCommandBuilder();

        for (int i = 0; i < 12; i++)
        {
            int repeat = (int) (Math.random() * 50) + 10;
            int swtch = (int) (Math.random() * repeat);

            builder.set(i + 2, (int) (Math.random() * repeat), swtch, repeat);
        }

        return builder.toString();
    }

}
