package com.wesleyprado.trabalhouna.resources;

import com.wesleyprado.trabalhouna.dto.TransferDTO;
import com.wesleyprado.trabalhouna.services.TransferService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/transfers")
public class TransferResource {

    private final TransferService transferService;

    @Autowired
    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @ApiOperation(value = "Receive a list of transfers to execute")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "List received successfully"),
            @ApiResponse(code = 400, message = "Error in data validation"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveAndExecuteTransfers(@Valid @RequestBody List<TransferDTO> transferDTOList) {
        transferService.processesTransfers(transferDTOList);
        return ResponseEntity.ok().build();
    }

}
