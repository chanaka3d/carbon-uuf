/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.uuf.httpconnector.msf4j;

import org.wso2.carbon.uuf.api.Server;
import org.wso2.msf4j.Microservice;
import org.wso2.msf4j.Request;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * UUF Connector for MSF4J.
 */
public class UUFMicroservice implements Microservice {

    private Server uufServer;

    public UUFMicroservice(Server uufServer) {
        this.uufServer = uufServer;
    }

    @GET
    @Path(".*")
    public Response get(@Context Request request) {
        return getImpl(request);
    }

    @GET
    @Path("")
    public Response getImpl(@Context Request request) {
        MicroserviceHttpRequest httpRequest = new MicroserviceHttpRequest(request);
        MicroserviceHttpResponse httpResponse = new MicroserviceHttpResponse();
        uufServer.serve(httpRequest, httpResponse);
        return httpResponse.build();
    }

    @POST
    @Path(".*")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response post(@Context Request request, @Context MultivaluedMap multivaluedMap) {
        return postImpl(request, multivaluedMap);
    }

    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response postImpl(@Context Request request, @Context MultivaluedMap multivaluedMap) {
        MicroserviceHttpRequest httpRequest = new MicroserviceHttpRequest(request, multivaluedMap);
        MicroserviceHttpResponse httpResponse = new MicroserviceHttpResponse();
        uufServer.serve(httpRequest, httpResponse);
        return httpResponse.build();
    }
}
