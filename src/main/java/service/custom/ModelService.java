package service.custom;

import dto.ModelDto;
import service.SuperService;

import java.util.List;

public interface ModelService extends SuperService {
    String saveModel(ModelDto modelDto);

    String updateModel(ModelDto modelDto, String previousModel);

    String deleteModel(String model);

    ModelDto getModel(String model);

    List<ModelDto> getAllModels();
}
