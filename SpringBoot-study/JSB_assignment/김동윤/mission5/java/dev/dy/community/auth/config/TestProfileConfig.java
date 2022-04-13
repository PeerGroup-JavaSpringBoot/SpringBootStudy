package dev.aquashdw.community.auth.config;

import dev.aquashdw.community.entity.AreaEntity;
import dev.aquashdw.community.entity.UserEntity;
import dev.aquashdw.community.repository.AreaRepository;
import dev.aquashdw.community.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

// testing purposes
@Profile("test")
@Configuration
public class TestProfileConfig {
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TestProfileConfig(
            AreaRepository areaRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.areaRepository = areaRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.setDefaultAreaEntities();
        this.setDefaultUserEntities();
    }

    private void setDefaultAreaEntities(){
        AreaEntity tempEntity = new AreaEntity();
        tempEntity.setRegionMajor("Seoul");
        tempEntity.setRegionMinor("Seocho-gu");
        tempEntity.setRegionPatch("Seocho-dong");
        tempEntity.setLatitude(37.4877);
        tempEntity.setLongitude(127.0174);
        this.areaRepository.save(tempEntity);

        tempEntity = new AreaEntity();
        tempEntity.setRegionMajor("Seoul");
        tempEntity.setRegionMinor("Gangnam-gu");
        tempEntity.setRegionPatch("Yeoksam-dong");
        tempEntity.setLatitude(37.4999);
        tempEntity.setLongitude(127.0374);
        this.areaRepository.save(tempEntity);

        tempEntity = new AreaEntity();
        tempEntity.setRegionMajor("Seoul");
        tempEntity.setRegionMinor("Gangnam-gu");
        tempEntity.setRegionPatch("Samseong-dong");
        tempEntity.setLatitude(37.5140);
        tempEntity.setLongitude(127.0565);
        this.areaRepository.save(tempEntity);
    }

    private void setDefaultUserEntities(){
        Iterator<AreaEntity> areaEntityIterator = this.areaRepository.findAll().iterator();
        AtomicInteger i = new AtomicInteger(0);
        areaEntityIterator.forEachRemaining(areaEntity -> {
            int index = i.incrementAndGet();
            UserEntity tempEntity = new UserEntity();
            tempEntity.setUsername(String.format("test_user%d", index));
            tempEntity.setPassword(passwordEncoder.encode(String.format("test%dpass", index)));
            tempEntity.setResidence(areaEntity);
            this.userRepository.save(tempEntity);
        });
    }
}
