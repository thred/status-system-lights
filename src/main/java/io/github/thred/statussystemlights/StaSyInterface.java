package io.github.thred.statussystemlights;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class StaSyInterface
{

    private String url = "http://nadindevel.wob.vw.vwg:8081/rest";

    public StaSyInterface()
    {
        super();
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void update()
    {
        try
        {
            Content content = Request.Get("http://targethost/homepage").execute().returnContent();
            
            JSONObject object = (JSONObject) JSONValue.parse(content.asString());
            
            for (Map.Entry entry : entries) {
                // this json lib sucks!!!!!!!!
            }
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace(System.err);
        }
    }
}
