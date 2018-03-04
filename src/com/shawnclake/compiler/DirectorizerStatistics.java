package com.shawnclake.compiler;

import com.shawnclake.morgencore.core.component.Results;

public class DirectorizerStatistics extends Results {

    public DirectorizerStatistics() {
        this.addResult("total-routes", 0);
    }

    public void addNewRoute(String route)
    {
        this.addResult("total-routes", this.getResult("total-routes").getInt() + 1);
        this.addResult("route-"+this.getResult("total-routes").getInt(), route);
        //System.out.println("route-"+this.getResult("total-routes").getInt() + ".");
    }

    public int getTotalRoutes()
    {
        return this.getResult("total-routes").getInt();
    }

    public String getRoute(int routeIndex)
    {
        routeIndex++;
        return this.getResult("route-"+routeIndex).getString();
    }
}
