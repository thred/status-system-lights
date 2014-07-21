package io.github.thred.statussystemlights;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class StaSyStatus implements Iterable<Map.Entry<String, Color>>
{

    private final Map<String, Color> colors = new HashMap<String, Color>();

    public StaSyStatus()
    {
        super();
    }

    public Color get(String name) {
        Color color = colors.get(name);
        
        return (color != null) ? color : Color.NONE;
    }
    
    @Override
    public Iterator<Entry<String, Color>> iterator()
    {
        return Collections.unmodifiableMap(colors).entrySet().iterator();
    }

    @SuppressWarnings("rawtypes")
    public boolean update(String url)
    {
        try
        {
            Content content = Request.Get(url).execute().returnContent();
            JSONObject object = (JSONObject) JSONValue.parse(content.asString());
            Iterator iterator = object.entrySet().iterator();

            while (iterator.hasNext())
            {
                Map.Entry entry = (Entry) iterator.next();
                JSONObject current = (JSONObject) entry.getValue();
                
                Color color = Color.parse((String) current.get("color"));
              
                colors.put((String) current.get("name"), color);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        
        return true;
    }
}
