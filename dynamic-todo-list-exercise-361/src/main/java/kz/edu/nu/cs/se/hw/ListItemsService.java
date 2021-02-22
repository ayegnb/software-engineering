package kz.edu.nu.cs.se.hw;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/items")
public class ListItemsService {
    
    private List<String> list = new CopyOnWriteArrayList<String>();

    public ListItemsService() {
        for (int i = 0; i < 20; i++) {
            list.add("Entry " + String.valueOf(i) + " [created at: " + String.valueOf(java.time.LocalTime.now()) + "]");
        }
        
        Collections.reverse(list);
    }
    
    @GET
    public Response getList() {
        Gson gson = new Gson();
        
        return Response.ok(gson.toJson(list)).build();
    }
    
    @GET
    @Path("{id: [0-9]+}")
    public Response getListItem(@PathParam("id") String id) {
        int i = Integer.parseInt(id);
        
        return Response.ok(list.get(i)).build();
    }
    
    @POST
    public Response postListItem(@FormParam("newEntry") String entry) {
        String newEntry = entry + " [created at: " + String.valueOf(java.time.LocalTime.now()) + "]";
        list.add(0, newEntry);

        return Response.ok().build();
    }

    @DELETE
    public void clearList() {
        list.clear();
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteItem(@PathParam("id") String id) {
        int i = Integer.parseInt(id);
        return Response.ok(list.remove(i)).build();
    }
}
