/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syntech.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syntech.exception.CustomException;
import com.syntech.model.Product;
import com.syntech.repository.ProductRepository;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author rasmi
 */
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductRestApi {

    @Inject
    private ProductRepository productRepository;

    @POST
    @Path("create")
    public Response createProduct(Product product) throws JsonProcessingException {
        productRepository.create(product);
        return RestResponse.responseBuilder("true", "200", " Product Created ", product);
    }

    @GET
    public Response getAllProduct() throws JsonProcessingException, CustomException {
        List<Product> p = productRepository.findAll();
        if (p == null) {
            throw new CustomException("Product is null");
        }
        return RestResponse.responseBuilder("true", "200", "List of Product", p);
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam("id") Long id) throws JsonProcessingException, CustomException {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new CustomException("Product of id " + id + " is null");
        }
        return RestResponse.responseBuilder("True", "200", "List of product of the given id", product);
    }

    @DELETE
    @Path("delete/{id}")
    public Response deleteProduct(@PathParam("id") Long id) throws JsonProcessingException, CustomException {
        Product p = productRepository.findById(id);
        if (p == null) {
            throw new CustomException("Product of id " + id + " is null");
        }
        productRepository.delete(p);
        return RestResponse.responseBuilder("True", "200", "Product deleted successfully", null);
    }

    @PUT
    @Path("edit/{id}")
    public Response editProduct(@PathParam("id") Long id, Product p) throws JsonProcessingException, CustomException {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new CustomException("Product of id " + id + " is null");
        }
        productRepository.edit(p);
        return RestResponse.responseBuilder("True", "200", "Product Edited Successfully", p);
    }
}
