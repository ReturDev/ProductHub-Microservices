package com.returdev.product.controllers;

import com.returdev.product.dtos.request.ModelRequestDTO;
import com.returdev.product.dtos.request.PaginationRequestDTO;
import com.returdev.product.dtos.response.ModelResponseDTO;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.mappers.EntityDtoMapper;
import com.returdev.product.services.model.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Model entities. Provides endpoints to retrieve, update, activate,
 * and deactivate models, as well as associate models with brands.
 */
@RestController
@RequestMapping("/v1/model")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves a model by its ID.
     *
     * @param modelId the ID of the model to retrieve
     * @return a wrapper containing the model details
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ModelResponseDTO> getModelById(
            @PathVariable("id") Long modelId
    ) {
        return entityDtoMapper.modelEntityToContentResponse(
                modelService.getModelById(modelId)
        );
    }

    /**
     * Retrieves a paginated list of models, with optional inclusion of inactive models.
     *
     * @param paginationRequestDTO pagination settings
     * @param includeInactive whether to include inactive models in the results
     * @return a paginated wrapper containing the models
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ModelResponseDTO> getModels(
            @Valid PaginationRequestDTO paginationRequestDTO,
            @RequestParam(value = "showInactives", defaultValue = "false") boolean includeInactive
    ) {
        Pageable pageable = entityDtoMapper.paginationRequestToPageable(paginationRequestDTO);
        return entityDtoMapper.modelEntityPageToPaginationResponse(
                modelService.getAllModels(pageable, includeInactive)
        );
    }

    /**
     * Retrieves a paginated list of models for a specific brand by brand ID.
     *
     * @param brandId the ID of the brand to retrieve models for
     * @param includeInactive whether to include inactive models in the results
     * @param paginationRequestDTO pagination settings
     * @return a paginated wrapper containing the models for the specified brand
     */
    @GetMapping("/brand/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ModelResponseDTO> getModelByBrandId(
            @PathVariable("id") Long brandId,
            @RequestParam(value = "showInactives", defaultValue = "false") boolean includeInactive,
            @Valid PaginationRequestDTO paginationRequestDTO
    ) {
        Pageable pageable = entityDtoMapper.paginationRequestToPageable(paginationRequestDTO);
        return entityDtoMapper.modelEntityPageToPaginationResponse(
                modelService.getModelsByBrandId(brandId, includeInactive, pageable)
        );
    }

    /**
     * Updates an entire model entity based on the provided request data.
     *
     * @param modelRequestDTO the data to update the model with
     * @return a wrapper containing the updated model details
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ModelResponseDTO> updateModel(
            @RequestBody @Valid ModelRequestDTO modelRequestDTO
    ) {
        return entityDtoMapper.modelEntityToContentResponse(
                entityDtoMapper.modelRequestToEntity(modelRequestDTO)
        );
    }

    /**
     * Partially updates a model's properties based on the provided request data.
     *
     * @param modelRequestDTO the data to update specific model properties
     * @return a wrapper containing the updated model details
     */
    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ModelResponseDTO> updateModelProperty(
            @RequestBody ModelRequestDTO modelRequestDTO
    ) {
        return entityDtoMapper.modelEntityToContentResponse(
                modelService.updateModel(
                        modelRequestDTO.id(),
                        modelRequestDTO.name(),
                        modelRequestDTO.summary()
                )
        );
    }

    /**
     * Activates a model by its ID.
     *
     * @param modelId the ID of the model to activate
     */
    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateModel(@PathVariable("id") Long modelId) {
        modelService.activateModel(modelId);
    }

    /**
     * Updates the brand association of a model.
     *
     * @param modelId the ID of the model to update
     * @param brandId the ID of the brand to associate with the model
     * @return a wrapper containing the updated model with the new brand association
     */
    @PatchMapping("/{modelId}/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ContentResponseWrapper<ModelResponseDTO> updateModelBrand(
            @PathVariable("modelId") Long modelId,
            @PathVariable("brandId") Long brandId
    ) {
        return entityDtoMapper.modelEntityToContentResponse(
                modelService.updateModelBrand(modelId, brandId)
        );
    }

    /**
     * Deactivates a model by its ID.
     *
     * @param modelId the ID of the model to deactivate
     */
    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateModel(@PathVariable("id") Long modelId) {
        modelService.inactivateModel(modelId);
    }

    /**
     * Saves a new model based on the provided request data.
     *
     * @param modelRequestDTO the data to create the new model
     * @return a wrapper containing the newly created model details
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ContentResponseWrapper<ModelResponseDTO> saveModel(
            @RequestBody @Valid ModelRequestDTO modelRequestDTO
    ) {
        return entityDtoMapper.modelEntityToContentResponse(
                modelService.saveModel(
                        entityDtoMapper.modelRequestToEntity(modelRequestDTO)
                )
        );
    }
}

