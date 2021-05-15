### RESTFULL Generic Lib

1- Getting started
--
1. Getting the Package:
   you can easily get the package from maven central
   * Maven:
    ```xml
    <dependency>
        <groupId>dev.yafatek</groupId>
        <artifactId>yafatek-restcore</artifactId>
        <version>1.0.104</version>
    </dependency>
    ```
   * gradle:
    ```groovy
     implementation 'dev.yafatek:yafatek-restcore:1.0.104'
   ```
   * kotlin DSL:
   ```kotlin
      implementation("dev.yafatek:yafatek-restcore:1.0.104")
   ```
2- need more?
-- 
check out our repository: https://search.maven.org/artifact/dev.yafatek/yafatek-restcore

3. Usage:
   * simple Usage: by create a repository that extends the GenericRepo
   ```java
   @Repository
   @Transactional
    public interface CustomersRepo<T extends BaseEntity, ID extends Serializable> extends GenericRepo<T, ID>
           Customer findByEmail(String email);
    }
   ```
   * create repo object in RestController class 
   ```java
    private final CustomersRepo<Customer, UUID> customersRepo;
   ```
   * and autowire it in the constructor.
   ```java
      public CustomerConsumer( CustomersRepo<Customer, UUID> customersRepo) {
         this.customersRepo = customersRepo;
        }
   ```
   * to start using the api we need to create an object from: ApiService<> interface:
   ```java
   private final ApiServices<Customer, UUID> customerService;
   ```
   * then pass the repositrory that we create to the initalization of the api in the constructor
   ```java
    public CustomerConsumer( CustomersRepo<Customer, UUID> customersRepo) {
         this.customersRepo = customersRepo;
         this.customerService = new ApiServicesWrapper<Customer, UUID>(customersRepo) {};
        }
  
   ```
3- Full Demo:
--
 * full demo of the api can be found at the following Code

```java
package dev.yafatek.restcore.unittest.v1;

import ApiUtils;
import dev.yafatek.restcore.domain.DemoEntity;
import dev.yafatek.restcore.domain.DemoRepo;
import dev.yafatek.restcore.services.ApiServices;
import dev.yafatek.restcore.wrappers.ApiServicesWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
        // save dummy Data.

        return apiServices
                .getAll();


    }

    @GetMapping(value = "/query")
    public Object getOne(@RequestParam("target") String uuid) {
        return apiServices.getById(UUID.fromString(uuid));
    }

    @GetMapping(value = "/by-date")
    public Object byDate(@RequestParam("offset") String after) {
        return apiServices.getAllAfter(ApiUtils.stringToInstant(after));
    }

    @PatchMapping
    public Object update(@RequestBody DummyUpdate dummy) {
        DemoEntity demoEntity = new DemoEntity(dummy.getDescription(), dummy.getAttribute());
        demoEntity.setModified(Instant.now());
        return apiServices.updateById(demoEntity, UUID.fromString(dummy.getId()));
    }

    @DeleteMapping(value = "/secure/delete")
    public Object deleteOne(@RequestParam("target") String target) {
        return apiServices.deleteById(UUID.fromString(target));
    }

    @DeleteMapping(value = "/secure/bulk-delete")
    public Object bulkDelete() {
        return apiServices.bulkDelete();
    }

    @PostMapping
    public Object post(@RequestBody Dummy dummy) {
        LOGGER.info(" [.] Saving Dummy Data: {}", dummy);
        // UUID id, Instant created, String description, String attribute
        return apiServices.saveEntity(new DemoEntity(UUID.randomUUID(), Instant.now(), dummy.getDescription(), dummy.getAttribute()));
    }


    static class DummyUpdate {
        protected String id;
        protected String description;
        protected String attribute;

        public DummyUpdate() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }
    }

    static class Dummy {
        protected String description;
        protected String attribute;

        public Dummy() {
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        @Override
        public String toString() {
            return "Dummy{" +
                    "description='" + description + '\'' +
                    ", attribute='" + attribute + '\'' +
                    '}';
        }
    }
}

```