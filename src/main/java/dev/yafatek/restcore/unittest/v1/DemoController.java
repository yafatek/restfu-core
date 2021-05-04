package dev.yafatek.restcore.unittest.v1;

import dev.yafatek.restcore.domain.DemoEntity;
import dev.yafatek.restcore.domain.DemoRepo;
import dev.yafatek.restcore.services.ApiServices;
import dev.yafatek.restcore.wrappers.ApiServicesWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/d")
public class DemoController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
    protected final DemoRepo<DemoEntity, UUID> demoEntityUUIDDemoRepo;
    protected ApiServices<DemoEntity, UUID> apiServices;

    public DemoController(DemoRepo<DemoEntity, UUID> demoEntityUUIDDemoRepo) {
        this.apiServices = new ApiServicesWrapper<>(demoEntityUUIDDemoRepo) {
        };
        this.demoEntityUUIDDemoRepo = demoEntityUUIDDemoRepo;
    }

    @GetMapping
    public Object get() {
        LOGGER.info(" [*] debugging: apiService: {}", apiServices);
        LOGGER.info(" [*] debugging: demo Repo: {}", demoEntityUUIDDemoRepo);
        return apiServices
                .getAll();
    }
}
