package io.github.thred.statussystemlights.serial;

import io.github.thred.tinyconsole.AbstractCommand;
import io.github.thred.tinyconsole.Process;

public class OpenCommand extends AbstractCommand
{

    private final SerialInterface serialInterface;

    public OpenCommand(SerialInterface serialInterface)
    {
        super("open", "o");

        this.serialInterface = serialInterface;
    }

    @Override
    public String getFormat()
    {
        return "<PORT>";
    }

    @Override
    public String getDescription()
    {
        return "Opens a serial port";
    }

    @Override
    public int getOrdinal()
    {
        return 11;
    }

    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        String name = process.args.consumeString();

        if (name == null)
        {
            process.err.println("Port is missing.");

            return -1;
        }

        serialInterface.open(name, process.out);

        return 0;
    }

    @Override
    protected String getHelpDescription()
    {
        return "Opens a serial port. Usually the <PORT> depends on your system.\n\n" // 
            + "Samples:\n" // 
            + "  Linux:      open /dev/ttyUSB0\n" // 
            + "  Windows:    open COM11\n" // 
            + "  Max OS X:   open /dev/tty.usbserial-A9007UX1";
    }

}
