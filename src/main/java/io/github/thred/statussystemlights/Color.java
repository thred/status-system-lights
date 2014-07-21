package io.github.thred.statussystemlights;

public enum Color
{

    NONE,

    RED,

    YELLOW,

    GREEN;

    public static Color parse(String s)
    {
        for (Color color : values())
        {
            if (color.name().equalsIgnoreCase(s))
            {
                return color;
            }
        }

        return NONE;
    }
}
