package kz.edu.nu.cs.exercise;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/items")
public class PagingService {

    private List<String> list = new CopyOnWriteArrayList<String>();

    public PagingService() {
        for (int i = 0; i < 100; i++) {
            list.add("Entry " + String.valueOf(i));
        }
    }

    @GET
    public Response getMyList(@QueryParam("page") int page) {

        int size = 10;
        Gson gson = new Gson();
        String json;
        
        PagedHelper p = new PagedHelper();

        if (page+1 > list.size()/size) {
            p.setNext(Integer.toString(page));
        } else {
            p.setNext(Integer.toString(page+1));
        }
        p.setPrev(Integer.toString(page-1));
        p.setList(list.subList((page-1)*size, page*size));

        json = gson.toJson(p, PagedHelper.class);
        
        return Response.ok(json).build();
    }
}
