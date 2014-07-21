package io.github.thred.statussystemlights.serial;

public class SerialInterfaceException extends Exception
{

    private static final long serialVersionUID = 6417035255129864916L;

    public SerialInterfaceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SerialInterfaceException(String message)
    {
        super(message);
    }

}
