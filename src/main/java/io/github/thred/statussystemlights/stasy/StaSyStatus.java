package io.github.thred.statussystemlights.stasy;

import io.github.thred.statussystemlights.lights.Color;
import io.github.thred.statussystemlights.util.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class StaSyStatus implements Iterable<Map.Entry<String, Color>>
{

    private final Map<String, Color> colors = new TreeMap<String, Color>(Utils.DICTIONARY_COMPARATOR);

    public StaSyStatus()
    {
        super();
    }

    public Color get(String name)
    {
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
        colors.clear();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        try
        {
            CloseableHttpResponse response = client.execute(get);

            try
            {
                int status = response.getStatusLine().getStatusCode();

                if ((status >= 200) && (status < 300))
                {
                    HttpEntity entity = response.getEntity();

                    if (entity != null)
                    {
                        JSONObject object = (JSONObject) JSONValue.parse(EntityUtils.toString(entity));
                        Iterator iterator = object.entrySet().iterator();

                        while (iterator.hasNext())
                        {
                            Map.Entry entry = (Entry) iterator.next();
                            JSONObject current = (JSONObject) entry.getValue();
                            Color color = Color.parse((String) current.get("color"));

                            colors.put((String) current.get("name"), color);
                        }
                    }
                }
                else
                {
                    System.err.println("Unexpected response status: " + status);
                }
            }
            finally
            {
                response.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }

        return true;
    }
}
