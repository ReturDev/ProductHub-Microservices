package com.returdev.product.services.model;

import com.returdev.product.entities.ModelEntity;
import com.returdev.product.exceptions.InvalidIdentifierException;
import com.returdev.product.repositories.ModelRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * Implementation of the ModelService interface for managing models in the system.
 */
@Validated
@Repository
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ModelEntity> getModelById(
            @NotNull(message = "${validation.not_null.message}") Long id
    ) {
        return modelRepository.findById(id);
    }

    /**
     * {@inheritDoc}
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
     */
    @Override
    public Page<ModelEntity> getModelsByBrandId(
            @NotNull(message = "${validation.not_null.message}") Long brandId,
            boolean includeInactive,
            Pageable pageable
    ) {
        return modelRepository.findModelsByBrandId(brandId, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelEntity updateModel(@Valid ModelEntity model) {

        if (model.getId() == null) {
            throw new InvalidIdentifierException("exception.id_is_null.message");
        }

        if (!modelRepository.existsById(model.getId())) {
            throw new InvalidIdentifierException("exception.not_exists_by_id.message");
        }

        return modelRepository.save(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ModelEntity> updateModelName(
            @NotNull(message = "${validation.not_null.message}") Long modelId,
            @NotBlank(message = "${validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "${validation.size.message}")
            String newName
    ) {
        return modelRepository.updateModelName(modelId, newName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ModelEntity> updateModelSummary(
            @NotNull(message = "${validation.not_null.message}") Long modelId,
            @NotNull(message = "${validation.not_null.message}")
            @Size(max = 150, message = "${validation.size.max.message}")
            String newSummary
    ) {
        return modelRepository.updateModelSummary(modelId, newSummary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ModelEntity> updateModelBrand(
            @NotNull(message = "${validation.not_null.message}") Long modelId,
            @NotNull(message = "${validation.not_null.message}") Long brandId
    ) {
        return modelRepository.updateModelBrand(modelId, brandId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelEntity saveModel(ModelEntity model) {

        if (model.getId() != null) {
            throw new InvalidIdentifierException("exception.id_is_not_null.message");
        }

        return modelRepository.save(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateModel(
            @NotNull(message = "${validation.not_null.message}") Long modelId
    ) {
        modelRepository.activateModel(modelId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inactivateModel(
            @NotNull(message = "${validation.not_null.message}") Long modelId
    ) {
        modelRepository.inactivateModel(modelId);
    }

}
