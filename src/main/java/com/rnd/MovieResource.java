package com.rnd;

import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;

@Path("/movie")
public class MovieResource {

    private static final Logger log = Logger.getLogger(MovieResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(){
        List<Movie> movies = Movie.listAll();
        return RestResponse.ok(movies);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getById(@PathParam("id")Long id){
        log.info("get movie by="+id);
        return Movie.findByIdOptional(id)
                .map(data -> RestResponse.ok(data))
                .orElse(RestResponse.failed());
    }

    @GET
    @Path("/country/{country}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getByCountry(@PathParam("country") String country){
        List<Movie> movies = Movie.list("SELECT m FROM Movie m WHERE m.country=?1 ORDER BY id DESC",country);
        log.info("movies by contry="+movies);
        return RestResponse.ok(movies);
    }

    @GET
    @Path("/title")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getByTitle(@QueryParam("param") String title){
        log.info("movie get by title="+title);
        return Movie.find("title", title)
                .singleResultOptional()
                .map(movie -> RestResponse.ok(movie))
                .orElse(RestResponse.notfound());
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse create(Movie movie){
        log.info("process create data movie="+movie);
        Movie.persist(movie);
        if (movie.isPersistent()){
            return RestResponse
                    .ok(URI.create("/movie/"+movie.id));
        }
        return RestResponse.failed();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse deletedById(@PathParam("id")Long id){
        log.info("delete movie by id="+id);
        boolean deleted = Movie.deleteById(id);
        if (deleted){
            return RestResponse.ok("id "+id+" success deleted");
        }
        return RestResponse.failed();
    }
}
