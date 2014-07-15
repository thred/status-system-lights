package io.github.thred.statussystemlights;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import io.github.thred.statussystemlights.util.Redirect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SerialInterface implements SerialPortEventListener
{

    public static final int TIME_OUT = 2000;
    public static final int DATA_RATE = 9600;

    private SerialPort serialPort;

    private Redirect redirect;
    private DataOutputStream out;

    public SerialInterface()
    {
        super();
    }

    public List<String> list()
    {
        List<String> results = new ArrayList<>();

        @SuppressWarnings("rawtypes")
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements())
        {
            results.add(((CommPortIdentifier) portEnum.nextElement()).getName());
        }

        return results;
    }

    protected CommPortIdentifier findPort(String name)
    {
        @SuppressWarnings("rawtypes")
        Enumeration enumerator = CommPortIdentifier.getPortIdentifiers();

        while (enumerator.hasMoreElements())
        {
            CommPortIdentifier port = (CommPortIdentifier) enumerator.nextElement();

            if (name.equalsIgnoreCase(port.getName()))
            {
                return port;
            }
        }

        return null;
    }

    public void open(String name, PrintStream consoleOut) throws SerialInterfaceException
    {
        if (serialPort != null)
        {
            throw new SerialInterfaceException("Already open");
        }

        CommPortIdentifier port = findPort(name);

        if (port == null)
        {
            throw new SerialInterfaceException(String.format("Port \"%s\" not found", name));
        }

        try
        {
            serialPort = (SerialPort) port.open(this.getClass().getName(), TIME_OUT);
        }
        catch (PortInUseException e)
        {
            throw new SerialInterfaceException("Port in use", e);
        }

        try
        {
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        }
        catch (UnsupportedCommOperationException e)
        {
            close();
            throw new SerialInterfaceException("Unsupported comm operation", e);
        }

        try
        {
            redirect = new Redirect(serialPort.getInputStream(), consoleOut).start();
        }
        catch (IOException e)
        {
            close();
            throw new SerialInterfaceException("Failed to open input stream", e);
        }

        try
        {
            out = new DataOutputStream(serialPort.getOutputStream());
        }
        catch (IOException e)
        {
            close();
            throw new SerialInterfaceException("Failed to open output stream", e);
        }

        // add event listeners
        // serialPort.addEventListener(this);
        // serialPort.notifyOnDataAvailable(true);
    }

    public void close()
    {
        if (redirect != null)
        {
            try
            {
                redirect.close();
            }
            catch (IOException e)
            {
                // ignore
            }

            redirect = null;
        }

        out = null;

        if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent oEvent)
    {
        //        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        //        {
        //            try
        //            {
        //                String inputLine = input.readLine();
        //                System.out.println(inputLine);
        //            }
        //            catch (Exception e)
        //            {
        //                System.err.println(e.toString());
        //            }
        //        }
    }

    public void sendInt(int value) throws SerialInterfaceException
    {
        if (out == null)
        {
            throw new SerialInterfaceException("Serial port not open");
        }
        
        try
        {
            out.writeInt(value);
        }
        catch (IOException e)
        {
            close();
            throw new SerialInterfaceException("Failed to send value");
        }
    }

}
