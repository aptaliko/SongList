package songlist.service.features;

import songlist.exceptions.ValidationException;
import songlist.model.features.region.Region;
import songlist.model.features.region.dto.NewRegionDTO;
import songlist.model.features.region.dto.RegionDTO;
import songlist.repository.features.RegionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static songlist.utils.Constants.DOES_NOT_EXIST;
import static songlist.utils.Utils.isNotNullOrEmpty;

public class RegionService {

    RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<RegionDTO> getAll() {
        return regionRepository.findAll().stream().map(r -> new RegionDTO(r.getId().toString(), r.getFullRegionName())).collect(Collectors.toList());
    }

    public Region get(String id) throws ValidationException {
        Optional<Region> region = regionRepository.findById(UUID.fromString(id));

        if (region.isEmpty()) {
            throw new ValidationException("Region with id: " + id  + DOES_NOT_EXIST);
        }
        return region.get();
    }

    public RegionDTO getDTO(String id) {
        Region region = regionRepository.getOne(UUID.fromString(id));
        return new RegionDTO(region.getId().toString(), region.getFullRegionName());
    }

    public String create(NewRegionDTO newRegionDTO) throws ValidationException {
        Region region = new Region();
        updateRegionObject(region, newRegionDTO);

        return regionRepository.save(region).getId().toString();
    }

    public String update(String id, NewRegionDTO newRegionDTO) throws ValidationException {
        Region region = get(id);
        updateRegionObject(region, newRegionDTO);

        return regionRepository.save(region).getId().toString();
    }

    public void delete(String id) {
        regionRepository.deleteById(UUID.fromString(id));
    }

    private void updateRegionObject(Region region, NewRegionDTO dto) throws ValidationException {
        region.setName(dto.getName());
        if (isNotNullOrEmpty(dto.getParentRegionId())) {
            region.setParentRegion(get(dto.getParentRegionId()));
        }
    }
}