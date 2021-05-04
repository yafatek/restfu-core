//package dev.yafatek.restcore.unittest;
//
//import dev.yafatek.restcore.domain.BaseEntity;
//import dev.yafatek.restcore.domain.GenericRepo;
//import dev.yafatek.restcore.services.ApiServices;
//import org.hibernate.annotations.Type;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.UUID;
//
//public class UnitTest {
//
//    /**
//     * This is a unit test to test the Api Library
//     * and check if its usable among the way while developing the libraries
//     * the library is using some theory about the rest controlllers
//     */
//    @Autowired
//    private static UserRepo userRepo;
//    //    @Autowired
////    private static ApiServiceImpl<User,UUID> apiService;
//    private static ApiServices<User, UUID> apiServices;
////    private static ApiServices<User, UUID> apiServices = new ApiServiceImpl<>(userRepo); //= ApiServices.build();
//
//    public static void main(String[] args) {
//
//        apiServices.build(userRepo)
//                .saveEntity(new User(1, Instant.now(), UUID.randomUUID(), "description"));
//
//        apiServices.getById(UUID.fromString("65daf6df-sfs3-gsdf-45-")).getResults();
//        apiServices.bulkDelete();
//        apiServices.getAll();
//        apiServices.getAllAfter(Instant.now().minus(1, ChronoUnit.DAYS));
//        apiServices.deleteById(UUID.fromString("shjd"));
//        apiServices.saveEntity(new User(1, Instant.now(), UUID.randomUUID(), "description"));
//        apiServices.updateById(new User(1, Instant.now(), UUID.randomUUID(), "description"), UUID.fromString("jhbsfkf"));
//
//    }
//
//    @Repository
//    private interface UserRepo extends GenericRepo<User, UUID> {
//
//    }
//
//    @Entity
//    @Table(name = "users")
//    private static class User extends BaseEntity {
//        @Lob
//        @Column(name = "description")
//        protected String description;
//        @Id
//        @Type(type = "org.hibernate.type.UUIDCharType")
//        @Column(name = "id", columnDefinition = "BINARY(36)", updatable = false, nullable = false)
//        private UUID id;
//
//        public User(int version, Instant created, UUID id, String description) {
//            super(version, created);
//            this.id = id;
//            this.description = description;
//        }
//
//        public User() {
//
//        }
//
//        public UUID getId() {
//            return id;
//        }
//
//        public void setId(UUID id) {
//            this.id = id;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//    }
//}
