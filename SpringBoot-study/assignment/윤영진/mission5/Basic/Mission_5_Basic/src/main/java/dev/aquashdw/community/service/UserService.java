package dev.aquashdw.community.service;

import dev.aquashdw.community.controller.dto.UserDto;
import dev.aquashdw.community.entity.AreaEntity;
import dev.aquashdw.community.entity.UserEntity;
import dev.aquashdw.community.repository.AreaRepository;
import dev.aquashdw.community.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    public UserService(
            UserRepository userRepository,
            AreaRepository areaRepository
    ) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    public UserDto readUser(Long id) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        if (userEntityOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return new UserDto(userEntityOptional.get());
    }

    public List<UserDto> readUserAll(){
        List<UserDto> userDtoList = new ArrayList<>();
        this.userRepository.findAll().forEach(userEntity ->
                userDtoList.add(new UserDto(userEntity)));
        return userDtoList;
    }

    public void updateUser(Long id, UserDto dto){
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        if (userEntityOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserEntity userEntity = userEntityOptional.get();
        userEntity.setPassword(
                dto.getPassword() == null ? userEntity.getPassword() : dto.getPassword()
        );
        userEntity.setShopOwner(
                dto.getIsShopOwner() == null ? userEntity.getShopOwner() : dto.getIsShopOwner()
        );

        Optional<AreaEntity> newArea = this.areaRepository.findById(
                dto.getId() == null ? userEntity.getResidence().getId() : dto.getAreaId());

        newArea.ifPresent(userEntity::setResidence);
        userRepository.save(userEntity);
    }

    public void deleteUser(Long id){
        if (!this.userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.userRepository.deleteById(id);
    }
}
