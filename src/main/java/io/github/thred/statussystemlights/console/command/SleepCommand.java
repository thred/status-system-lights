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
package io.github.thred.statussystemlights.console.command;

import io.github.thred.statussystemlights.console.AbstractCommand;
import io.github.thred.statussystemlights.console.ArgumentException;
import io.github.thred.statussystemlights.console.ConsoleAdapter;
import io.github.thred.statussystemlights.console.Process;

/**
 * Prints text.
 * 
 * @author Manfred Hantschel
 */
public class SleepCommand extends AbstractCommand
{

    public SleepCommand(ConsoleAdapter consoleAdapter)
    {
        super(consoleAdapter, "sleep");
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.Command#getFormat()
     */
    @Override
    public String getFormat()
    {
        return "<MILLIS>";
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.Command#getDescription()
     */
    @Override
    public String getDescription()
    {
        return "Sleeps.";
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.Command#getOrdinal()
     */
    @Override
    public int getOrdinal()
    {
        return 1500;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.Command#execute(java.lang.String,
     *      net.sourceforge.eclipsejetty.starter.console.Process)
     */
    @Override
    public int execute(String commandName, Process process) throws Exception
    {
        Long duration = process.args.consumeLong();

        if (duration == null)
        {
            throw new ArgumentException("Duration missing");
        }

        Thread.sleep(duration.longValue());

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see net.sourceforge.eclipsejetty.starter.console.AbstractCommand#getHelpDescription()
     */
    @Override
    protected String getHelpDescription()
    {
        return null;
    }

}
