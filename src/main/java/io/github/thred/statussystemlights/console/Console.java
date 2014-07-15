// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package io.github.thred.statussystemlights.console;

import io.github.thred.statussystemlights.console.util.Arguments;
import io.github.thred.statussystemlights.console.util.Scanner;
import io.github.thred.statussystemlights.console.util.Tokenizer;
import io.github.thred.statussystemlights.service.GlobalServiceResolver;
import io.github.thred.statussystemlights.service.ServiceResolver;
import io.github.thred.statussystemlights.service.ServiceUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The single instance of the console. Parses the input, creates process and executes them.
 * 
 * @author Manfred Hantschel
 */
public class Console implements Runnable, ConsoleAdapter
{

    /**
     * The single instance of the console.
     */
    public static final Console INSTANCE = new Console();

    /**
     * The console can be started on it's own. Lives until killed.
     * 
     * @param args the arguments
     * @throws InterruptedException on occasion
     */
    public static void main(String[] args) throws InterruptedException
    {
        Console.INSTANCE.initialize(GlobalServiceResolver.INSTANCE);
        Console.INSTANCE.start(false);
    }

    private final Map<String, Command> commands = new LinkedHashMap<String, Command>();

    private Tokenizer tokenizer;

    private Console()
    {
        super();

        GlobalServiceResolver.INSTANCE.register(this);
    }

    /**
     * Initializes the console using the specified {@link ServiceResolver}.
     * 
     * @param resolver the resolver
     */
    public void initialize(ServiceResolver resolver)
    {
        Collection<Object> commands;

        try
        {
            commands = ServiceUtils.instantiateContributions(getClass(), resolver);
        }
        catch (IOException e)
        {
            throw new ExceptionInInitializerError(e);
        }

        for (Object command : commands)
        {
            register((Command) command);
        }
    }

    /**
     * Registers a command.
     * 
     * @param command the command, never null
     */
    public void register(Command command)
    {
        if (!command.isEnabled())
        {
            return;
        }

        for (String commandName : command.getNames())
        {
            commands.put(commandName.toLowerCase(), command);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.ConsoleAdapter#getCommand(java.lang.String)
     */
    @Override
    public Command getCommand(String name)
    {
        return commands.get(name.toLowerCase());
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.ConsoleAdapter#getCommands()
     */
    @Override
    public Collection<Command> getCommands()
    {
        List<Command> result = new ArrayList<Command>(new HashSet<Command>(commands.values()));

        Collections.sort(result, new Comparator<Command>()
        {

            @Override
            public int compare(Command o1, Command o2)
            {
                return o1.getOrdinal() - o2.getOrdinal();
            }

        });

        return result;
    }

    /**
     * Starts a daemon thread using this console. Reads from the default in stream.
     */
    public void start(boolean daemon)
    {
        if (tokenizer != null)
        {
            return;
        }

        Thread thread = new Thread(this, "Eclipse Jetty Console");

        thread.setDaemon(daemon);
        thread.start();
    }

    /**
     * Stops the console.
     */
    public void stop()
    {
        if (tokenizer == null)
        {
            return;
        }

        try
        {
            tokenizer.close();
        }
        catch (IOException e)
        {
            // ignore
        }
        finally
        {
            tokenizer = null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        try
        {
            tokenizer = new Tokenizer(new Scanner(new InputStreamReader(System.in)));

            try
            {
                Arguments args;

                while ((args = tokenizer.read()) != null)
                {
                    new Process(this, null, args, System.in, System.out, System.err).execute();
                }
            }
            finally
            {
                tokenizer.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        finally
        {
            tokenizer = null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.ConsoleAdapter#getLineLength()
     */
    @Override
    public int getLineLength()
    {
        return 80;
    }

}
