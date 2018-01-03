package com.invoke.web.controller.material;

import com.invoke.model.material.MaterialConsumption;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author summer
 */
@Api(value = "materialConsumption", description = "the materialConsumption API")
public interface MaterialConsumptionApi {
    //option的value的内容是这个method的描述，notes是详细描述，response是最终返回的json model。其他可以忽略
    @ApiOperation(value = "Add a new materialConsumption to the DB", notes = "", response = Void.class, authorizations = {
            @Authorization(value = "petstore_auth", scopes = {
                    @AuthorizationScope(scope = "write:materialConsumptions", description = "modify materialConsumptions in your account"),
                    @AuthorizationScope(scope = "read:materialConsumptions", description = "read your materialConsumptions")
            })
    }, tags = {"materialConsumption",})

    //这里是显示你可能返回的http状态，以及原因。比如404 not found, 303 see other
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input", response = Void.class)})
    @RequestMapping(value = "/pet",
            produces = {"application/xml", "application/json"},
            consumes = {"application/json", "application/xml"},
            method = RequestMethod.POST)
    ResponseEntity<Void> addMaterialConsumption(
            //这里是针对每个参数的描述
            @ApiParam(value = "MaterialConsumption object that needs to be added to the DB", required = true) @RequestBody MaterialConsumption body);
}
