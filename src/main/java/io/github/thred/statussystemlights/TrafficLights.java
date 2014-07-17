package io.github.thred.statussystemlights;

public class TrafficLights implements Runnable
{

    private final StaSyInterface staSyInterface;

    public TrafficLights(StaSyInterface staSyInterface)
    {
        super();

        this.staSyInterface = staSyInterface;
    }

    @Override
    public void run()
    {
        // TODO Auto-generated method stub

    }

}
