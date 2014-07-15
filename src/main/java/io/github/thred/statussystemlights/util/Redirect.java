package io.github.thred.statussystemlights.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Redirect implements Runnable
{

    private final InputStream in;
    private final OutputStream out;

    private Thread thread;

    private volatile boolean running = false;

    public Redirect(InputStream in, OutputStream out)
    {
        super();

        this.in = in;
        this.out = out;
    }

    public Redirect start()
    {
        if (thread != null)
        {
            throw new IllegalStateException("Already running");
        }
        
        thread = new Thread(this, "Redirect");

        thread.setDaemon(true);
        thread.start();

        return this;
    }

    public void close() throws IOException
    {
        running = false;

        in.close();
    }

    @Override
    public void run()
    {
        byte[] buffer = new byte[1024];
        running = true;

        try
        {
            while (running)
            {
                int length = in.read(buffer);

                if (length < 0)
                {
                    break;
                }

                out.write(buffer, 0, length);
            }
        }
        catch (IOException e)
        {
            if (running)
            {
                e.printStackTrace(System.err);
            }
        }
        finally
        {
            running = false;
        }
    }

}
