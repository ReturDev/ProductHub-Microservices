package com.returdev.product.services.model;

import com.returdev.product.entities.ModelEntity;
import com.returdev.product.repositories.ModelRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


/**
 * Implementation of the {@link ModelService} interface for managing models in the system.
 */
@Validated
@Repository
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no model is found with the provided {@code id}.
     */
    @Override
    public ModelEntity getModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     *
     * @param includeInactive boolean flag to include inactive models or not.
     * @return a page of {@link ModelEntity}.
     */
    @Override
    public Page<ModelEntity> getAllModels(Pageable pageable, boolean includeInactive) {
        if (includeInactive) {
            return modelRepository.findAll(pageable);
        } else {
            return modelRepository.findAllActiveModels(pageable);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no models are found with the provided {@code brandId}.
     */
    @Override
    public Page<ModelEntity> getModelsByBrandId(Long brandId, boolean includeInactive, Pageable pageable) {
        return modelRepository.findModelsByBrandId(brandId, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code model} has a null ID, as it should be present for updating.
     * @throws EntityNotFoundException if no model exists with the provided {@code modelId}.
     */
    @Transactional
    @Override
    public ModelEntity updateModel(ModelEntity model) {
        if (model.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long modelId = model.getId();

        if (!modelRepository.existsById(modelId)) {
            throw exceptionService.createEntityNotFoundException(modelId);
        }

        return modelRepository.save(model);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no model is found with the provided {@code modelId}.
     */
    @Override
    public ModelEntity updateModelName(Long modelId, String newName) {
        return modelRepository.updateModelName(modelId, newName).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(modelId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no model is found with the provided {@code modelId}.
     */
    @Override
    public ModelEntity updateModelSummary(Long modelId, String newSummary) {
        return modelRepository.updateModelSummary(modelId, newSummary).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(modelId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no model is found with the provided {@code modelId}.
     */
    @Override
    public ModelEntity updateModelBrand(Long modelId, Long brandId) {
        return modelRepository.updateModelBrand(modelId, brandId).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(modelId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code model} has a non-null ID, as it should be null for a new model.
     */
    @Override
    public ModelEntity saveModel(ModelEntity model) {
        if (model.getId() != null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }
        return modelRepository.save(model);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no model is found with the provided {@code modelId}.
     */
    @Override
    public void activateModel(Long modelId) {
        int result = modelRepository.activateModel(modelId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(modelId);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no model is found with the provided {@code modelId}.
     */
    @Override
    public void inactivateModel(Long modelId) {
        int result = modelRepository.inactivateModel(modelId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(modelId);
        }
    }
}

