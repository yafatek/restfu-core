package dev.yafatek.restcore.unittest.v1;

import dev.yafatek.restcore.services.ApiServices;
import dev.yafatek.restcore.wrappers.ApiServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/d")
public class DemoController {
    protected final DemoRepo<DemoEntity, UUID> demoEntityUUIDDemoRepo;
    protected ApiServices<DemoEntity, UUID> apiServices = new ApiServiceImpl<>(demoEntityUUIDDemoRepo);

    public DemoController(DemoRepo<DemoEntity, UUID> demoEntityUUIDDemoRepo) {
        this.demoEntityUUIDDemoRepo = demoEntityUUIDDemoRepo;
    }

    @GetMapping
    public Object get() {
        return apiServices
                .getAll();
    }
}
