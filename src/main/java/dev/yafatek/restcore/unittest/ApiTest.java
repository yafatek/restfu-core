//package dev.yafatek.restcore.unittest;
//
//import dev.yafatek.restcore.domain.BaseEntity;
//import dev.yafatek.restcore.domain.GenericRepo;
//import dev.yafatek.restcore.services.ApiServices;
//import org.hibernate.annotations.Type;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.Column;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import java.util.UUID;
//
//@SpringBootApplication
//public class ApiTest {
//
//    private static ApiServices<User, UUID> apiServices;
//    @Autowired
//    private static UsersRepo<User,UUID> userUUIDUsersRepo;
//    public static void main(String[] args) {
//        apiServices.build(userUUIDUsersRepo).getAll();
//    }
//
//
//    @Repository
//    private interface UsersRepo<T extends BaseEntity, ID extends UUID> extends GenericRepo<T,ID> {
//
//    }
//
//    private static class User extends BaseEntity {
//        @Lob
//        @Column(name = "description")
//        protected String description;
//        @Id
//        @Type(type = "org.hibernate.type.UUIDCharType")
//        @Column(name = "id", columnDefinition = "BINARY(36)", updatable = false, nullable = false)
//        protected UUID userId;
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public UUID getUserId() {
//            return userId;
//        }
//
//        public void setUserId(UUID userId) {
//            this.userId = userId;
//        }
//    }
//}
